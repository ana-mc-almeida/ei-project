package org.acme;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

@Path("CrossSellingRecommendation")
public class CrossSellingRecommendationResource {

    @Inject
    io.vertx.mutiny.mysqlclient.MySQLPool client;

    @Inject
    @ConfigProperty(name = "myapp.schema.create", defaultValue = "true")
    boolean schemaCreate;

    @ConfigProperty(name = "kafka.bootstrap.servers")
    String kafka_servers;

    @Channel("crossSellingRecommendation")
    Emitter<String> emitter;

    void config(@Observes StartupEvent ev) {

    }

    @POST
    public Uni<Response> create(CrossSellingRecommendation crossSellingRecommendation) {
        String message = crossSellingRecommendation.toString();
        emitter.send(message)
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        System.err.println("Failed to send message to Kafka Topic: " + failure.getMessage());
                    }
                });
        return Uni.createFrom().item(Response.accepted().entity("Message sent to Kafka Topic: " + message).build());
    }
}
