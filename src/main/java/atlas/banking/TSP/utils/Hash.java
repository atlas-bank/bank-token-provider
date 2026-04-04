package atlas.banking.TSP.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class Hash {
    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public String encode(String data) {
        return encoder.encode(data);
    }

    public boolean validateHash(String rawData, String hashedData) {
        return encoder.matches(rawData, hashedData);
    }
}
