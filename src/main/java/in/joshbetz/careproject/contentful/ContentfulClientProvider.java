package in.joshbetz.careproject.contentful;

import com.contentful.java.cda.CDAClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentfulClientProvider {

    private final ContentfulProperties properties;

    @Bean
    public CDAClient cdaClient() {
        return CDAClient.builder()
                .setSpace(properties.getSpaceId())
                .setToken(properties.getApiKey())
                .setEnvironment("master")
                .build();
    }
}
