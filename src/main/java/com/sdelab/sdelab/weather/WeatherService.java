package com.sdelab.sdelab.weather;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WeatherService {

    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.open-meteo.com/v1").build();
    }

    public Mono<Weather> getWeatherData(double latitude, double longitude) {
        return webClient
                .get()
                .uri("/forecast?latitude={latitude}&longitude={longitude}&current=temperature_2m&forecast_days=1", latitude, longitude)
                .retrieve()
                .bodyToMono(Weather.class)
                .onErrorResume(throwable -> {
                    // Handle the error here
                    // You can log the error or return a fallback value, for example:
                    log.error("An error occurred while fetching weather data.", throwable);
                    return Mono.empty(); // Return an empty Mono or a default value
                });
    }


}
