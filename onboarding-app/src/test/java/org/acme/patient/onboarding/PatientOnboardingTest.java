package org.acme.patient.onboarding;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;
import org.acme.patient.onboarding.activities.OnboardingActivities;
import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.wokflow.OnboardingWorkflow;
import org.acme.patient.onboarding.wokflow.OnboardingWorkflowImpl;
import org.junit.jupiter.api.*;

public class PatientOnboardingTest {

    private static TestWorkflowEnvironment testEnv;
    private static Worker worker;
    private static WorkflowClient client;
    private static String taskQueue = "TestOnboardingTaskQueue";

    @BeforeAll
    public static void setUp() {
        testEnv = TestWorkflowEnvironment.newInstance();
        worker = testEnv.newWorker(taskQueue);
        worker.registerWorkflowImplementationTypes(OnboardingWorkflowImpl.class);

        client = testEnv.getWorkflowClient();
    }

    @AfterAll
    public static void tearDown() {
        testEnv.close();
    }

    @Test
    public void testMockedPatientOnboarding() {
        OnboardingActivities activities = mock(OnboardingActivities.class);
        Patient testPatient = new Patient("Tester", "22", "30041", "", "", "Asthma");
        Patient onboardedPatient = new Patient("Tester", "22", "30041", "", "", "Asthma");
        onboardedPatient.setOnboarded("yes");


        // mock activity methods
        when(activities.storeNewPatient(any())).thenReturn(testPatient);
        when(activities.assignHospitalToPatient(any())).thenReturn(testPatient);
        when(activities.assignDoctorToPatient(any())).thenReturn(testPatient);
        when(activities.finishOnboarding(any())).thenReturn(onboardedPatient);
        worker.registerActivitiesImplementations(activities);

        testEnv.start();

        OnboardingWorkflow workflow =
                client.newWorkflowStub(
                        OnboardingWorkflow.class, WorkflowOptions.newBuilder()
                                .setWorkflowId("patientonboarding-" + testPatient.getName())
                                .setTaskQueue(taskQueue).build());

        // Execute a workflow waiting for it to complete.
        Patient resultPatient = workflow.onboardNewPatient(testPatient);

        // Small checks
        Assertions.assertNotNull(resultPatient);
        Assertions.assertEquals("Tester", resultPatient.getName());
        Assertions.assertEquals("yes", resultPatient.getOnboarded());
    }

}
