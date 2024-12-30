package klerer.citiBike.json;

public class StationResponse {
    //CHECKSTYLE:OFF
    public String station_id;
    //CHECKSTYLE:ON

    public String name;
    public double lon;
    public double lat;



    public StationResponse(String stationId, String name, int lon, int lat) {
        //CHECKSTYLE:OFF
        this.station_id = stationId;
        //CHECKSTYLE:ON
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }


}
