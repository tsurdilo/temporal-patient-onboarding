package org.acme.patient.onboarding.activities;

import org.acme.patient.onboarding.model.Doctor;
import org.acme.patient.onboarding.model.Hospital;
import org.acme.patient.onboarding.model.Patient;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OnboardingActivitiesImpl implements OnboardingActivities {

    private List<Hospital> hospitals;
    private List<Doctor> doctors;

    public OnboardingActivitiesImpl() {}

    public OnboardingActivitiesImpl(List<Hospital> hospitals, List<Doctor> doctors) {
        this.hospitals = hospitals;
        this.doctors = doctors;
    }

    @Override
    public Patient storeNewPatient(Patient patient) {
        sendMessage("Storing new patient: " + patient.getName());
        // simulate some work...
        sleep(5);
        return patient;
    }

    @Override
    public Patient assignHospitalToPatient(Patient patient) {
        sendMessage("Assigning hospital to patient: " + patient.getName());
        // simulate some work...
        sleep(5);
        Hospital hospital = hospitals.stream().filter(h -> h.getZip().equals(patient.getZip()))
                .findFirst()
                .orElse(new Hospital("Local Hospital","123 Local Street", "555-55-5555", "12345"));
        patient.setHospital(hospital);

        return patient;
    }

    @Override
    public Patient assignDoctorToPatient(Patient patient) {
        sendMessage("Assigning doctor to patient: " + patient.getName());
        // simulate some work...
        sleep(5);
        Doctor doctor = doctors.stream().filter(d -> d.getSpecialty().equals(patient.getCondition()))
                .findFirst()
                .orElse(new Doctor("Michael Scott", "img/docfemale.png", "General"));
        patient.setDoctor(doctor);

        return patient;
    }

    @Override
    public Patient finishOnboarding(Patient patient) {
        sendMessage("Finalizing patient onboarding: " + patient.getName());
        // simulate some work...
        sleep(5);
        patient.setOnboarded("yes");
        return patient;
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ee) {
            // Empty
        }
    }

    private void sendMessage(String message) {
        try {
            HttpClient client= new DefaultHttpClient();
            HttpPost request = new HttpPost("http://localhost:8081/messages");

            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("message", message));

            request.setEntity(new UrlEncodedFormEntity(pairs ));
            client.execute(request);
        } catch (IOException e) {
            System.out.println("Unable to send message from activity");
            throw new IllegalStateException(e.getMessage());
        }
    }
}
