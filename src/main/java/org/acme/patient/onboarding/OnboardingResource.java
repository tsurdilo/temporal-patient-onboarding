package org.acme.patient.onboarding;

import io.temporal.client.WorkflowOptions;
import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.wokflow.OnboardingWorkflow;
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

    @POST
    public Patient doOnboard(Patient patient) {
        // start a new workflow execution
        // use the patient name for the unique workflow id..irl this would be some unique generated id maybe
        OnboardingWorkflow workflow =
                observer.getClient().newWorkflowStub(
                        OnboardingWorkflow.class, WorkflowOptions.newBuilder()
                                .setWorkflowId("patientonboarding-" + patient.getName().replaceAll(" ", "-").toLowerCase())
                                .setTaskQueue(taskQueue).build());

        return workflow.onboardNewPatient(patient);
    }
}