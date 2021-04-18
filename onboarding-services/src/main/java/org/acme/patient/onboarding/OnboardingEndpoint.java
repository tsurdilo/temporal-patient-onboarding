package org.acme.patient.onboarding;

import org.acme.patient.onboarding.model.Doctor;
import org.acme.patient.onboarding.model.Hospital;
import org.acme.patient.onboarding.model.Patient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@Path("onboard")
@ApplicationScoped
public class OnboardingEndpoint {

    @Inject
    List<Hospital> hospitals;

    @Inject
    List<Doctor> doctors;

    private String message = "";

    @POST
    @Path("storenew")
    public synchronized Patient storeNewPatient(Patient patient) {
        this.message = "Storing new patient: " + patient.getName();
        return patient;
    }

    @POST
    @Path("assignhospital")
    public synchronized Patient assignHospitalToPatient(Patient patient) {
        this.message = "Assigning hospital to patient: " + patient.getName();

        Hospital hospital = hospitals.stream().filter(h -> h.getZip().equals(patient.getZip()))
                .findFirst()
                .orElse(new Hospital("Local Hospital","123 Local Street", "555-55-5555", "12345"));
        patient.setHospital(hospital);

        return patient;
    }

    @POST
    @Path("assigndoctor")
    public synchronized Patient assignDoctorToPatient(Patient patient) {
        this.message = "Assigning doctor to patient: " + patient.getName();

        Doctor doctor = doctors.stream().filter(d -> d.getSpecialty().equals(patient.getCondition()))
                .findFirst()
                .orElse(new Doctor("Michael Scott", "img/docfemale.png", "General"));
        patient.setDoctor(doctor);

        return patient;
    }

    @POST
    @Path("finalize")
    public synchronized Patient finalizeOnboarding(Patient patient) {
        this.message = "Finalizing patient onboarding: " + patient.getName();
        patient.setOnboarded("yes");
        return patient;
    }

    @GET
    public synchronized String getMessage() {
        return message;
    }
}

