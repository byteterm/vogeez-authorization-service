package systems.tat.authorization.service.config.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Configuration
public class oAuth2DataConfiguration {

    @Value("${tat.authorization-service.oauth2.issuer:http://127.0.0.1:8080}")
    public String oauth2Issuer;
}
