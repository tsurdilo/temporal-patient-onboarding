package org.acme.patient.onboarding;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.testing.WorkflowReplayer;
import io.temporal.worker.Worker;
import org.acme.patient.onboarding.app.ServiceExecutor;
import org.acme.patient.onboarding.model.Doctor;
import org.acme.patient.onboarding.model.Hospital;
import org.acme.patient.onboarding.model.Patient;
import org.acme.patient.onboarding.app.Onboarding;
import org.acme.patient.onboarding.app.OnboardingImpl;
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
        worker.registerWorkflowImplementationTypes(OnboardingImpl.class);

        client = testEnv.getWorkflowClient();
    }

    @AfterAll
    public static void tearDown() {
        testEnv.close();
    }

    @Test
    public void testMockedPatientOnboarding() {

        // mock our workflow activities
        ServiceExecutor activities = mock(ServiceExecutor.class);

        Patient testPatient = new Patient("123", "Tester", "22", "30041", "", "", "Asthma", "tester@test.io", "555-55-5555", "TEXT");
        Patient onboardedPatient = new Patient("123", "Tester", "22", "30041", "", "", "Asthma", "tester@test.io", "555-55-5555", "TEXT");
        Hospital testHospital = new Hospital();
        Doctor testDoctor = new Doctor();

        onboardedPatient.setOnboarded("yes");

        // mock activity methods
        when(activities.assignHospitalToPatient(anyString())).thenReturn(testHospital);
        when(activities.assignDoctorToPatient(anyString())).thenReturn(testDoctor);
        when(activities.assignDoctorToPatient(anyString())).thenReturn(testDoctor);
        when(activities.finalizeOnboarding()).thenReturn("yes");
        doNothing().when(activities).notifyViaEmail(anyString());
        doNothing().when(activities).notifyViaText(anyString());

        worker.registerActivitiesImplementations(activities);

        testEnv.start();

        Onboarding workflow =
                client.newWorkflowStub(
                        Onboarding.class, WorkflowOptions.newBuilder()
                                .setWorkflowId(testPatient.getId())
                                .setTaskQueue(taskQueue).build());

        // Execute a workflow waiting for it to complete.
        Patient resultPatient = workflow.onboardNewPatient(testPatient);

        // Small checks
        Assertions.assertNotNull(resultPatient);
        Assertions.assertEquals("Tester", resultPatient.getName());
        Assertions.assertEquals("yes", resultPatient.getOnboarded());
    }

    //@Test
    public void testOnboardingReplay() throws Exception {
        WorkflowReplayer.replayWorkflowExecutionFromResource(
                "testrun.json", OnboardingImpl.class);
    }


}
