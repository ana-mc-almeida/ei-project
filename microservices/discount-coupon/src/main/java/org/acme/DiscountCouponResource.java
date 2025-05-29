package org.acme;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

@Path("DiscountCoupon")
public class DiscountCouponResource {

    @Inject
    io.vertx.mutiny.mysqlclient.MySQLPool client;

    @Inject
    @ConfigProperty(name = "myapp.schema.create", defaultValue = "true")
    boolean schemaCreate;

    @ConfigProperty(name = "kafka.bootstrap.servers")
    String kafka_servers;

    @Channel("discountCoupon")
    Emitter<String> emitter;

    void config(@Observes StartupEvent ev) {
        if (schemaCreate) {
            initdb();
        }
    }

    private void initdb() {
        client.query(
                "CREATE TABLE IF NOT EXISTS DiscountCoupon (id SERIAL PRIMARY KEY, idLoyaltyCard BIGINT UNSIGNED, expirationDate DATETIME, discount INT)")
                .execute()
                .flatMap(r -> client.query(
                        "CREATE TABLE IF NOT EXISTS DiscountCouponShops (idDiscountCoupon BIGINT UNSIGNED, idShop BIGINT UNSIGNED, PRIMARY KEY (idDiscountCoupon, idShop))")
                        .execute())
                .await().indefinitely();
    }

    @GET
    public Multi<DiscountCoupon> get() {
        return DiscountCoupon.findAll(client);
    }

    @GET
    @Path("{id}")
    public Uni<Response> getSingle(Long id) {
        return DiscountCoupon.findById(client, id)
                .onItem()
                .transform(discountCoupon -> discountCoupon != null ? Response.ok(discountCoupon)
                        : Response.status(Response.Status.NOT_FOUND))
                .onItem().transform(ResponseBuilder::build);
    }

    @POST
    public Uni<Response> create(DiscountCoupon discountCoupon) {
        return discountCoupon
                .save(client, discountCoupon.idLoyaltyCard, discountCoupon.idsShops, discountCoupon.discount,
                        discountCoupon.expirationDate)
                .onItem().transform(id -> {
                    String message = "{id=" + id + ", idLoyaltyCard=" + discountCoupon.idLoyaltyCard + ", idsShops="
                            + (discountCoupon.idsShops != null
                                    ? java.util.Arrays.toString(discountCoupon.idsShops)
                                    : "null")
                            + ", discount=" + discountCoupon.discount
                            + ", expirationDate=" + discountCoupon.expirationDate + "}";
                    emitter.send(message)
                            .whenComplete((success, failure) -> {
                                if (failure != null) {
                                    System.err.println("Failed to send message to Kafka DiscountCoupon Topic: "
                                            + failure.getMessage());
                                }
                            });
                    return URI.create("/discountCoupon/" + id);
                })
                .onItem().transform(uri -> Response.created(uri).build());
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(Long id) {
        return DiscountCoupon.delete(client, id)
                .onItem().transform(deleted -> deleted ? Response.Status.NO_CONTENT : Response.Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

    @GET
    @Path("health")
    public Uni<Response> health() {
        return DiscountCoupon.checkDatabaseConnection(client)
                .onItem().transform(this::buildHealthResponse)
                .onFailure().recoverWithItem(this::buildErrorResponse);
    }

    private Response buildHealthResponse(Boolean dbHealthy) {
        Map<String, Object> healthStatus = new HashMap<>();
        healthStatus.put("status", dbHealthy ? "UP" : "DOWN");
        healthStatus.put("timestamp", System.currentTimeMillis());
        
        Map<String, Object> checks = new HashMap<>();
        checks.put("database", dbHealthy ? "UP" : "DOWN");
        checks.put("kafka_servers", kafka_servers != null ? "CONFIGURED" : "NOT_CONFIGURED");
        
        healthStatus.put("checks", checks);
        
        Response.Status responseStatus = dbHealthy ? Response.Status.OK : Response.Status.SERVICE_UNAVAILABLE;
        return Response.status(responseStatus).entity(healthStatus).build();
    }

    private Response buildErrorResponse(Throwable throwable) {
        Map<String, Object> healthStatus = new HashMap<>();
        healthStatus.put("status", "DOWN");
        healthStatus.put("timestamp", System.currentTimeMillis());
        healthStatus.put("error", throwable.getMessage());
        
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(healthStatus)
                .build();
    }
}
