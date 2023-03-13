package dist.group2.NamingServer.NamingServer;

import jakarta.transaction.Transactional;
import java.net.InetAddress;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NamingService {
    private Map<Integer, InetAddress> repository = new TreeMap<>();

    @Autowired
    public NamingService() {}

    public int hashValue(String name) {
        int min = -2147483647;
        int max = Integer.MAX_VALUE;
        Integer hash = (name.hashCode() + max) * (32768 / max + Math.abs(min));
        return hash;
    }

    public synchronized void addNode(String nodeName, InetAddress IPAddress) {
        if (repository.containsKey(hashValue(nodeName))) {
            throw new IllegalStateException("Hash of " + nodeName + " is already being used");
        }
        repository.put(hashValue(nodeName), IPAddress);
    }

    public synchronized void deleteNode(String nodeName) {
        if (!repository.containsKey(hashValue(nodeName))) {
            throw new IllegalStateException("There is no node with name" + nodeName);
        }
        repository.remove(hashValue(nodeName));
    }

    @Transactional
    public synchronized InetAddress findFile(String fileName) {
        int fileHash = this.hashValue(fileName);
        List<Integer> hashes = (List<Integer>) repository.keySet();

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
                return repository.get(Collections.max(smallerHashes));
            } else {
                return repository.get(Collections.max(hashes));
            }
        }
    }
}