package dist.group2.NamingServer.NamingServer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
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

    public static TreeMap<Integer, String> convertJsonToMap() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(path), TreeMap.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
