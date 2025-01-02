package klerer.citibike.map;

import io.reactivex.rxjava3.core.Single;
import klerer.citibike.lambda.CitiBikeRequest;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LambdaService {
    @POST("/")
    Single<CitiBikeRequest> sendRequest(@Body CitiBikeRequest request);

}
