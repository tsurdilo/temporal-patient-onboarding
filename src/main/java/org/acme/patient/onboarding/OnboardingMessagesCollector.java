package org.acme.patient.onboarding;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/messages-collector")
@ApplicationScoped
public class OnboardingMessagesCollector {
    private String message = "";

    @POST
    public synchronized void consumeMessage(String message) {
        this.message = message;
    }

    @GET
    public synchronized String getMessage() {
        return message;
    }
}

