package com.sdelab.sdelab.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/health")
    public String getHealth(){
        return "Healthy";
    }

    @GetMapping("/forecast")
    public Mono<Weather> getData(@RequestParam double latitude, @RequestParam double longitude){
        return weatherService.getWeatherData(latitude, longitude);
    }
}
