package com.sdelab.sdelab.service;

import com.sdelab.sdelab.constants.ErrorConstant;
import com.sdelab.sdelab.constants.PathConstant;
import com.sdelab.sdelab.entity.Weather;
import com.sdelab.sdelab.exception.WeatherRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {

    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    private final String ENDPOINT_URL = "/forecast?latitude={latitude}&longitude={longitude}&current=temperature_2m&forecast_days=1";
    private final String LOG_MESSAGE = "Request with Correlation ID: {} received in WeatherService";
    private final String LOG_SUCCESS_MESSAGE = "Request with Correlation ID: {} was sent data successfully from WeatherService";
    private final String LOG_FAILURE_MESSAGE = "Request with Correlation ID: {} failed in WeatherService with Error: ";

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(PathConstant.OPEN_METEO_BASE_URL).build();
    }

    public Mono<Weather> getWeatherData(double latitude, double longitude, HttpServletRequest request) {
        String correlationId = (String) request.getAttribute("correlationId");
        log.info(LOG_MESSAGE,correlationId);
        return webClient
                .get()
                .uri(ENDPOINT_URL, latitude, longitude)
                .retrieve()
                .bodyToMono(Weather.class)
                .doOnSuccess(weather-> log.info(LOG_SUCCESS_MESSAGE,correlationId))
                .onErrorResume(ex -> {
                    log.error(LOG_FAILURE_MESSAGE+ ex.getMessage(),correlationId);
                    return Mono.error(new WeatherRuntimeException(ErrorConstant.ERROR_MESSAGE_INTERNAL_SERVER_ERROR));
                });
    }
}
