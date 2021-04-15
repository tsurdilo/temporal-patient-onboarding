# Temporal Demo: new patient onboarding

Shows Temporal workflow execution running on [Quarkus](https://quarkus.io/).
Uses the [Temporal Java SDK](https://github.com/temporalio/sdk-java).

## About
In this demo we show off a simple patient onboarding workflow.
Our workflow is exposed as a service running locally and can be accessed at end-point
`/onboard`. The application includes an UI which you can use to enter in new patient 
information and start workflow execution and see live-updates and results of the 
onboarding process (workflow).

## Running the demo

1. Clone the repository locally:

```shell script
git clone https://github.com/tsurdilo/temporal-patient-onboarding.git
cd temporal-patient-onboarding
```

2. Start the Temporal Service (docker compose): 

```shell script
 git clone https://github.com/temporalio/docker-compose.git
 cd  docker-compose
 docker-compose up
```

3. Start the demo:

```shell script
mvn clean install quarkus:dev
```

4. Access the demo UI in browser via: [http://localhost:8080](http://localhost:8080)

5. Access the Temporal Web-Ui via: [http://localhost:8088](http://localhost:8088)

## Interacting with the demo

Our demo has a simple UI that intially looks like this:

<p align="center">
<img src="img/initial-screen.png" height="400px"/>
</p>

You can type in the new patient information and here are some useful hints:

Our demo workflow activities will associate the zip code you type in with one of the 
pre-defined hospitals. To get a specific hospital you can use the following zips:
`30041`, `55902`, `90095`, `90048`, `10065`. You can type in any zip you wish but if it's not one 
of those our workflow activities will associate it with the "default" hospital.

There is a number of conditions which you can use to associate the patient with 
one of the pre-defined doctors in the demo. These are:
`Diabetes`, `Anxiety`, `Cancer`, `Asthma`. you can type in any condition you wish but if it's not one of theose
the workflow activities will associate it with the "default" doctor.

Once you fill in the new patient form click the "Onboard Patient" button.

This will start our workflow run which is associated with the new patient that we are onboarding.

The workflow is going to execute a number of activities one after another (pipeline).
When each activity executes, you will see a notification popup of the activity, for example:

<p align="center">
<img src="img/notifications.png" height="400px"/>
</p>

These notifications are pushed from our workflow activities.
There is no need to click on the notifications "OK" button. These notifications
will automatically close when the assiciated workflow activitiy has completed
and will dissapear when the workflow run completes.

After the workflow run completes, the section of bottom of the scren will update to show you
that the patient is indeed onboarded:

<p align="center">
<img src="img/onboarded.png" height="150px"/>
</p>

Now let's take a look at our Temporal Web-UI client, it will show us that our workflow execution 
has successfully completed:

<p align="center">
<img src="img/workflowruncompletion.png" height="150px"/>
</p>

You can click on the Run Id link and explore the workflow execution history.


And that's it. Hope you enjoyed running this demo :) 