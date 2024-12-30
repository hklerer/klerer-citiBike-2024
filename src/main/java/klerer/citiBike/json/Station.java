package klerer.citiBike.json;

public class Station {
    //CHECKSTYLE:OFF
    public String station_id;
    //CHECKSTYLE:ON

    public String name;
    public double lon;
    public double lat;

    //CHECKSTYLE:OFF
    public int num_ebikes_available;
    //CHECKSTYLE:ON

    //CHECKSTYLE:OFF
    public int num_docks_available;
    //CHECKSTYLE:ON

    //CHECKSTYLE:OFF
    public int num_bikes_available;
    //CHECKSTYLE:ON

    public Station(String stationId, String name, int lon, int lat) {
        //CHECKSTYLE:OFF
        this.station_id = stationId;
        //CHECKSTYLE:ON
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }

    public Station(String stationId, String name, int lon, int lat,
                          int numBikesAvailable, int numEbikesAvailable, int numDocksAvailable) {
        //CHECKSTYLE:OFF
        this.station_id = stationId;
        //CHECKSTYLE:ON
        this.num_bikes_available = numBikesAvailable;
        this.num_ebikes_available = numEbikesAvailable;
        this.num_docks_available = numDocksAvailable;
    }



}
