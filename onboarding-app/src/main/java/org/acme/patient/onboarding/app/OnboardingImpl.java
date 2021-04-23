package org.acme.patient.onboarding.app;

import io.temporal.workflow.Saga;
import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.utils.ActivityStubUtils;

public class OnboardingImpl implements Onboarding {

    ServiceExecution activities = ActivityStubUtils.getActivitiesStubWithRetries();
    String statusMessage;

    @Override
    public Patient onboardNewPatient(Patient patient) {
        Patient onboardingPatient = patient;

        Saga saga = new Saga(new Saga.Options.Builder().setParallelCompensation(false).build());
        saga.addCompensation(
                () -> {
                    System.out.println("WORKFLOW: PERFORMING COMPENSATION");
                    patient.setOnboarded("no");
                });


        try {
            // simple pipeline-like execution of activities
            // 1. store new patient
            this.statusMessage = "Storing new patient: " + patient.getName();
            onboardingPatient = activities.storeNewPatient(onboardingPatient);

            // 2. assign hospital to patient
            this.statusMessage = "Assigning hospital to patient: " + patient.getName();
            onboardingPatient = activities.assignHospitalToPatient(onboardingPatient);

            // 3. assign doctor to patient
            this.statusMessage = "Assigning doctor to patient: " + patient.getName();
            onboardingPatient = activities.assignDoctorToPatient(onboardingPatient);

            // 4. finalize
            this.statusMessage = "Finalizing patient onboarding: " + patient.getName();
            onboardingPatient = activities.finalizeOnboarding(onboardingPatient);

            return onboardingPatient;
        } catch (Exception e) {
            saga.compensate();
            return patient;
        }
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }
    
}
