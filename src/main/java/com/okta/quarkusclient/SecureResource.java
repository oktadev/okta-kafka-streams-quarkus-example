package com.okta.quarkusclient;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.jboss.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("/secured")
@RolesAllowed({"Everyone"})
public class SecureResource {

    private static final Logger LOG = Logger.getLogger(SecureResource.class);

    @Inject
    Template secured;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@Context SecurityContext ctx) {
        Principal caller =  ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        LOG.info("name: " + name);
        return secured.data("name", name);
    }
}