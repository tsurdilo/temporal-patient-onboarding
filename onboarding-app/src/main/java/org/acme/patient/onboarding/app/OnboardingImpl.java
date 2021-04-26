package org.acme.patient.onboarding.app;

import io.temporal.workflow.Saga;
import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.utils.ActivityStubUtils;
import org.apache.commons.codec.binary.StringUtils;

public class OnboardingImpl implements Onboarding {

    ServiceExecutor serviceExecutor = ActivityStubUtils.getActivitiesStubWithRetries();
    Saga saga = new Saga(new Saga.Options.Builder().setParallelCompensation(false).build());

    String status;
    Patient onboardingPatient;

    @Override
    public Patient onboardNewPatient(Patient patient) {
        onboardingPatient = patient;

        saga.addCompensation(() -> {
            status = "Compensating Onboarding for: " + onboardingPatient.getName();
            this.onboardingPatient =  serviceExecutor.compensateOnboarding(this.onboardingPatient);
        });

        try {
            // 1. assign hospital to patient
            status = "Assigning hospital to patient: " + onboardingPatient.getName();
            onboardingPatient.setHospital(
                    serviceExecutor.assignHospitalToPatient(onboardingPatient.getZip()));

            // 2. assign doctor to patient
            status = "Assigning doctor to patient: " + onboardingPatient.getName();
            onboardingPatient.setDoctor(
                    serviceExecutor.assignDoctorToPatient(onboardingPatient.getCondition()));

            // 3. notify patient
            status = "Notifying patient: " + onboardingPatient.getName();
            switch (onboardingPatient.getContactMethod()) {
                case PHONE:
                    serviceExecutor.notifyViaEmail(onboardingPatient.getEmail());
                    break;

                case TEXT:
                    serviceExecutor.notifyViaText(onboardingPatient.getPhone());
                    break;
            }

            // 4. finalize onboarding
            status = "Finalizing onboarding for: " + onboardingPatient.getName();
            patient.setOnboarded(
                    serviceExecutor.finalizeOnboarding());

        } catch (Exception e) {
            saga.compensate();
        }

        return onboardingPatient;
    }

    @Override
    public String getStatus() {
        return status;
    }
    
}
