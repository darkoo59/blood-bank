package simulator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simulator.Coordinates;
import simulator.exceptions.ServiceCurrentlyUnavailable;
import simulator.service.LocationSimulationService;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/simulation")
public class LocationSimulationController {

    private final LocationSimulationService locationSimulationService;

    @GetMapping
    public ResponseEntity<?> getCoordinates(@RequestParam("A_lng") double ALng, @RequestParam("A_lat") double ALat,
                                            @RequestParam("B_lng") double BLng, @RequestParam("B_lat") double BLat) {
        Coordinates A = new Coordinates(ALng, ALat);
        Coordinates B = new Coordinates(BLng, BLat);
        try {
            locationSimulationService.activateService(A, B);
        } catch (ServiceCurrentlyUnavailable e) {
            return new ResponseEntity<>(e.getMessage(), SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>(OK);
    }
}
