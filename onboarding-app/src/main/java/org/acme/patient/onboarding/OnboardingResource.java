package org.acme.patient.onboarding;

import io.temporal.client.WorkflowOptions;
import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.app.Onboarding;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.*;

@Path("/onboard")
public class OnboardingResource {

    @Inject
    WorkflowApplicationObserver observer;

    @ConfigProperty(name = "onboarding.task.queue")
    String taskQueue;

    @POST
    public Patient doOnboard(Patient patient) {
        // start a new workflow execution
        // use the patient id for the unique id
        Onboarding workflow =
                observer.getClient().newWorkflowStub(
                        Onboarding.class, WorkflowOptions.newBuilder()
                                .setWorkflowId(patient.getId())
                                .setTaskQueue(taskQueue).build());

        return workflow.onboardNewPatient(patient);
    }

    @GET
    public String getStatus(@QueryParam("id") String patientId) {
        // query workflow to get the status message
        try {
            Onboarding workflow = observer.getClient().newWorkflowStub(Onboarding.class, patientId);
            return workflow.getStatusMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to query workflow with id: " + patientId;
        }
    }
}