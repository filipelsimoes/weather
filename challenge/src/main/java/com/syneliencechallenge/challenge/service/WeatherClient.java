//package com.syneliencechallenge.challenge.service;
//
//import com.syneliencechallenge.challenge.entity.Weather;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@FeignClient(url="http://api.openweathermap.org/data/2.5")
//public interface WeatherClient {
//
//    @RequestMapping(method = RequestMethod.GET, value="/weather?q={city}&appid=4568194581a9e78fae108b41dbea4a2c")
//    ResponseEntity<String> getWeather(@RequestParam String city);
//
//    @RequestMapping(method = RequestMethod.GET, value="/find?lat={lat}&lon={lon}&cnt={cnt}&appid=4568194581a9e78fae108b41dbea4a2c")
//    ResponseEntity<String> findWeatherByCoords(@RequestParam String lat, @RequestParam String lon, @RequestParam int cnt);
//}
