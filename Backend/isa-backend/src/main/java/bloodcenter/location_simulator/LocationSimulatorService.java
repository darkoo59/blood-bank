package bloodcenter.location_simulator;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class LocationSimulatorService {

    private final LocationSimulatorController controller;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void messageQueueHandler(LocationMessage message) {

        controller.sendLocationToAll(message);
    }

    public void initiateLocationService(double ALng, double ALat, double BLng, double BLat) {
        WebClient client = WebClient.builder().baseUrl("http://localhost:6556/api/simulation")
                .build();
        Mono<String> response = client.get()
                .uri(uriBuilder -> uriBuilder.queryParam("A_lng", ALng)
                        .queryParam("A_lat", ALat)
                        .queryParam("B_lng", BLng)
                        .queryParam("B_lat", BLat)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
        response.subscribe(data -> {
            // handle data
        }, error -> {
            // handle error
        });
    }
}
