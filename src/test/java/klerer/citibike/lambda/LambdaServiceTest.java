package klerer.citibike.lambda;

import klerer.citibike.map.LambdaService;
import klerer.citibike.map.LambdaServiceFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LambdaServiceTest {
    @Test
    public void sendRequest() {
        // given
        LambdaService service = new LambdaServiceFactory().getService();

        Location from = new Location(40.77, -73.99);
        Location to = new Location(40.7484, -73.9857);
        CitiBikeRequest request = new CitiBikeRequest(from, to);

        // when
        CitiBikeResponse response = service.getStations(request).blockingGet();

        // then
        assertEquals("11 Ave & W 59 St", response.start.name);
        assertEquals("E 33 St & 5 Ave", response.end.name);
    }
}

