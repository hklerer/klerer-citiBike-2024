package klerer.citibike;

import klerer.citibike.json.Station;
import klerer.citibike.json.Stations;

import java.util.Objects;

public class FindStations {
    private final Stations stationInfo;
    private final Stations stationStatus;

    public FindStations(Stations stationInfo, Stations stationStatus) {
        this.stationInfo = stationInfo;
        this.stationStatus = stationStatus;

    }

    public Station stationById(String stationId) {
        for (Station station : stationStatus.data.stations) {
            if (Objects.equals(stationId, station.station_id)) {
                return station;
            }
        }
        return null;

    }

    public Station closestStationWithBikes(double lat, double lon) {
        Station closestStation = null;
        double closest = Double.MAX_VALUE;

        for (Station station : stationStatus.data.stations) {
            for (Station s : stationInfo.data.stations) {
                if (station.num_bikes_available > 0) {
                    double distance = calculateDistance(lat, lon, s.lat, s.lon);
                    if (distance < closest) {
                        closestStation = station;
                        closest = distance;
                    }
                }
            }
        }
        return closestStation;
    }

    public Station closestStationWithSlots(double lat, double lon) {
        Station closestStation = null;
        double closest = Double.MAX_VALUE;

        for (Station station : stationStatus.data.stations) {
            if (station.num_docks_available > 0) {
                double distance = calculateDistance(lat, lon, station.lat, station.lon);
                if (distance < closest) {
                    closestStation = station;
                    closest = distance;
                }
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
