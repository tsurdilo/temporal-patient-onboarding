package org.acme.patient.onboarding.app;

import io.temporal.activity.ActivityInterface;
import org.acme.patient.onboarding.model.Patient;

@ActivityInterface
public interface ServiceExecution {
    Patient storeNewPatient(Patient patient);
    Patient assignHospitalToPatient(Patient patient);
    Patient assignDoctorToPatient(Patient patient);
    Patient finalizeOnboarding(Patient patient);
    Patient compensateOnboarding(Patient patient);
}
