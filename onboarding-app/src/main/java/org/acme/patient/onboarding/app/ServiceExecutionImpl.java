package org.acme.patient.onboarding.app;

import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.utils.OnboardingServiceClient;

public class ServiceExecutionImpl implements ServiceExecution {

    private OnboardingServiceClient serviceClient;

    public ServiceExecutionImpl(OnboardingServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @Override
    public Patient storeNewPatient(Patient patient) {
        // call onboarding service
        Patient updatedPatient = serviceClient.storeNewPatient(patient);
        // simulate some work...
        sleep(5);
        return updatedPatient;
    }

    @Override
    public Patient assignHospitalToPatient(Patient patient) {
        // call onboarding service
        Patient updatedPatient = serviceClient.assignHospitalToPatient(patient);
        // simulate some work...
        sleep(5);
        return updatedPatient;
    }

    @Override
    public Patient assignDoctorToPatient(Patient patient) {
        Patient updatedPatient = serviceClient.assignDoctorToPatient(patient);
        // simulate some work...
        sleep(5);
        return updatedPatient;
    }

    @Override
    public Patient finalizeOnboarding(Patient patient) {
        Patient updatedPatient = serviceClient.finalizeOnboarding(patient);
        // simulate some work...
        sleep(5);
        return updatedPatient;
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ee) {
            // Empty
        }
    }
}
