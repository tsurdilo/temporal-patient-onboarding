package org.acme.patient.onboarding.utils;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.acme.patient.onboarding.app.ServiceExecution;

import java.time.Duration;

public class ActivityStubUtils {
    public static ServiceExecution getActivitiesStub() {
        return Workflow.newActivityStub(
                ServiceExecution.class,
                ActivityOptions.newBuilder()
                        .setStartToCloseTimeout(Duration.ofMinutes(1))
                        .build());
    }

    public static ServiceExecution getActivitiesStubWithRetries() {
        return Workflow.newActivityStub(
                ServiceExecution.class,
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
