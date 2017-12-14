import com.codahale.metrics.Counter;
import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.eclipse.microprofile.health.Health;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Aljaz on 25/10/2017.
 */
@Log
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("profile")
@Health
public class PaymentsResource {

    static boolean running = true;

    public static boolean isRunning() {
        return running;
    }

    @POST
    @Metered(name = "packets-delivered-meter")
    @Path("packets-delivered")
    public Response packetsDelivered() {

        return Response.ok().build();
    }

    @GET
    @Path("kill-service")
    public Response killService() {
        running = !running;
        return Response.ok(running).build();
    }

    @GET
    @Path("fibonacci-load/{num}")
    public Response fibonacciLoad(@PathParam("num") int num) {
        return Response.ok(fibonacci(num)).build();
    }

    private int fibonacci(int n)  {
        if (n == 0)
            return 0;
        else if(n == 1)
            return 1;
        else
            return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
