package klerer.citibike;

import klerer.citibike.json.Station;
import klerer.citibike.json.Stations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FindStationsTest {
    @Test
    public void stationById() {
        // given
        CitiBikeService service = new CitiBikeServiceFactory().getService();
        Stations stationInfo = service.stationInfo().blockingGet();
        Stations stationStatus = service.stationStatus().blockingGet();
        FindStations findStations = new FindStations(stationInfo, stationStatus);

        Station[] stations = stationInfo.data.stations;
        String id = stations[0].station_id;

        // when
        Station station = findStations.stationById(id);

        // then
        assertNotNull(station);
        assertEquals(id, station.station_id);
    }

    @Test
    public void closestStationWithBikes() {
        // given
        CitiBikeService service = new CitiBikeServiceFactory().getService();
        Stations stationInfo = service.stationInfo().blockingGet();
        Stations stationStatus = service.stationStatus().blockingGet();

        double lat = 40.77185400202356;
        double lon = -73.98830454232788;

        // when
        FindStations findStations = new FindStations(stationInfo, stationStatus);
        Station closestStation = findStations.closestStationWithBikes(lat, lon);

        // then
        assertNotNull(closestStation);
        assertNotEquals(0, closestStation.num_bikes_available);
        assertEquals("11 Ave & W 59 St", closestStation.name);
    }

    @Test
    public void closestStationWithSlots() {
        // given
        CitiBikeService service = new CitiBikeServiceFactory().getService();
        Stations stationInfo = service.stationInfo().blockingGet();
        Stations stationStatus = service.stationStatus().blockingGet();

        double lat = 40.77185400202356;
        double lon = -73.98830454232788;

        // when
        FindStations findStations = new FindStations(stationInfo, stationStatus);
        Station closestStation = findStations.closestStationWithSlots(lat, lon);

        // then
        assertNotNull(closestStation);
        assertNotEquals(0, closestStation.num_docks_available);
        assertEquals("11 Ave & W 59 St", closestStation.name);
    }

}