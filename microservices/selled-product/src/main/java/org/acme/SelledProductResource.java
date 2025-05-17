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

@Path("SelledProduct")
public class SelledProductResource {

    @Inject
    io.vertx.mutiny.mysqlclient.MySQLPool client;

    @Inject
    @ConfigProperty(name = "myapp.schema.create", defaultValue = "true")
    boolean schemaCreate;

    @ConfigProperty(name = "kafka.bootstrap.servers")
    String kafka_servers;

    @Channel("selledProductByLoyaltyCard")
    Emitter<String> emitterLoyaltyCard;

    @Channel("selledProductByShop")
    Emitter<String> emitterShop;

    @Channel("selledProductByLocation")
    Emitter<String> emitterLocation;

    @Channel("selledProductByCustomer")
    Emitter<String> emitterCustomer;

    @Channel("selledProductByCoupon")
    Emitter<String> emitterCoupon;

    void config(@Observes StartupEvent ev) {
        if (schemaCreate) {
            initdb();
        }
    }

    private void initdb() {
        client.query(
                "CREATE TABLE IF NOT EXISTS SelledProduct (id SERIAL PRIMARY KEY, idLoyaltyCard BIGINT UNSIGNED, idCustomer BIGINT UNSIGNED, idDiscountCupon BIGINT UNSIGNED NULL, idShop BIGINT UNSIGNED, idPurchase BIGINT UNSIGNED, location VARCHAR(255))")
                .execute()
                .await().indefinitely();
    }

    @GET
    public Multi<SelledProduct> get() {
        return SelledProduct.findAll(client);
    }

    @GET
    @Path("{id}")
    public Uni<Response> getSingle(Long id) {
        return SelledProduct.findById(client, id)
                .onItem()
                .transform(selledProduct -> selledProduct != null
                        ? Response.ok(selledProduct)
                        : Response.status(Response.Status.NOT_FOUND))
                .onItem().transform(ResponseBuilder::build);
    }

    @POST
    public Uni<Response> create(SelledProduct selledProduct) {
        return selledProduct
                .save(client, selledProduct.idLoyaltyCard,
                        selledProduct.idCustomer,
                        selledProduct.idDiscountCupon,
                        selledProduct.idShop,
                        selledProduct.idPurchase,
                        selledProduct.location)
                .onItem().transform(id -> {
                    String message = "{id="+id+", idLoyaltyCard:" + selledProduct.idLoyaltyCard
                            + ", idCustomer:" + selledProduct.idCustomer
                            + ", idDiscountCupon:" + selledProduct.idDiscountCupon
                            + ", idShop:" + selledProduct.idShop
                            + ", idPurchase:" + selledProduct.idPurchase
                            + ", location:'" + selledProduct.location + "'}";

                    emitterLoyaltyCard.send(message).whenComplete((success, failure) -> {
                        if (failure != null) {
                            System.err.println(
                                    "Failed to send message to Kafka - byLoyaltyCard: " + failure.getMessage());
                        }
                    });
                    emitterShop.send(message).whenComplete((success, failure) -> {
                        if (failure != null) {
                            System.err.println(
                                    "Failed to send message to Kafka - byShop: " + failure.getMessage());
                        }
                    });
                    emitterLocation.send(message).whenComplete((success, failure) -> {
                        if (failure != null) {
                            System.err.println(
                                    "Failed to send message to Kafka - byLocation: " + failure.getMessage());
                        }
                    });
                    emitterCustomer.send(message).whenComplete((success, failure) -> {
                        if (failure != null) {
                            System.err.println(
                                    "Failed to send message to Kafka - byCustomer: " + failure.getMessage());
                        }
                    });
                    emitterCoupon.send(message).whenComplete((success, failure) -> {
                        if (failure != null) {
                            System.err.println(
                                    "Failed to send message to Kafka - byCoupon: " + failure.getMessage());
                        }
                    });

                    return URI.create("/crossSellingRecommendation/" + id);
                })
                .onItem().transform(uri -> Response.created(uri).build());
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(Long id) {
        return SelledProduct.delete(client, id)
                .onItem().transform(deleted -> deleted ? Response.Status.NO_CONTENT : Response.Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }
}
