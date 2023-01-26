package bloodcenter.location_simulator;

import bloodcenter.map_simulator.LocationMessage;
import bloodcenter.map_simulator.MapSimulatorController;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LocationSimulator {

    private final MapSimulatorController controller;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void messageQueueHandler(LocationMessage message) {

        controller.sendLocationToAll(message);
    }
}
