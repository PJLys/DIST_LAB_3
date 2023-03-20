package dist.group2.NamingServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.UnknownHostException;

@SpringBootApplication
public class NamingServerApplication {
	public static void main(String[] args) throws UnknownHostException {
		// Instantiate Client threads
		Client client1 = new Client();
		Thread clientThread1 = new Thread(client1);

		// Start client thread
		clientThread1.start();	// Only client1 is running and asking for the file locations
	}

	public static void sleep(int time) {
		try {
			Thread.sleep(time); // Wait 5 seconds for server to start up
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}