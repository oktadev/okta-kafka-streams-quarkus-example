package com.okta.quarkusclient;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/words")
@RequestScoped
public class SpeechResource {

    @Inject
    @Channel("words")
    Publisher<String> words;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RolesAllowed({"Everyone"})
    @SseElementType("text/plain")
    public Publisher<String> stream() {
        return words;
    }
}
