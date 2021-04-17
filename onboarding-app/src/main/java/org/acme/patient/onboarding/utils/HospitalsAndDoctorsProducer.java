package org.acme.patient.onboarding.utils;

import org.acme.patient.onboarding.model.Doctor;
import org.acme.patient.onboarding.model.Hospital;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.ArrayList;
import java.util.List;

// Producer for some pre-defined hospitals and doctors in our demo app
public class HospitalsAndDoctorsProducer {

    @Produces
    @ApplicationScoped
    public List<Hospital> getParticipatingHospitals() {
        List<Hospital> hospitals = new ArrayList<>();

        // some default available hospitals
        hospitals.add(new Hospital("Northside Hospital", "12345 Some Address", "555-55-5555", "30041"));
        hospitals.add(new Hospital("Mayo Clinic", "12345 Some Address", "555-55-5555", "55902"));
        hospitals.add(new Hospital("UCLA Medical Center", "12345 Some Address", "555-55-5555", "90095"));
        hospitals.add(new Hospital("Cedars-Sinai Medical Center", "12345 Some Address", "555-55-5555", "90048"));
        hospitals.add(new Hospital("NewYork-Presbyterian Hospital", "12345 Some Address", "555-55-5555", "10065"));

        return hospitals;
    }

    @Produces
    @ApplicationScoped
    public List<Doctor> getParticipatingDoctors() {
        List<Doctor> doctors = new ArrayList<>();

        doctors.add(new Doctor("Dr. John Doe", "img/docmale.png", "Diabetes"));
        doctors.add(new Doctor("Dr. Karen Smith", "img/docfemale.png", "Anxiety"));
        doctors.add(new Doctor("Dr. David Johnson", "img/docmale.png", "Cancer"));
        doctors.add(new Doctor("Dr. Lisa Adams", "img/docfemale.png", "Asthma"));

        return doctors;
    }

}
