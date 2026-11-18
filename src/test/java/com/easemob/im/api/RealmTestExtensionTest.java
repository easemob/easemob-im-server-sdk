package com.easemob.im.api;

import com.easemob.im.ApiClient;
import com.easemob.im.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

@Execution(ExecutionMode.SAME_THREAD)
@ResourceLock("default-api-client")
class RealmTestExtensionTest {
    private static final List<ApiClient.Realm> INVOCATIONS = new ArrayList<>();
    private static final List<Object> INSTANCES = new ArrayList<>();
    private static final Map<ApiClient.Realm, ApiClient> CLIENTS = new EnumMap<>(ApiClient.Realm.class);
    private ApiClient originalClient;

    @BeforeEach
    void prepareClients() {
        originalClient = Configuration.getDefaultApiClient();
        INVOCATIONS.clear();
        INSTANCES.clear();
        CLIENTS.clear();
        for (ApiClient.Realm realm : ApiClient.Realm.values()) {
            CLIENTS.put(realm, new ApiClient() {
                @Override
                public Realm getRealm() {
                    return realm;
                }
            });
        }
    }

    @AfterEach
    void restoreClient() {
        Configuration.setDefaultApiClient(originalClient);
    }

    @Test
    void runsBothRealmsWithTheirClientBoundBeforeFieldInitialization() {
        TestExecutionSummary summary = runFixture(SuccessfulFixture.class);

        assertEquals(2, summary.getTestsSucceededCount());
        assertEquals(0, summary.getTestsFailedCount());
        assertEquals(0, summary.getContainersFailedCount());
        assertEquals(Arrays.asList(ApiClient.Realm.EASEMOB_REALM, ApiClient.Realm.AGORA_REALM), INVOCATIONS);
        assertEquals(2, INSTANCES.size());
        assertNotSame(INSTANCES.get(0), INSTANCES.get(1));
    }

    @Test
    void stillRunsAgoraWhenEasemobFails() {
        TestExecutionSummary summary = runFixture(FailingFixture.class);

        assertEquals(1, summary.getTestsFailedCount());
        assertEquals(1, summary.getTestsSucceededCount());
        assertEquals(0, summary.getContainersFailedCount());
        assertEquals(Arrays.asList(ApiClient.Realm.EASEMOB_REALM, ApiClient.Realm.AGORA_REALM), INVOCATIONS);
    }

    private static TestExecutionSummary runFixture(Class<?> fixture) {
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        LauncherFactory.create().execute(LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(fixture))
                .configurationParameter("junit.jupiter.execution.parallel.enabled", "false")
                .build(), listener);
        return listener.getSummary();
    }

    public static class StubRealmExtension extends RealmTestExtension {
        @Override
        protected ApiClient clientForRealm(ApiClient.Realm realm) {
            return CLIENTS.get(realm);
        }
    }

    public static class SuccessfulFixture {
        private final ApiClient capturedClient = Configuration.getDefaultApiClient();

        @TestTemplate
        @ExtendWith(StubRealmExtension.class)
        void exercise() {
            INVOCATIONS.add(capturedClient.getRealm());
            INSTANCES.add(this);
            assertSame(CLIENTS.get(capturedClient.getRealm()), capturedClient);
            assertSame(capturedClient, Configuration.getDefaultApiClient());
        }
    }

    public static class FailingFixture {
        private final ApiClient capturedClient = Configuration.getDefaultApiClient();

        @TestTemplate
        @ExtendWith(StubRealmExtension.class)
        void exercise() {
            INVOCATIONS.add(capturedClient.getRealm());
            if (capturedClient.getRealm() == ApiClient.Realm.EASEMOB_REALM) {
                throw new AssertionError("Intentional fixture failure");
            }
        }
    }
}
