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
            // 1. store new patient
            onboardingPatient = activities.storeNewPatient(onboardingPatient);
            // 2. assign hospital to patient
            onboardingPatient = activities.assignHospitalToPatient(onboardingPatient);
            // 3. assign doctor to patient
            onboardingPatient = activities.assignDoctorToPatient(onboardingPatient);
            // 4. finalize
            onboardingPatient = activities.finalizeOnboarding(onboardingPatient);

            return onboardingPatient;
        } catch (Exception e) {
            saga.compensate();
            return patient;
        }
    }
}
