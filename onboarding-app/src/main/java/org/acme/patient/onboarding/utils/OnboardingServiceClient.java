package org.acme.patient.onboarding.utils;

import org.acme.patient.onboarding.model.Doctor;
import org.acme.patient.onboarding.model.Hospital;
import org.acme.patient.onboarding.model.Patient;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("onboard")
@RegisterRestClient
public interface OnboardingServiceClient {

    @POST
    @Path("assignhospital")
    Hospital assignHospitalToPatient(String zip);

    @POST
    @Path("assigndoctor")
    Doctor assignDoctorToPatient(String condition);

    @POST
    @Path("notify")
    Patient notifyPatient(String email);
}
