package com.sdelab.sdelab.controller;

import com.sdelab.sdelab.constants.PathConstant;
import com.sdelab.sdelab.entity.Weather;
import com.sdelab.sdelab.service.WeatherService;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Validated
@RestController
@RequestMapping("/api/weather/v1")
public class WeatherController {

    private final WeatherService weatherService;
    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);
    private final String LOG_MESSAGE = "Request with Correlation ID: {} received in WeatherController";

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Cacheable(value = "weatherData",key = "{#latitude, #longitude}")
    @GetMapping(PathConstant.GET_FORECAST_URL_CONTROLLER)
    public Mono<Weather> getData(@RequestParam @Min(value = -90) @Max(value = 90) double latitude, @RequestParam @Min(value = -180) @Max(value = 180) double longitude, HttpServletRequest request){
        String correlationId = (String) request.getAttribute("correlationId");
        log.info(LOG_MESSAGE,correlationId);
        return weatherService.getWeatherData(latitude, longitude, request).cache(Duration.ofMinutes(5));
    }
}