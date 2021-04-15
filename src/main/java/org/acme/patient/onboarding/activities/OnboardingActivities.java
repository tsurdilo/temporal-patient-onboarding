package org.acme.patient.onboarding.activities;

import io.temporal.activity.ActivityInterface;
import org.acme.patient.onboarding.model.Patient;

@ActivityInterface
public interface OnboardingActivities {
    Patient storeNewPatient(Patient patient);
    Patient assignHospitalToPatient(Patient patient);
    Patient assignDoctorToPatient(Patient patient);
    Patient finishOnboarding(Patient patient);
}
