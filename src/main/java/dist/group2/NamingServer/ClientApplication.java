package dist.group2.NamingServer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.UnknownHostException;

public class ClientApplication {
	public static void main(String[] args) throws UnknownHostException {
		Client client = new Client();
		client.run();
	}
}