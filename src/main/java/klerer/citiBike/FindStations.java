package klerer.citiBike;

import klerer.citiBike.json.Data;
import klerer.citiBike.json.Station;

import java.util.Objects;

public class FindStations {

    public Station stationById(Station[] stations, String stationId) {
        Station foundStation = null;
        for (Station station: stations) {
            if (Objects.equals(stationId, station.station_id)) {
                foundStation = station;
            }
        }
        return foundStation;

    }

    public Station closestStation(int lon, int lat) {

        return;

    }



}
