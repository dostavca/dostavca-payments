import com.codahale.metrics.Counter;
import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.eclipse.microprofile.health.Health;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Created by Aljaz on 14/12/2017.
 */
@Log
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("payments")
@Health
public class PaymentsResource {

    @Inject
    @DiscoverService(value = "dostavca-payment-calculator", version = "1.0.x", environment = "dev")
    Optional<WebTarget> paymentCalculator;

    @POST
    @Metered(name = "transfer-funds-meter")
    @Path("transfer-funds")
    public Response transferFunds(Transfer transfer) {
        return Response.ok().build();
    }

    @GET
    @Metered(name = "check-balance-meter")
    @Path("check-balance")
    public Response checkBalance(User user) {
        return Response.ok(user.getBalance()).build();
    }

    public Response getRecommendedPayFallback() {
        return Response.ok("{\"message\": \"Payment recommendations currently under maintenance.\"}").build();
    }

    @POST
    @Path("recommended-pay")
    @Metered
    @CircuitBreaker
    @Fallback(fallbackMethod = "getRecommendedPayFallback")
    public Response recommendedPay() {

        Transport transport = new Transport(new Packet(5), 100);

        if (!paymentCalculator.isPresent()) {
            return Response.ok().entity("{ \"message\": \"Payment calculator currently not available.\" }").build();
        }

        WebTarget service = paymentCalculator.get().path("v1/payment-calculator/calculate-payment");

        Response response;

        try {
            response = service.request().post(Entity.json(transport));
        } catch (ProcessingException e) {
            return Response.status(408).build();
        }

        return Response.fromResponse(response).build();
    }

}
