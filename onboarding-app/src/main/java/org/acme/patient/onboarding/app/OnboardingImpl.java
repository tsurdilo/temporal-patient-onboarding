package org.acme.patient.onboarding.app;

import io.temporal.workflow.Saga;
import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.utils.ActivityStubUtils;

public class OnboardingImpl implements Onboarding {

    ServiceExecution serviceExecution = ActivityStubUtils.getActivitiesStubWithRetries();
    String status;
    Patient onboardingPatient;

    @Override
    public Patient onboardNewPatient(Patient patient) {
        onboardingPatient = patient;

        Saga saga = new Saga(new Saga.Options.Builder().setParallelCompensation(false).build());
        saga.addCompensation(
                () -> {
                    status = "Compensating Onboarding for: " + this.onboardingPatient.getName();
                    serviceExecution.compensateOnboarding(this.onboardingPatient);
                });

        try {
            // 1. store new patient
            status = "Storing new patient: " + onboardingPatient.getName();
            onboardingPatient = serviceExecution.storeNewPatient(onboardingPatient);

            // 2. assign hospital to patient
            status = "Assigning hospital to patient: " + onboardingPatient.getName();
            onboardingPatient = serviceExecution.assignHospitalToPatient(onboardingPatient);

            // 3. assign doctor to patient
            status = "Assigning doctor to patient: " + onboardingPatient.getName();
            onboardingPatient = serviceExecution.assignDoctorToPatient(onboardingPatient);

            // 4. finalize
            status = "Finalizing patient onboarding: " + onboardingPatient.getName();
            onboardingPatient = serviceExecution.finalizeOnboarding(onboardingPatient);

            return onboardingPatient;
        } catch (Exception e) {
            saga.compensate();
            return patient;
        }
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void compensateOnboarding(Patient patient) {
        patient.setOnboarded("no");
    }
    
}
