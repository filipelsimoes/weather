package com.syneliencechallenge.challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syneliencechallenge.challenge.entity.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${weatherUrlWeather}")
    private String WEATHER_URL;

    @Value("${weatherUrlFind}")
    private String FIND_WEATHER_URL;

    @Value("${apiKeyForMapsApi}")
    private String apiKey;

    private RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper;

    public Weather getWeather(String city){
//        ResponseEntity<String> newFeignResponse = weatherClient.getWeather(city);
//        System.out.println("FEIGN: " + newFeignResponse);
        URI url = new UriTemplate(WEATHER_URL).expand(city, apiKey);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return convert(response);
    }

    public List<Weather> findWeatherByCords(String lat, String lon, int cnt){
        List<Weather> listByCoords = new ArrayList<>();
        URI url = new UriTemplate(FIND_WEATHER_URL).expand(lat, lon, cnt, apiKey);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());
            for(int i=0; i < cnt; i++){
                Weather newWeather = convertByList(root, i);
                listByCoords.add(newWeather);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
        return listByCoords;
    }

    private Weather convertByList(JsonNode root, int i) {
        Weather newWeather = new Weather(
                root.path("list").get(i).path("name").asText(),
                root.path("list").get(i).path("weather").get(0).path("main").asText(),
                BigDecimal.valueOf(root.path("list").get(i).path("main").path("temp").asDouble()),
                BigDecimal.valueOf(root.path("list").get(i).path("main").path("temp_min").asDouble()),
                BigDecimal.valueOf(root.path("list").get(i).path("main").path("temp_max").asDouble()),
                BigDecimal.valueOf(root.path("list").get(i).path("main").path("pressure").asDouble()),
                BigDecimal.valueOf(root.path("list").get(i).path("main").path("humidity").asDouble()));
        return newWeather;
    }

    private Weather convert(ResponseEntity<String> response) {
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            return new Weather(
                    root.path("name").asText(),
                    root.path("weather").get(0).path("main").asText(),
                    BigDecimal.valueOf(root.path("main").path("temp").asDouble()),
                    BigDecimal.valueOf(root.path("main").path("temp_min").asDouble()),
                    BigDecimal.valueOf(root.path("main").path("temp_max").asDouble()),
                    BigDecimal.valueOf(root.path("main").path("pressure").asDouble()),
                    BigDecimal.valueOf(root.path("main").path("humidity").asDouble()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }

}
