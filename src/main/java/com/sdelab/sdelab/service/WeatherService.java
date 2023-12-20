package com.sdelab.sdelab.service;

import com.sdelab.sdelab.constants.PathConstant;
import com.sdelab.sdelab.entity.Weather;
import com.sdelab.sdelab.util.StatusCodeToErrorMapping;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class WeatherService {

    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    private static final String ENDPOINT_URL = "/forecast?latitude={latitude}&longitude={longitude}&current=temperature_2m&forecast_days=1";
    private static final String LOG_MESSAGE = "Request with Correlation ID: {} received in WeatherService";
    private static final String LOG_FAILURE_MESSAGE = "Request with Correlation ID: {} failed in WeatherService with Error: {}";

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(PathConstant.OPEN_METEO_BASE_URL).build();
    }

    @Cacheable(value = "weatherData",key = "{#latitude, #longitude}")
    public Mono<Weather> getWeatherData(double latitude, double longitude, HttpServletRequest request) {
        String correlationId = (String) request.getAttribute("correlationId");
        log.info(LOG_MESSAGE,correlationId);

        return webClient
                .get()
                .uri(ENDPOINT_URL, latitude, longitude)
                .exchangeToMono(res -> {
                    if (res.statusCode().equals(HttpStatus.OK)) {
                        return res.bodyToMono(Weather.class);
                    }
                    else {
                        log.error(LOG_FAILURE_MESSAGE, correlationId, res.statusCode().value());
                        return Mono.error(StatusCodeToErrorMapping.getException(res.statusCode().value()));
                    }
                })
                .cache(Duration.ofMinutes(5))
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)))
                .timeout(Duration.ofSeconds(15));
    }
}