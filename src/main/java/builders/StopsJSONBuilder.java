package builders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Stop;

import java.io.IOException;
import java.util.List;

public class StopsJSONBuilder {
    public static List<Stop> fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(json, new TypeReference<List<Stop>>(){});
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
