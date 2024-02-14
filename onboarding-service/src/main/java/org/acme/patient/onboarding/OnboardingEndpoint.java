package org.acme.patient.onboarding;

import org.acme.patient.onboarding.model.Doctor;
import org.acme.patient.onboarding.model.Hospital;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@ApplicationScoped
@Path("onboard")
@Tag(name = "Onboarding Services Endpoints")
public class OnboardingEndpoint {

    @Inject
    List<Hospital> hospitals;

    @Inject
    List<Doctor> doctors;

    @POST
    @Path("assignhospital")
    public synchronized Hospital assignHospitalToPatient(String zip) {
        Hospital hospital = hospitals.stream().filter(h -> h.getZip().equals(zip))
                .findFirst()
                .orElse(new Hospital("Local Hospital","123 Local Street", "555-55-5555", "12345"));

        return hospital;
    }

    @POST
    @Path("assigndoctor")
    public synchronized Doctor assignDoctorToPatient(String condition) {
        Doctor doctor = doctors.stream().filter(d -> d.getSpecialty().equals(condition))
                .findFirst()
                .orElse(new Doctor("Michael Scott", "img/docfemale.png", "General"));
        return doctor;
    }

    @POST
    @Path("notify")
    public synchronized void notifyPatient(String contact) {
        // do nothing here for demo...
        // irl would send email or text message or both
    }
}

