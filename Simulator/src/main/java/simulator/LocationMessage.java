package simulator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationMessage {
    Coordinates current;
    Coordinates source;
    Coordinates destination;
}
