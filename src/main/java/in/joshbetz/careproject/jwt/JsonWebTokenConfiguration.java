package in.joshbetz.careproject.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("jwt")
@Component
@Data
public class JsonWebTokenConfiguration {

    private String secret;

}
