package com.syneliencechallenge.challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.syneliencechallenge.challenge.entity.Weather;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(WeatherService.class)
public class WeatherServiceTest {

    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={API key}";
    private static final String FIND_WEATHER_URL = "http://api.openweathermap.org/data/2.5/find?lat={lat}&lon={lon}&cnt={cnt}&appid={API key}";
    private static String apiKey = "4568194581a9e78fae108b41dbea4a2c";

    @Autowired
    private WeatherService weatherService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void getWeatherTest() {
        String city = "Lisbon";

        Weather w1 = new Weather("Lisbon", "Clear", BigDecimal.valueOf(298.4), BigDecimal.valueOf(293.51), BigDecimal.valueOf(302.25), BigDecimal.valueOf(1020.0), BigDecimal.valueOf(59.0));

        URI url = new UriTemplate(WEATHER_URL).expand(city, apiKey);

        String body = "{\"coord\":{\"lon\":-13.1333,\"lat\":38.7167},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":298.4,\"feels_like\":298.52,\"temp_min\":293.51,\"temp_max\":302.25,\"pressure\":1020,\"humidity\":59},\"visibility\":10000,\"wind\":{\"speed\":2.06,\"deg\":150},\"clouds\":{\"all\":0},\"dt\":1634568509,\"sys\":{\"type\":1,\"id\":6901,\"country\":\"PT\",\"sunrise\":1634539718,\"sunset\":1634579661},\"timezone\":3600,\"id\":2267057,\"name\":\"Lisbon\",\"cod\":200}";
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> response = new ResponseEntity<String>(body, headers,HttpStatus.SC_OK);//Martelar resposta, ver no serviço

        ReflectionTestUtils.setField(weatherService, "restTemplate", restTemplate);
        Mockito.when(restTemplate.getForEntity(url, String.class)).thenReturn(response);

        Weather w2 = weatherService.getWeather(city);

        assertEquals(w1,w2);

    }

    @Test
    public void findWeatherByCordsTest(){
        String lat = "35.00";
        String lon = "2.35";
        int cnt = 2;

        URI url = new UriTemplate(FIND_WEATHER_URL).expand(lat, lon, cnt, apiKey);

        String body = "{\"message\":\"accurate\",\"cod\":\"200\",\"count\":2,\"list\":[{\"id\":2491335,\"name\":\"Ksar Chellala\",\"coord\":{\"lat\":35.2122,\"lon\":2.3189},\"main\":{\"temp\":294.96,\"feels_like\":294.08,\"temp_min\":294.96,\"temp_max\":294.96,\"pressure\":1020,\"humidity\":34,\"sea_level\":1020,\"grnd_level\":926},\"dt\":1634569752,\"wind\":{\"speed\":2.06,\"deg\":33},\"sys\":{\"country\":\"DZ\"},\"rain\":null,\"snow\":null,\"clouds\":{\"all\":49},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}]},{\"id\":2483827,\"name\":\"Rechaïga\",\"coord\":{\"lat\":35.4068,\"lon\":1.9739},\"main\":{\"temp\":294.88,\"feels_like\":294.05,\"temp_min\":294.88,\"temp_max\":294.88,\"pressure\":1020,\"humidity\":36,\"sea_level\":1020,\"grnd_level\":925},\"dt\":1634569798,\"wind\":{\"speed\":1.75,\"deg\":360},\"sys\":{\"country\":\"DZ\"},\"rain\":null,\"snow\":null,\"clouds\":{\"all\":77},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}]}]}";
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> response = new ResponseEntity<String>(body, headers,HttpStatus.SC_OK);

        ReflectionTestUtils.setField(weatherService, "restTemplate", restTemplate);
        Mockito.when(restTemplate.getForEntity(url, String.class)).thenReturn(response);

        List<Weather> list2 = weatherService.findWeatherByCords(lat,lon, cnt);

        List<Weather> list1 = new ArrayList<>();
        list1.add(new Weather("Ksar Chellala", "Clouds", BigDecimal.valueOf(294.96), BigDecimal.valueOf(294.96), BigDecimal.valueOf(294.96), BigDecimal.valueOf(1020.0), BigDecimal.valueOf(34.0)));
        list1.add(new Weather("Rechaïga", "Clouds", BigDecimal.valueOf(294.88), BigDecimal.valueOf(294.88), BigDecimal.valueOf(294.88), BigDecimal.valueOf(1020.0), BigDecimal.valueOf(36.0)));

        assertEquals(list1, list2);
    }
}
