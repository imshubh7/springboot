package com.sdelab.sdelab.controller;

import com.sdelab.sdelab.constants.PathConstant;
import com.sdelab.sdelab.entity.Weather;
import com.sdelab.sdelab.service.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);
    private final String LOG_MESSAGE = "Request with Correlation ID: {} received in WeatherController";

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping(PathConstant.GET_FORECAST_URL_CONTROLLER)
    public Mono<Weather> getData(@RequestParam double latitude, @RequestParam double longitude, HttpServletRequest request){
        String correlationId = (String) request.getAttribute("correlationId");
        log.info(LOG_MESSAGE,correlationId);
        return weatherService.getWeatherData(latitude, longitude, request);
    }
}
