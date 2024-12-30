package klerer.citibike;

import klerer.citibike.json.Station;
import klerer.citibike.json.Stations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CitiBikeServiceTest {
    @Test
    public void stationInfo() {
        // given
        CitiBikeService service = new CitiBikeServiceFactory().getService();

        // when
        Stations info = service.stationInfo().blockingGet();
        Station station = info.data.stations[0];

        // then
        assertNotNull(station.station_id);
        assertNotNull(station.name);
        assertNotEquals(0, station.lat);
        assertNotEquals(0, station.lon);
    }

    @Test
    public void stationStatus() {
        // given
        CitiBikeService service = new CitiBikeServiceFactory().getService();

        // when
        Stations info = service.stationStatus().blockingGet();
        Station station = info.data.stations[3];

        // then
        assertNotNull(station.station_id);
        assertNotEquals(0, station.num_bikes_available);
        assertNotEquals(0, station.num_ebikes_available);
        assertNotEquals(0, station.num_docks_available);
    }

}
