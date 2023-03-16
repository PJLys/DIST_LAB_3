package dist.group2.NamingServer.NamingServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.TreeMap;

public class JsonHelper {
    private static final String path = "src/files/map.json";
    public static void convertMapToJson(Map<Integer, String> map) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(path), map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, String> convertJsonToMap() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> map = mapper.readValue(new File(path), Map.class);

            Map<Integer, String> map2 = new TreeMap<>();
            for (String key: map.keySet()) {
                map2.put(Integer.parseInt(key), map.get(key));
            }
            return map2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
