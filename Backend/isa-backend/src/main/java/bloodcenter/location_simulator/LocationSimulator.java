package bloodcenter.location_simulator;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LocationSimulator {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void messageQueueHandler(Object message) {
    }
}
