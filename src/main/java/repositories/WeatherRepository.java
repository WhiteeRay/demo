package repositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.CityDatabase;
import models.Weather;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WeatherRepository {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();



    public static Weather getWeather(String token, String placeId) {
        CityDatabase db = new CityDatabase();



        Scanner sc = new Scanner(System.in);
        String url = "https://www.meteosource.com/api/v1/free/point?" + "key=" + token
                + "&place_id=" + placeId + "&sections=daily";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status code: " + response.statusCode());
            System.out.println("Body code: " + response.body());
            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = response.body();
            JsonNode jsonNode = mapper.readTree(jsonResponse);
            JsonNode daily = jsonNode.get("daily");
            int i = 1;
            for (JsonNode data : daily.get("data")) {
                System.out.println("[" + i++ + "]" + data.get("day"));
            }

            try {
                int choice = sc.nextInt();
                JsonNode data = daily.get("data");
                Weather weather = null;
                switch (choice) {

                    case 1:
                        weather = new Weather(data.get(0).get("day").asText(), data.get(0).get("summary").asText());

                        break;
                    case 2:
                        weather = new Weather(data.get(1).get("day").asText(), data.get(1).get("summary").asText());

                        break;
                    case 3:
                        weather = new Weather(data.get(2).get("day").asText(), data.get(2).get("summary").asText());


                        break;
                    case 4:
                        weather = new Weather(data.get(3).get("day").asText(), data.get(3).get("summary").asText());

                        break;
                    case 5:
                        weather = new Weather(data.get(4).get("day").asText(), data.get(4).get("summary").asText());

                        break;
                    case 6:
                        weather = new Weather(data.get(5).get("day").asText(), data.get(5).get("summary").asText());

                        break;
                    case 7:
                        weather = new Weather(data.get(6).get("day").asText(), data.get(6).get("summary").asText());

                        break;
                    default:
                        System.out.println("Please select option between 1-7.");
                        break;
                }
                return weather;
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
