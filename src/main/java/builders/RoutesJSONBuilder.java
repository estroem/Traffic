package builders;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Route;
import model.Stop;

import java.io.IOException;
import java.util.List;

public class RoutesJSONBuilder {
    public static List<Route> fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(json, new TypeReference<List<Route>>(){});
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
