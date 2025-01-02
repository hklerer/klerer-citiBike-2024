package klerer.citibike.map;

import io.reactivex.rxjava3.core.Single;
import klerer.citibike.lambda.CitiBikeRequest;
import klerer.citibike.lambda.CitiBikeResponse;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LambdaService {
    @POST("/")
    Single<CitiBikeResponse> getStations(@Body CitiBikeRequest request);

}
