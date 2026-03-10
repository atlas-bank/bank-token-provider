package atlas.banking.TSP.configs;

import atlas.banking.TSP.utils.ApiKeyValidation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ApiKeyValidation apiKeyValidation;

    public WebConfig(ApiKeyValidation apiKeyValidation) {
        this.apiKeyValidation = apiKeyValidation;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyValidation)
                .addPathPatterns("/**"); // todos endpoints
    }
}
