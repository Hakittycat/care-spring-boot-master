package in.joshbetz.careproject.contentful;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("contentful.data")
@Component
@Data
public class ContentfulProperties {

    private String apiKey;
    private String spaceId;

}
