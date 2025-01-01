package klerer.citibike.lambda;

import klerer.citibike.json.Station;

public class CitiBikeResponse {
    public Location from;
    public Location to;
    public Station start;
    public Station end;

    public CitiBikeResponse(Location from, Location to, Station start, Station end) {
        this.from = from;
        this.to = to;
        this.start = start;
        this.end = end;
    }

}
