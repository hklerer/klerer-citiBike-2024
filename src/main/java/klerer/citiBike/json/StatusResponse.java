package klerer.citiBike.json;

public class StatusResponse {
    //CHECKSTYLE:OFF
    public String station_id;
    //CHECKSTYLE:ON


    //CHECKSTYLE:OFF
    public int num_ebikes_available;
    //CHECKSTYLE:ON

    //CHECKSTYLE:OFF
    public int num_docks_available;
    //CHECKSTYLE:ON

    //CHECKSTYLE:OFF
    public int num_bikes_available;
    //CHECKSTYLE:ON

    public StatusResponse(String stationId, String name, int lon, int lat,
                           int numBikesAvailable, int numEbikesAvailable, int numDocksAvailable) {
        //CHECKSTYLE:OFF
        this.station_id = stationId;
        //CHECKSTYLE:ON
        this.num_bikes_available = numBikesAvailable;
        this.num_ebikes_available = numEbikesAvailable;
        this.num_docks_available = numDocksAvailable;
    }

}
