package atlas.banking.TSP.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;


@Component
public class Generator {


    private static final String SECRET = System.getenv("SECRET");
    private static final String BIN = "599990";


    private String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    public String generateCVV(String cpf, String pan, Instant createdAt) {
        try {
            String data = cpf + pan + createdAt.toString();

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
            mac.init(secretKey);

            byte[] hashBytes = mac.doFinal(data.getBytes());
            String hash = bytesToHex(hashBytes);

            String numbers = hash.replaceAll("[^0-9]", "");

            return numbers.substring(0, 3);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Erro ao gerar CVV", e);
        }
    }

    public String generatePan(String cpf, Instant createdAt) {
        try {
            String data = cpf + createdAt.toString();

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
            mac.init(key);

            byte[] hash = mac.doFinal(data.getBytes());
            String hex = bytesToHex(hash);

            String numbers = hex.replaceAll("[^0-9]", "");

            String panWithoutCheck = BIN + numbers.substring(0, 9);

            return panWithoutCheck + calculateLuhnDigit(panWithoutCheck);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PAN", e);
        }
    }

    private int calculateLuhnDigit(String number) {
        int sum = 0;
        boolean alternate = true;

        for (int i = number.length() - 1; i >= 0; i--) {
            int n = number.charAt(i) - '0';

            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }

            sum += n;
            alternate = !alternate;
        }

        return (10 - (sum % 10)) % 10;
    }
    public String deterministicHashCpf(String cpf) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
            mac.init(keySpec);
            byte[] hashBytes = mac.doFinal(cpf.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
