package bloodcenter.map_simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/map-sim")
public class MapSimulatorController {
    private final SimpMessagingTemplate template;
    @Autowired
    public MapSimulatorController(SimpMessagingTemplate template) {
        this.template = template;
    }

    Location current = new Location(45.25636, 19.84731);
    Location source = new Location(45.25636, 19.84731);
    Location destination = new Location(45.23636, 19.82731);
    @Scheduled(fixedDelay = 3000)
    public void scheduleFixedDelayTask()  {
        current.setLat(current.getLat() - 0.001);
        current.setLng(current.getLng() - 0.001);

        sendLocationToAll(new LocationMessage(current, source, destination));
    }

    public void sendLocationToAll(LocationMessage loc) {
        template.convertAndSend("/start/location", loc);
    }
}