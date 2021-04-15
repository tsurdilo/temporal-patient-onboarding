package org.acme.patient.onboarding;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.acme.patient.onboarding.activities.OnboardingActivitiesImpl;
import org.acme.patient.onboarding.wokflow.OnboardingWorkflowImpl;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class WorkflowApplicationObserver {

    private WorkflowClient client;
    private WorkerFactory factory;

    @ConfigProperty(name = "onboarding.task.queue")
    String taskQueue;

    void onStart(@Observes StartupEvent ev) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        client = WorkflowClient.newInstance(service);
        factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(taskQueue);

        worker.registerWorkflowImplementationTypes(OnboardingWorkflowImpl.class);
        worker.registerActivitiesImplementations(new OnboardingActivitiesImpl());

        factory.start();
    }

    void onStop(@Observes ShutdownEvent ev) {
        factory.shutdown();
    }

    public WorkflowClient getClient() {
        return client;
    }
}
