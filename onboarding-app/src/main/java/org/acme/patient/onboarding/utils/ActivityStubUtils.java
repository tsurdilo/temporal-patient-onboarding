package org.acme.patient.onboarding.utils;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.acme.patient.onboarding.app.ServiceExecutor;

import java.time.Duration;

public class ActivityStubUtils {
    public static ServiceExecutor getActivitiesStub() {
        return Workflow.newActivityStub(
                ServiceExecutor.class,
                ActivityOptions.newBuilder()
                        .setStartToCloseTimeout(Duration.ofMinutes(1))
                        .build());
    }

    public static ServiceExecutor getActivitiesStubWithRetries() {
        return Workflow.newActivityStub(
                ServiceExecutor.class,
                ActivityOptions.newBuilder()
                        .setRetryOptions(
                                RetryOptions.newBuilder()
                                        .setInitialInterval(Duration.ofSeconds(1))
                                        .setMaximumAttempts(6)
                                        .build()
                        )
                        .setStartToCloseTimeout(Duration.ofMinutes(1))
                        .build());
    }
}
