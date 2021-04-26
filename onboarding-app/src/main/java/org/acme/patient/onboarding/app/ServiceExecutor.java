package org.acme.patient.onboarding.app;

import io.temporal.activity.ActivityInterface;
import org.acme.patient.onboarding.model.Patient;

@ActivityInterface
public interface ServiceExecutor {
    Patient storeNewPatient(Patient patient);
    Patient assignHospitalToPatient(Patient patient);
    Patient assignDoctorToPatient(Patient patient);
    Patient notifyPatient(Patient patient);
    Patient compensateOnboarding(Patient patient);
}
