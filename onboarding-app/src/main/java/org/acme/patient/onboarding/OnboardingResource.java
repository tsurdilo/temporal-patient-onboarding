package org.acme.patient.onboarding;

import io.temporal.client.WorkflowOptions;
import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.app.Onboarding;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/onboard")
public class OnboardingResource {

    @Inject
    WorkflowApplicationObserver observer;

    @ConfigProperty(name = "onboarding.task.queue")
    String taskQueue;

    @ConfigProperty(name = "onboarding.id.prefix")
    String idPrefix;

    @POST
    public Patient doOnboard(Patient patient) {
        // start a new workflow execution
        // use the patient name for the unique workflow id
        Onboarding workflow =
                observer.getClient().newWorkflowStub(
                        Onboarding.class, WorkflowOptions.newBuilder()
                                .setWorkflowId(idPrefix + patient.getName().replaceAll(" ", "-").toLowerCase())
                                .setTaskQueue(taskQueue).build());

        return workflow.onboardNewPatient(patient);
    }
}