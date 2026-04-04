package atlas.banking.TSP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

@SpringBootApplication
public class TspTokenServiceProviderApplication {

	public static void main(String[] args) {


		System.out.println(Instant.now());
		SpringApplication.run(TspTokenServiceProviderApplication.class, args);

	}

}
