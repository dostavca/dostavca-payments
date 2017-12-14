import com.kumuluz.ee.discovery.annotations.RegisterService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Created by Aljaz on 25/10/2017.
 */
@RegisterService
@ApplicationPath("v1")
public class PaymentsApplication extends Application {
}
