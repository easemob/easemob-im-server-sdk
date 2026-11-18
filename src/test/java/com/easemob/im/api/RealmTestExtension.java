package com.easemob.im.api;

import com.easemob.im.ApiClient;
import com.easemob.im.Configuration;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePreConstructCallback;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class RealmTestExtension implements TestTemplateInvocationContextProvider {
    private static final Map<ApiClient.Realm, ApiClient> CLIENTS = new EnumMap<>(ApiClient.Realm.class);

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return Stream.of(invocation(ApiClient.Realm.EASEMOB_REALM), invocation(ApiClient.Realm.AGORA_REALM));
    }

    private TestTemplateInvocationContext invocation(ApiClient.Realm realm) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return realm.name();
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                // API fields are initialized in test constructors, before @BeforeEach callbacks.
                TestInstancePreConstructCallback selectClient = (factoryContext, extensionContext) ->
                        Configuration.setDefaultApiClient(clientForRealm(realm));
                return Collections.singletonList(selectClient);
            }
        };
    }

    protected ApiClient clientForRealm(ApiClient.Realm realm) throws Exception {
        synchronized (CLIENTS) {
            ApiClient client = CLIENTS.get(realm);
            if (client == null) {
                ApiClient.Builder builder = ApiClient.builder()
                        .setDispatcherMaxRequests(300)
                        .setDispatcherMaxRequestsPerHost(60);
                if (realm == ApiClient.Realm.AGORA_REALM) {
                    builder.setBasePath(agoraSetting("IM_AG_BASE_URI"))
                            .setRealm(ApiClient.Realm.AGORA_REALM)
                            .setAppId(agoraSetting("APP_ID"))
                            .setAppCert(agoraSetting("APP_CERT"));
                } else {
                    builder.setBasePath(System.getenv("IM_BASE_URI"))
                            .setAppKey(System.getenv("IM_APPKEY"))
                            .setClientId(System.getenv("IM_CLIENT_ID"))
                            .setClientSecret(System.getenv("IM_CLIENT_SECRET"));
                }
                client = builder.build();
                CLIENTS.put(realm, client);
            }
            return client;
        }
    }

    private static String agoraSetting(String name) throws IOException {
        String value = System.getenv(name);
        if (value != null && !value.trim().isEmpty()) {
            return value;
        }
        Path localEnvironment = Paths.get(".env.agora.local");
        if (Files.isRegularFile(localEnvironment)) {
            for (String line : Files.readAllLines(localEnvironment, StandardCharsets.UTF_8)) {
                String entry = line.trim();
                if (entry.startsWith("export ")) {
                    entry = entry.substring(7).trim();
                }
                int separator = entry.indexOf('=');
                if (separator < 0 || !entry.substring(0, separator).trim().equals(name)) {
                    continue;
                }
                value = entry.substring(separator + 1).trim();
                if (value.length() >= 2 && ((value.startsWith("'") && value.endsWith("'"))
                        || (value.startsWith("\"") && value.endsWith("\"")))) {
                    value = value.substring(1, value.length() - 1);
                }
                return value;
            }
        }
        return null;
    }
}
