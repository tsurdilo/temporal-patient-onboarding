package org.acme.patient.onboarding;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OnboardingMessagesReactor {

    @Incoming("incoming-messages")
    @Outgoing("outgoing-messages")
    String convert(String message) {
        return message;
    }
}
