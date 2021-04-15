package org.acme.patient.onboarding.wokflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import org.acme.patient.onboarding.activities.OnboardingActivities;
import org.acme.patient.onboarding.model.Patient;
import java.time.Duration;

public class OnboardingWorkflowImpl implements OnboardingWorkflow {

    private final OnboardingActivities activities =
            Workflow.newActivityStub(
                    OnboardingActivities.class,
                    ActivityOptions.newBuilder().setScheduleToCloseTimeout(Duration.ofSeconds(10)).build());


    @Override
    public Patient onboardNewPatient(Patient patient) {
        // simple pipeline-like execution of activities
        Patient onboardingPatient = patient;
        onboardingPatient = activities.storeNewPatient(onboardingPatient);
        onboardingPatient = activities.assignHospitalToPatient(onboardingPatient);
        onboardingPatient = activities.assignDoctorToPatient(onboardingPatient);
        onboardingPatient = activities.finishOnboarding(onboardingPatient);

        return onboardingPatient;
    }
}
