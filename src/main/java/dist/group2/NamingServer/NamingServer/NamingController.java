package dist.group2.NamingServer.NamingServer;

import java.net.InetAddress;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import ch.qos.logback.core.joran.sanity.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void addNode(@RequestBody Map<String, String> node) {
        this.service.addNode(node);
    }

    @DeleteMapping(path = {"{nodeName}"})
    public void deleteNode(@PathVariable("nodeName") String nodeName) {
        this.service.deleteNode(nodeName);
    }

    @GetMapping
    public String findFile(String fileName) {
        return this.service.findFile(fileName);
    }
}
