package klerer.citibike;

import klerer.citibike.json.Station;
import klerer.citibike.json.Stations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindStationsTest {
    @Test
    public void stationById() {
        // given
        FindStations findStations = new FindStations();
        CitiBikeService service = new CitiBikeServiceFactory().getService();
        Stations stationInfo = service.stationStatus().blockingGet();
        Station[] stations = stationInfo.data.stations;
        String id = stations[0].station_id;

        // when
        Station station = findStations.stationById(stations, id);

        // then
        assertNotNull(station);
        assertEquals(id, station.station_id);
    }

    @Test
    public void closestStationWithBikes() {
        // given
        FindStations findStations = new FindStations();
        CitiBikeService service = new CitiBikeServiceFactory().getService();
        Stations stationInfo = service.stationStatus().blockingGet();
        Station[] stations = stationInfo.data.stations;
        double lat = 40.77185400202356;
        double lon = -73.98830454232788;

        // when
        Station closestStation = findStations.closestStationWithBikes(stations, lat, lon);

        // then
        assertNotNull(stations);
        assertNotEquals(0, closestStation.num_bikes_available);
        assertEquals("11 Ave & W 59 St", closestStation.name);
    }

    @Test
    public void closestStationWithSlots() {
        // given
        FindStations findStations = new FindStations();
        CitiBikeService service = new CitiBikeServiceFactory().getService();
        Stations stationInfo = service.stationStatus().blockingGet();
        Station[] stations = stationInfo.data.stations;
        double lat = 40.77185400202356;
        double lon = -73.98830454232788;

        // when
        Station closestStation = findStations.closestStationWithSlots(stations, lat, lon);

        // then
        assertNotNull(stations);
        assertNotEquals(0, closestStation.num_docks_available);
        assertEquals("11 Ave & W 59 St", closestStation.name);
    }

}