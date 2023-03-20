package dist.group2.NamingServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.UnknownHostException;

@SpringBootApplication
public class NamingServerApplication {
	public static void main(String[] args) {
		// Start Naming Server
		SpringApplication.run(NamingServerApplication.class, args);
	}
}