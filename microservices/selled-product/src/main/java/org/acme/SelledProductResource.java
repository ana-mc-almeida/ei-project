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

    @Channel("selledProductByProduct")
    Emitter<String> emitterProduct;

    void config(@Observes StartupEvent ev) {

    }

    private Emitter<String> getEmitterForType(SelledProduct.TypeOfAnalysis typeOfAnalysis) {
        switch (typeOfAnalysis) {
            case LOYALTY_CARD:
                return emitterLoyaltyCard;
            case CUSTOMER:
                return emitterCustomer;
            case DISCOUNT_COUPON:
                return emitterCoupon;
            case SHOP:
                return emitterShop;
            case PRODUCT:
                return emitterProduct;
            case POSTAL_CODE:
                return emitterLocation;
            default:
                throw new IllegalArgumentException("Unknown TypeOfAnalysis: " + typeOfAnalysis);
        }
    }

    @POST
    public Uni<Response> create(SelledProduct selledProduct) {
        String message = selledProduct.toString();
        Emitter<String> emitter = getEmitterForType(selledProduct.typeOfAnalysis);
        emitter.send(message)
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        System.err.println("Failed to send message to Kafka Topic: "
                                + failure.getMessage());
                    }
                });
        return Uni.createFrom().item(Response.accepted().entity("Message sent to Kafka Topic: " + message).build());
    }
}
