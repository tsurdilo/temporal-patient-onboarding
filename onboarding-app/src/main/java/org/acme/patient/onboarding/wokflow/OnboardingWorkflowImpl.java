package org.acme.patient.onboarding.wokflow;

import io.temporal.workflow.Saga;
import org.acme.patient.onboarding.activities.OnboardingActivities;
import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.utils.ActivityStubUtils;

public class OnboardingWorkflowImpl implements OnboardingWorkflow {

    OnboardingActivities activities = ActivityStubUtils.getActivitiesStubWithRetries();

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
