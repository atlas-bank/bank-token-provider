package atlas.banking.TSP.utils;


import atlas.banking.TSP.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyValidation implements HandlerInterceptor {

    private final String API_KEY_INTERNAL = System.getenv("API_KEY_INTERNAL");

    public void validateApiKey(String apiKey) throws UnauthorizedException {
        if (API_KEY_INTERNAL == null || API_KEY_INTERNAL.isBlank()) {
            throw new UnauthorizedException("OS API-KEY is empty");
        }
        if (apiKey == null) {
            throw new UnauthorizedException("Header apikey is null");
        }
        if (!API_KEY_INTERNAL.equals(apiKey)) {
            throw new UnauthorizedException("Invalid API-KEY");
        }

    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws UnauthorizedException {

        String apiKey = request.getHeader("x-api-key");

        validateApiKey(apiKey);

        return true;
    }
}
