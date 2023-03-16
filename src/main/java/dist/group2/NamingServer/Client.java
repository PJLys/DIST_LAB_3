package dist.group2.NamingServer.Client1;

import ch.qos.logback.core.joran.sanity.Pair;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Client1 implements Runnable {
    private final String name;
    private final String IPAddress;
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public Client1(String nodeName, String IPAddress) throws UnknownHostException {
        this.name = nodeName;
        this.IPAddress = IPAddress;
        this.restTemplate = new RestTemplate();
        this.baseUrl = "http://localhost:8080/api/naming";
        addNode(this.name, this.IPAddress);
        System.out.println("<---> " + nodeName + " Instantiated <--->");
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
        restTemplate.postForObject(url, requestBody, Void.class);
        System.out.println("<" + this.name + "> - Add node with name " + nodeName + " and IP address " + IPAddress);
    }

    // DELETE functionality
    public void deleteNode(String nodeName) {
        String url = baseUrl + "/" + nodeName;
        restTemplate.delete(url);
        System.out.println("<" + this.name + "> - Deleted node with name " + nodeName);
    }

    // GET functionality
    public void findFile(String fileName) {
        String url = baseUrl + "?fileName=" + fileName;
        String IPAddress = restTemplate.getForObject(url, String.class);
        System.out.println("<" + this.name + "> - " + fileName + " is stored at IPAddress " + IPAddress);
    }
}