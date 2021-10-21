package com.syneliencechallenge.challenge.controller;

import com.syneliencechallenge.challenge.entity.Weather;
import com.syneliencechallenge.challenge.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final WeatherService weatherService;

    /**
     *
     * @param city
     * Param city is a string
     * @return an object Weather to displau on the screen
     * This is cached so if you send a request but the response is already cached will display the response and will not do another request
     * If the request is done sucessufuly the endpoint will return a http status code 200, if not will return an 500.
     * If the api key is invalid the endpoint will return an http status code 401.
     */
    @Cacheable("city")
    @GetMapping("/weather")
    public Weather getWeather(@RequestParam String city){
        Weather weather = weatherService.getWeather(city);
        return weather;
    }

    /**
     *
     * @param lat must be a string like 3.5
     * @param lon must be a string like 35.0
     * @param cnt must be a intenger, min value is 1
     * @return a list of Objects Weather
     * If the request is done sucessufuly the endpoint will return a http status code 200, if not will return an 500.
     * If the api key is invalid the endpoint will return an http status code 401.
     */
    @GetMapping("/find")
    public List<Weather> findWeatherByCoords(@RequestParam String lat, @RequestParam String lon, @RequestParam int cnt){
        List<Weather> listByCoords = weatherService.findWeatherByCords(lat, lon, cnt);
        return listByCoords;
    }
}
