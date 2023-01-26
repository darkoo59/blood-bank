package bloodcenter.location_simulator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationMessage {
    Location current;
    Location source;
    Location destination;
}
