package klerer.citibike.map;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import klerer.citibike.lambda.CitiBikeRequest;
import klerer.citibike.lambda.Location;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.geom.Point2D;

public class CitiBikeController {
    CitiBikeComponent component;
    CitiBikeFrame frame;
    JLabel fromLabel;
    JLabel toLabel;
    RoutePainter routePainter;
    WaypointPainter<Waypoint> waypointPainter;
    Location from = new Location();
    Location to = new Location();
    LambdaService lambdaService = new LambdaServiceFactory().getService();


    public CitiBikeController (JLabel fromLabel, JLabel toLabel, CitiBikeComponent view) {
        this.fromLabel = fromLabel;
        this.toLabel = toLabel;
        this.component = view;

    }

    public void drawMap() {
        CitiBikeRequest request = new CitiBikeRequest();
        request.from = from;
        request.to = to;
        Disposable disposable = lambdaService.sendRequest(request)
                // tells Rx to request the data on a background Thread
                .subscribeOn(Schedulers.io())
                // tells Rx to handle the response on Swing's main Thread
                .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                //.observeOn(AndroidSchedulers.mainThread()) // Instead use this on Android only
                .subscribe(
                        (response) -> handleResponse(response),
                        Throwable::printStackTrace);
    }

    public void setFromAndTo() {
        GeoPosition fromgp = component.getFrom();
        GeoPosition togp = component.getTo();
        if (fromgp != null && togp != null) {
            fromLabel.setText(fromgp.getLatitude() + ", "
                    + fromgp.getLongitude());
            toLabel.setText(togp.getLatitude() + ", "
                    + togp.getLongitude());

            from.lat = fromgp.getLatitude();
            from.lon = fromgp.getLongitude();
            to.lat = togp.getLatitude();
            to.lon = togp.getLongitude();
        }

    }

    public void handleResponse(CitiBikeRequest response) {
        System.out.println("got a response");
    }




}
