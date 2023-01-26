package simulator.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import simulator.Coordinates;
import simulator.exceptions.ServiceCurrentlyUnavailable;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenRouteService {

    @Value("${route.service.api_key}")
    private String API_KEY;
    @Value("${route.service.api_url}")
    private String API_URL;

    public List<Coordinates> getRoute(Coordinates A, Coordinates B) throws ServiceCurrentlyUnavailable {
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();

        String url = API_URL + "?api_key=" + API_KEY +
                "&start=" + A.getLng() + "," + A.getLat() + "&end=" + B.getLng() + "," + B.getLat();

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        } catch (HttpServerErrorException e) {
            throw new ServiceCurrentlyUnavailable();
        }
        JsonObject jsonResponse = gson.fromJson(response.getBody(), JsonObject.class);
        JsonArray coordinates = jsonResponse.getAsJsonArray("features")
                .get(0).getAsJsonObject().getAsJsonObject("geometry")
                .getAsJsonArray("coordinates");

        List<Coordinates> routeCoordinates = new ArrayList<>();

        for (JsonElement coordinate : coordinates) {
            JsonArray coord = coordinate.getAsJsonArray();
            routeCoordinates.add(new Coordinates(coord.get(0).getAsDouble(), coord.get(1).getAsDouble()));
        }

        return routeCoordinates;
    }
}
