
import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by Aljaz on 14/12/2017.
 */

@Health
@ApplicationScoped
public class HealthCheckBean implements HealthCheck {

    private static final String url = "https://paypal.com";

    private static final Logger LOG = Logger.getLogger(HealthCheckBean.class.getSimpleName());

    @Override
    public HealthCheckResponse call() {
        try {

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");

            if (connection.getResponseCode() == 200) {
                return HealthCheckResponse.named(HealthCheckBean.class.getSimpleName()).up().build();
            }
        } catch (Exception exception) {
            LOG.severe(exception.getMessage());
        }
        return HealthCheckResponse.named(HealthCheckBean.class.getSimpleName()).down().build();
    }
}