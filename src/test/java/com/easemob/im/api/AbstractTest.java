package com.easemob.im.api;

import com.easemob.im.ApiClient;
import com.easemob.im.Configuration;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Execution(ExecutionMode.SAME_THREAD)
@ResourceLock("default-api-client")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public abstract class AbstractTest {
    protected static String getAppBaseUrl() {
        ApiClient apiClient = Configuration.getDefaultApiClient();
        return apiClient.getServers().get(apiClient.getServerIndex())
                .URL(apiClient.getServerVariables());
    }

    protected static String randomUserName() {
        return String.format("user-%d-%d",
                ThreadLocalRandom.current().nextInt(100000000),
                Instant.now().toEpochMilli()
        );
    }
}
