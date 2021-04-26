package org.acme.patient.onboarding.app;

import io.temporal.workflow.Saga;
import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.utils.ActivityStubUtils;

public class OnboardingImpl implements Onboarding {

    ServiceExecutor serviceExecutor = ActivityStubUtils.getActivitiesStubWithRetries();

    String status;
    Patient onboardingPatient;

    @Override
    public Patient onboardNewPatient(Patient patient) {
        onboardingPatient = patient;

        // 1. store new patient
        status = "Storing new patient: " + onboardingPatient.getName();
        onboardingPatient = serviceExecutor.storeNewPatient(onboardingPatient);

        // 2. assign hospital to patient
        status = "Assigning hospital to patient: " + onboardingPatient.getName();
        onboardingPatient = serviceExecutor.assignHospitalToPatient(onboardingPatient);

        // 3. assign doctor to patient
        status = "Assigning doctor to patient: " + onboardingPatient.getName();
        onboardingPatient = serviceExecutor.assignDoctorToPatient(onboardingPatient);

        // 4. finalize
        status = "Notifying patient: " + onboardingPatient.getName();
        onboardingPatient = serviceExecutor.notifyPatient(onboardingPatient);

        return onboardingPatient;
    }

    @Override
    public String getStatus() {
        return status;
    }
    
}
