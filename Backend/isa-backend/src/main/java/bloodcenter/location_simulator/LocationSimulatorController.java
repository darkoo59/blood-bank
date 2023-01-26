package bloodcenter.location_simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/map-sim")
public class LocationSimulatorController {
    private final SimpMessagingTemplate template;
    @Autowired
    public LocationSimulatorController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendLocationToAll(LocationMessage loc) {
        template.convertAndSend("/start/location", loc);
    }
}