package klerer.citibike;

import io.reactivex.rxjava3.core.Single;
import klerer.citibike.json.Stations;
import retrofit2.http.GET;

public interface CitiBikeService {
    @GET("/gbfs/en/station_information.json")
    Single<Stations> stationInfo();

    @GET("/gbfs/en/station_status.json")
    Single<Stations> stationStatus();
}
