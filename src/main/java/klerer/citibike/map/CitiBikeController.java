package klerer.citibike.map;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import klerer.citibike.lambda.CitiBikeRequest;
import klerer.citibike.lambda.Location;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CitiBikeController {
    CitiBikeComponent view;
    JLabel fromLabel;
    JLabel toLabel;
    RoutePainter routePainter;
    WaypointPainter<Waypoint> waypointPainter;
    Location from;
    Location to;
    LambdaService lambdaService = new LambdaServiceFactory().getService();
    GeoPosition fromgp;
    GeoPosition togp;
    GeoPosition startgp;
    GeoPosition endgp;
    List<GeoPosition> routePoints = new ArrayList<>();


    public CitiBikeController(JLabel fromLabel, JLabel toLabel, CitiBikeComponent view) {
        this.fromLabel = fromLabel;
        this.toLabel = toLabel;
        this.view = view;
        view.getMapViewer().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                Point2D.Double point = new Point2D.Double(x, y);
                GeoPosition position = view.getMapViewer().convertPointToGeoPosition(point);
                if (view.getFrom() == null) {
                    fromgp = position;
                } else {
                    togp = position;
                }
                setFromAndTo();
            }
        });
    }


    public void drawMap() {
        CitiBikeRequest request = new CitiBikeRequest(from, to);
        request.from = from;
        request.to = to;
        Disposable disposable = lambdaService.getStations(request)
                // tells Rx to request the data on a background Thread
                .subscribeOn(Schedulers.io())
                // tells Rx to handle the response on Swing's main Thread
                .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                //.observeOn(AndroidSchedulers.mainThread()) // Instead use this on Android only
                .subscribe(
                        (response) -> handleResponse(request),
                        Throwable::printStackTrace);
        updateMap();

    }

    public void updateMap() {
        routePoints.add(fromgp);
        routePoints.add(togp);
        routePoints.add(startgp);
        routePoints.add(endgp);
        routePainter = new RoutePainter(routePoints);

        waypointPainter.setWaypoints(Set.of(
                new DefaultWaypoint(fromgp),
                new DefaultWaypoint(startgp),
                new DefaultWaypoint(endgp),
                new DefaultWaypoint(togp)

        ));
        zoomToFit();
    }


    public void setFromAndTo() {
        fromgp = view.getFrom();
        togp = view.getTo();

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
        System.out.println("Got a response");

       // startgp = new GeoPosition(response.start.lat, response.start.lon);
      //  endgp = new GeoPosition(response.end.lat, response.end.lon);

        updateMap();
    }

    public void zoomToFit() {
        view.getMapViewer().zoomToBestFit(
                Set.of(fromgp, startgp, endgp, togp),
                1.0
        );
    }

    public void drawRoute() {
        view.drawRoutes(routePainter, waypointPainter);
    }

    public void clearMap() {
        routePainter.setTrack(new ArrayList<>());
        waypointPainter.setWaypoints(Set.of());

        fromLabel.setText("");
        toLabel.setText("");

        fromgp = null;
        togp = null;
        startgp = null;
        endgp = null;
        view.originalMapState();
    }

}
