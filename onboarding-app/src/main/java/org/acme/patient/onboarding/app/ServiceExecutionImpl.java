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
        patient = serviceClient.assignHospitalToPatient(patient);
        // simulate some work...
        sleep(5);
        return patient;
    }

    @Override
    public Patient assignDoctorToPatient(Patient patient) {
        patient = serviceClient.assignDoctorToPatient(patient);
        // simulate some work...
        sleep(5);
        return patient;
    }

    @Override
    public Patient notifyPatient(Patient patient) {
        patient = serviceClient.notifyPatient(patient);
        // simulate some work...
        sleep(5);
        return patient;
    }

    @Override
    public Patient compensateOnboarding(Patient patient) {
        patient.setOnboarded("no");
        // simulate some work...
        sleep(5);
        return patient;
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ee) {
            // Empty
        }
    }
}
