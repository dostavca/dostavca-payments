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
 * Created by Aljaz on 14/12/2017.
 */
@Log
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("payments")
@Health
public class PaymentsResource {

    @POST
    @Metered(name = "transfer-funds-meter")
    @Path("transfer-funds")
    public Response transferFunds(Transfer transfer) {
        
        return Response.ok().build();
    }


}
