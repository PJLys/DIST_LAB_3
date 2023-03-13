package dist.group2.NamingServer.NamingServer;

import java.net.InetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"api/naming"}
)
public class NamingController {
    private final NamingService service;

    @Autowired
    public NamingController(NamingService service) {
        this.service = service;
    }

    @PostMapping
    public void addNode(@RequestParam String nodeName, @RequestParam InetAddress IPAddress) {
        this.service.addNode(nodeName, IPAddress);
    }

    @DeleteMapping(
            path = {"{cardNumber}"}
    )
    public void deleteNode(@PathVariable("cardNumber") String nodeName) {
        this.service.deleteNode(nodeName);
    }

    @GetMapping
    public InetAddress findFile(String fileName) {
        return this.service.findFile(fileName);
    }
}
