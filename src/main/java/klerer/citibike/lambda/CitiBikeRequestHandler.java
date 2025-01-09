package klerer.citibike.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;
import klerer.citibike.CitiBikeService;
import klerer.citibike.CitiBikeServiceFactory;
import klerer.citibike.FindStations;
import klerer.citibike.json.Station;
import klerer.citibike.json.Stations;

public class CitiBikeRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, CitiBikeResponse> {
    StationsCache stationsCache = new StationsCache();

    @Override
    public CitiBikeResponse handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        String body = event.getBody();
        Gson gson = new Gson();
        CitiBikeRequest request = gson.fromJson(body, CitiBikeRequest.class);

        CitiBikeService service = stationsCache.getService();

        Stations stationInfo = stationsCache.getStations();
        Stations stationStatus = service.stationStatus().blockingGet();
        FindStations findStations = new FindStations(stationInfo, stationStatus);

        Station start = findStations.closestStationWithBikes(request.from.lat, request.from.lon);
        Station end = findStations.closestStationWithSlots(request.to.lat, request.to.lon);

        return new CitiBikeResponse(request.from, request.to, start, end);
    }

}
