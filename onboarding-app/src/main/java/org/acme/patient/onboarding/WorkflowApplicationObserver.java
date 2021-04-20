package org.acme.patient.onboarding;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.acme.patient.onboarding.app.ServiceExecutionImpl;
import org.acme.patient.onboarding.utils.OnboardingServiceClient;
import org.acme.patient.onboarding.app.OnboardingImpl;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class WorkflowApplicationObserver {

    private WorkflowClient client;
    private WorkerFactory factory;

    @ConfigProperty(name = "onboarding.task.queue")
    String taskQueue;

    @Inject
    @RestClient
    OnboardingServiceClient serviceClient;

    void onStart(@Observes StartupEvent ev) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        client = WorkflowClient.newInstance(service);
        factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(taskQueue);

        worker.registerWorkflowImplementationTypes(OnboardingImpl.class);
        worker.registerActivitiesImplementations(new ServiceExecutionImpl(serviceClient));

        factory.start();
    }

    void onStop(@Observes ShutdownEvent ev) {
        factory.shutdown();
    }

    public WorkflowClient getClient() {
        return client;
    }
}
