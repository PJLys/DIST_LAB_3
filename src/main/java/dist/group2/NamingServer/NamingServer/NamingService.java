package dist.group2.NamingServer.NamingServer;

import ch.qos.logback.core.joran.sanity.Pair;
import jakarta.transaction.Transactional;
import java.net.InetAddress;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dist.group2.NamingServer.NamingServer.JsonHelper.convertJsonToMap;
import static dist.group2.NamingServer.NamingServer.JsonHelper.convertMapToJson;

@Service
public class NamingService {
    private TreeMap<Integer, String> repository;

    @Autowired
    public NamingService() {
        repository = new TreeMap<>();       // convertJsonToMap();
    }

    public Integer hashValue(String name) {
        Integer hash = Math.abs(name.hashCode()) % 32769;
        return hash;
    }

    public synchronized void addNode(Map<String, String> node) {
        if (repository.containsKey(hashValue(node.get("nodeName")))) {
            throw new IllegalStateException("Hash of " + node.get("nodeName") + " is already being used");
        }
        System.out.println("Succesfully added node with name " + node.get("nodeName") + " (hash=" + hashValue(node.get("nodeName")) + ") and IP address " + node.get("IPAddress"));
        repository.put(hashValue(node.get("nodeName")), node.get("IPAddress"));
        convertMapToJson(repository);
    }

    public synchronized void deleteNode(String nodeName) {
        if (!repository.containsKey(hashValue(nodeName))) {
            throw new IllegalStateException("There is no node with name" + nodeName);
        }
        repository.remove(hashValue(nodeName));
        System.out.println("Succesfully removed node with name " + nodeName);
        convertMapToJson(repository);
    }

    @Transactional
    public synchronized String findFile(String fileName) {
        if (repository.isEmpty()) {
            throw new IllegalStateException("There is no node in the database!");
        }
        else {
            int fileHash = this.hashValue(fileName);
            Integer key;
            try {
                key = repository.headMap(fileHash).lastKey();
            }
            catch (NoSuchElementException e) {
                key = repository.lastKey();
            }
            return repository.get(key);
        }
    }
}