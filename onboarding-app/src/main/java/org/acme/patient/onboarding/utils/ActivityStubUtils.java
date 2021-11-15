package org.acme.patient.onboarding.utils;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.acme.patient.onboarding.app.ServiceExecutor;

import java.time.Duration;

public class ActivityStubUtils {
    // we use setScheduleToCloseTimeout for the demo
    // in order to limit the activity retry time
    // this is done so we don't have to wait too long in demo to show failure
    public static ServiceExecutor getActivitiesStub() {
        return Workflow.newActivityStub(
                ServiceExecutor.class,
                ActivityOptions.newBuilder()
                        .setScheduleToCloseTimeout(Duration.ofSeconds(60))
                        .setRetryOptions(RetryOptions.newBuilder()
                                .setBackoffCoefficient(1)
                                .build())
                        .build());
    }
}
