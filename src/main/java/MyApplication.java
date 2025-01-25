import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.CityDatabase;
import models.City;
import models.Weather;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class MyApplication {
    public static void main(String[] args) {
        CityDatabase db = new CityDatabase();
        db.connect("jdbc:postgresql://localhost:5432/" + "postgres", "postgres", "0000");
        Scanner sc = new Scanner(System.in);
        String place_id;
        System.out.println("Welcome");
        boolean t = true;
        while(t){
            try{
                System.out.println("[1] View all cities");
                System.out.println("[2] Get cities by id");
                System.out.println("[3] Exit");

                int choice = sc.nextInt();
                switch(choice){
                    case 1:
                        ArrayList<City> cities = db.getAllCities();
                        for(City city : cities){
                            city.displayInfo();
                        }
                        break;
                    case 2:
                        System.out.println("Enter ID: ");
                        place_id = db.getCityById(sc.nextInt());
                        String token = "wj41qlc7ukds7tj5n2iisohhfxu843nhepoxmpz8";
                        Weather weather = getWeather(token, place_id);
                        System.out.println(weather);
                        break;
                    case 3:
                        t = false;
                        break;
                    default:
                        System.out.println("Please select option between 1-3.");
                        break;
                }
            }catch(InputMismatchException e){
                System.out.println("Input must be integer " + e.getMessage());

            }catch(Exception e){
                System.out.println("Exception " + e.getMessage());
            }
        }


//        String token = "wj41qlc7ukds7tj5n2iisohhfxu843nhepoxmpz8";
//        Weather weather = getWeather(token, place_id);
//
//        System.out.println(weather);

    }

    public static Weather getWeather(String token, String placeId) {
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
            } catch (InputMismatchException e){
                System.out.println(e.getMessage());
        } catch(Exception e){
                System.out.println(e.getMessage());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
