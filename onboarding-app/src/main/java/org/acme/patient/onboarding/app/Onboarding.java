package org.acme.patient.onboarding.app;

import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import org.acme.patient.onboarding.model.Patient;


@WorkflowInterface
public interface Onboarding {

    @WorkflowMethod
    Patient onboardNewPatient(Patient patient);

    @QueryMethod
    String getStatus();
}


