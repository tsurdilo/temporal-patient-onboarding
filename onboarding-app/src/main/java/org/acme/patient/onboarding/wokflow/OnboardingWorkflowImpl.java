package org.acme.patient.onboarding.wokflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import org.acme.patient.onboarding.activities.OnboardingActivities;
import org.acme.patient.onboarding.model.Patient;
import java.time.Duration;

public class OnboardingWorkflowImpl implements OnboardingWorkflow {

    private final OnboardingActivities activities =
            Workflow.newActivityStub(
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


    @Override
    public Patient onboardNewPatient(Patient patient) {
        Patient onboardingPatient = patient;

        Saga saga = new Saga(new Saga.Options.Builder().setParallelCompensation(false).build());
        saga.addCompensation(
                () -> {
                    System.out.println("*********** PERFORMING COMPENSATION");
                    patient.setOnboarded("no");
                });


        try {
            // simple pipeline-like execution of activities
            onboardingPatient = activities.storeNewPatient(onboardingPatient);
            onboardingPatient = activities.assignHospitalToPatient(onboardingPatient);
            onboardingPatient = activities.assignDoctorToPatient(onboardingPatient);
            onboardingPatient = activities.finishOnboarding(onboardingPatient);

            return onboardingPatient;
        } catch (Exception e) {
            saga.compensate();
            return patient;
        }
    }
}
