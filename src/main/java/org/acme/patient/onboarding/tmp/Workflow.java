package org.acme.patient.onboarding.tmp;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

        @WorkflowInterface
        public interface Workflow {

            @WorkflowMethod
            String start(String language);

        }




