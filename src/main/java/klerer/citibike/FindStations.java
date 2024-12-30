package klerer.citibike;

import klerer.citibike.json.Station;

import java.util.Objects;

public class FindStations {

    public Station stationById(Station[] stations, String stationId) {
        Station foundStation = null;
        for (Station station : stations) {
            if (Objects.equals(stationId, station.station_id)) {
                foundStation = station;
                return station;
            }
        }
        return foundStation;

    }

    public Station closestStationWithBikes(Station[] stations, int lat, int lon) {
        Station closestStation = null;
        int closest = 0;

        for (Station station : stations) {
            if ((calculateDistance(lat, lon, station.lat, station.lon)) > closest
                    && station.num_bikes_available > 0) {
                closestStation = station;
            }
        }
        return closestStation;

    }

    public Station closestStationWithSlots(Station[] stations, int lat, int lon) {
        Station closestStation = null;
        int closest = 0;

        for (Station station : stations) {
            if ((calculateDistance(lat, lon, station.lat, station.lon)) > closest
                    && station.num_docks_available > 0) {
                closestStation = station;
            }
        }
        return closestStation;
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0;

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double differenceInLat = lat2Rad - lat1Rad;
        double differenceInLon = lon2Rad - lon1Rad;

        // Haversine formula
        double a = Math.sin(differenceInLat / 2) * Math.sin(differenceInLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(differenceInLon / 2) * Math.sin(differenceInLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }



}
