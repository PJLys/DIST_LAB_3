package dist.group2.NamingServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.UnknownHostException;

@SpringBootApplication
public class NamingServerApplication {
	public static void main(String[] args) throws UnknownHostException {
		// Instantiate Naming Server thread
		Thread serverThread = new Thread(() -> SpringApplication.run(NamingServerApplication.class, args));

		// Start Naming Server thread
		serverThread.start();

		// Wait 5 seconds (until the server is up and running)
		sleep(5000); 		// Wait 5 seconds (until the server is up and running)

		// Instantiate Client threads
		Client client1 = new Client("Kevin", "192.168.1.1");
		Client client2 = new Client("Peter", "192.168.1.2");
		Client client3 = new Client("Karen", "192.168.1.3");
		Thread clientThread1 = new Thread(client1);

		// Start client thread
		clientThread1.start();	// Only client1 is running and asking for the file locations

		// Hash values
		// "Kevin" -> hash = 3008
		// "Peter" -> hash = 30911
		// "Karen" -> hash = 10952
		// "file1.txt" -> hash = 6801	-> 	Stored on client 1
		// "file2.txt" -> hash = 812	-> 	Stored on client 2
		// "file3.txt" -> hash = 27592	-> 	Stored on client 3
	}

	public static void sleep(int time) {
		try {
			Thread.sleep(time); // Wait 5 seconds for server to start up
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}