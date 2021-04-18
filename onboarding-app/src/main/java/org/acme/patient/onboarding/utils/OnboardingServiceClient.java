package org.acme.patient.onboarding.utils;

import org.acme.patient.onboarding.model.Patient;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("onboard")
@RegisterRestClient
public interface OnboardingServiceClient {

    @POST
    @Path("storenew")
    Patient storeNewPatient(Patient patient);

    @POST
    @Path("assignhospital")
    Patient assignHospitalToPatient(Patient patient);

    @POST
    @Path("assigndoctor")
    Patient assignDoctorToPatient(Patient patient);

    @POST
    @Path("finalize")
    Patient finalizeOnboarding(Patient patient);
}
