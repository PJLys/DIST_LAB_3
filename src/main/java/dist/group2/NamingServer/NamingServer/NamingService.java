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
    private Map<Integer, String> repository;

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
        repository.put(hashValue(node.get("nodeName")), node.get("IPAddress"));
        convertMapToJson(repository);
    }

    public synchronized void deleteNode(String nodeName) {
        if (!repository.containsKey(hashValue(nodeName))) {
            throw new IllegalStateException("There is no node with name" + nodeName);
        }
        repository.remove(hashValue(nodeName));
        convertMapToJson(repository);
    }

    @Transactional
    public synchronized String findFile(String fileName) {
        int fileHash = this.hashValue(fileName);
        Set<Integer> hashes = repository.keySet();

        if (hashes.isEmpty()) {
            throw new IllegalStateException("There is no node in the database!");
        } else {
            List<Integer> smallerHashes = new ArrayList();
            Iterator var5 = hashes.iterator();

            while(var5.hasNext()) {
                Integer hash = (Integer)var5.next();
                if (hash < fileHash) {
                    smallerHashes.add(hash);
                }
            }

            if (smallerHashes.isEmpty()) {
                return repository.get(Collections.max(hashes));
            } else {
                return repository.get(Collections.max(smallerHashes));
            }
        }
    }
}