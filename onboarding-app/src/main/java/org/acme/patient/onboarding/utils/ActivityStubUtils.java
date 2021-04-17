package org.acme.patient.onboarding.utils;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.acme.patient.onboarding.activities.OnboardingActivities;

import java.time.Duration;

public class ActivityStubUtils {
    public static OnboardingActivities getActivitiesStub() {
        return Workflow.newActivityStub(
                OnboardingActivities.class,
                ActivityOptions.newBuilder()
                        .setStartToCloseTimeout(Duration.ofMinutes(1))
                        .build());
    }

    public static OnboardingActivities getActivitiesStubWithRetries() {
        return Workflow.newActivityStub(
                OnboardingActivities.class,
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
