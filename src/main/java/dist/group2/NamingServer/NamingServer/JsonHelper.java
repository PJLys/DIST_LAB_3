package dist.group2.NamingServer.NamingServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class JsonHelper {
    private static final String filePath = "src/files/map.json";
    public static void convertMapToJson(Map<Integer, String> map) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String json = objectMapper.writeValueAsString(map);
            File file = new File(filePath);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TreeMap<Integer, String> convertJsonToMap() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(filePath);
            TreeMap<Integer, String> map = objectMapper.readValue(file, TreeMap.class);
            return map;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
