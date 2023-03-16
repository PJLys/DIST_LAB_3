package dist.group2.NamingServer;

import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Client implements Runnable {
    private final String name;
    private final String IPAddress;
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public Client(String nodeName, String IPAddress) throws UnknownHostException {
        this.name = nodeName;
        this.IPAddress = IPAddress;
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:8080/api/naming";
        System.out.println("<---> " + nodeName + " Instantiated <--->");
        addNode(this.name, this.IPAddress);
    }

    public void run() {
        while (true) {
            try {
                runSequence();
            } catch (Exception e) {
                System.out.println("\t"+e.getMessage());
            }
        }
    }

    public void runSequence() {
        findFile("file1.txt");
        sleep(500);
        findFile("file2.txt");
        sleep(500);
        findFile("file3.txt");
        sleep(5000);
    }

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // POST functionality
    public void addNode(String nodeName, String IPAddress) {
        String url = baseUrl;

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nodeName", nodeName);
        requestBody.put("IPAddress", IPAddress);
        try {
            restTemplate.postForObject(url, requestBody, Void.class);
            System.out.println("<" + this.name + "> - Add node with name " + nodeName + " and IP address " + IPAddress);
        } catch(Exception e) {
            System.out.println("<" + this.name + "> - ERROR - Failed to add " + nodeName + ", hash collision occurred");
        }
    }

    // DELETE functionality
    public void deleteNode(String nodeName) {
        String url = baseUrl + "/" + nodeName;
        try {
            restTemplate.delete(url);
            System.out.println("<" + this.name + "> - Deleted node with name " + nodeName);
        } catch(Exception e) {
            System.out.println("<" + this.name + "> - ERROR - Failed to delete " + nodeName);
        }
    }

    // GET functionality
    public void findFile(String fileName) {
        String url = baseUrl + "?fileName=" + fileName;
        try {
            String IPAddress = restTemplate.getForObject(url, String.class);
            System.out.println("<" + this.name + "> - " + fileName + " is stored at IPAddress " + IPAddress);
        } catch(Exception e) {
            System.out.println("<" + this.name + "> - ERROR - Failed to find " + fileName + ", no nodes in database");
        }
    }
}