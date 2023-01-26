package simulator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import simulator.Coordinates;
import simulator.CoordinatesMessageConverter;
import simulator.LocationMessage;
import simulator.exceptions.ServiceCurrentlyUnavailable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class LocationSimulationService {

    private final OpenRouteService openRouteService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${simulator.frequency}")
    private int frequency;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routing_key}")
    private String routingKey;

    public void activateService(Coordinates A, Coordinates B) throws ServiceCurrentlyUnavailable {
        List<Coordinates> currentList = openRouteService.getRoute(A, B);
        List<LocationMessage> coordinates = new ArrayList<>();
        for (var coord : currentList) {
            coordinates.add(new LocationMessage(coord, A, B));
        }

        rabbitTemplate.setMessageConverter(new CoordinatesMessageConverter());

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            LocationMessage currentCoordinates = coordinates.remove(0);
            rabbitTemplate.convertAndSend(exchange, routingKey, currentCoordinates);
        }, 0, frequency, TimeUnit.SECONDS);
    }
}
