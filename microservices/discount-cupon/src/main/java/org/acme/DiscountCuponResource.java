package org.acme;

import java.net.URI;
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


@Path("DiscountCupon")
public class DiscountCuponResource {

    @Inject
    io.vertx.mutiny.mysqlclient.MySQLPool client;
    
    @Inject
    @ConfigProperty(name = "myapp.schema.create", defaultValue = "true") 
    boolean schemaCreate ;

    @ConfigProperty(name = "kafka.bootstrap.servers") 
    String kafka_servers;

    @Channel("discountCupon")
    Emitter<String> emitter;

    void config(@Observes StartupEvent ev) {
        if (schemaCreate) {
            initdb();
        }
    }

    
    private void initdb() {
            client.query("CREATE TABLE IF NOT EXISTS DiscountCupon (id SERIAL PRIMARY KEY, idLoyaltyCard BIGINT UNSIGNED, expirationDate DATETIME, discount INT)").execute()
            .flatMap(r -> client.query("CREATE TABLE IF NOT EXISTS DiscountCuponShops (idDiscountCupon BIGINT UNSIGNED, idShop BIGINT UNSIGNED, PRIMARY KEY (idDiscountCupon, idShop))").execute())
            .await().indefinitely();
    }

    
    @GET
    public Multi<DiscountCupon> get() {
            return DiscountCupon.findAll(client);
        }
    
    @GET
    @Path("{id}")
    public Uni<Response> getSingle(Long id) {
        return DiscountCupon.findById(client, id)
        .onItem().transform(discountCupon -> discountCupon != null ? Response.ok(discountCupon) : Response.status(Response.Status.NOT_FOUND)) 
                .onItem().transform(ResponseBuilder::build);
    }
     
    @POST
    public Uni<Response> create(DiscountCupon discountCupon) {
        return discountCupon
                .save(client, discountCupon.idLoyaltyCard, discountCupon.idsShops, discountCupon.discount, discountCupon.expirationDate)
                .onItem().transform(id -> {
                    String message = "{id=" + id +", idLoyaltyCard=" + discountCupon.idLoyaltyCard + ", idsShops="
                            + (discountCupon.idsShops != null
                                    ? java.util.Arrays.toString(discountCupon.idsShops)
                                    : "null")
                            + ", discount=" + discountCupon.discount
                            + ", expirationDate=" + discountCupon.expirationDate + "}";
                    emitter.send(message)
                            .whenComplete((success, failure) -> {
                                if (failure != null) {
                                    System.err.println("Failed to send message to Kafka DiscountCupon Topic: " + failure.getMessage());
                                }
                            });
                    return URI.create("/discountCupon/" + id);
                })
                .onItem().transform(uri -> Response.created(uri).build());
    }
    
    @DELETE
    @Path("{id}")
    public Uni<Response> delete(Long id) {
        return DiscountCupon.delete(client, id)
                .onItem().transform(deleted -> deleted ? Response.Status.NO_CONTENT : Response.Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }
}
