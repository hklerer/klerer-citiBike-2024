package klerer.citibike.map;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import klerer.citibike.lambda.CitiBikeRequest;
import klerer.citibike.lambda.CitiBikeResponse;
import klerer.citibike.lambda.Location;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.*;

public class CitiBikeController {
    private CitiBikeComponent view;
    private JLabel fromLabel;
    private JLabel toLabel;
    private RoutePainter routePainter;
    private WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
    private LambdaService lambdaService = new LambdaServiceFactory().getService();
    private GeoPosition fromgp;
    private GeoPosition togp;
    private GeoPosition startgp;
    private GeoPosition endgp;
    private List<GeoPosition> routePoints = new ArrayList<>();

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

                if (fromgp == null) {
                    fromgp = position;
                    fromLabel.setText(fromgp.getLatitude() + ", " + fromgp.getLongitude());
                } else if (togp == null) {
                    togp = position;
                    toLabel.setText(togp.getLatitude() + ", " + togp.getLongitude());
                }
            }
        });
    }

    public void drawMap() {
        Location from = new Location(fromgp.getLatitude(), fromgp.getLongitude());
        Location to = new Location(togp.getLatitude(), togp.getLongitude());
        CitiBikeRequest request = new CitiBikeRequest(from, to);
        Disposable disposable = lambdaService.getStations(request)
                // tells Rx to request the data on a background Thread
                .subscribeOn(Schedulers.io())
                // tells Rx to handle the response on Swing's main Thread
                .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                .subscribe(
                        this::handleResponse,
                        Throwable::printStackTrace
                );
    }

    public void updateMap() {
        routePoints.clear();
        routePoints.add(fromgp);
        routePoints.add(startgp);
        routePoints.add(endgp);
        routePoints.add(togp);

        routePainter = new RoutePainter(routePoints);
        drawRoute();

        waypointPainter.setWaypoints(Set.of(
                new DefaultWaypoint(fromgp),
                new DefaultWaypoint(startgp),
                new DefaultWaypoint(endgp),
                new DefaultWaypoint(togp)
        ));

        zoomToFit();
    }

    public void handleResponse(CitiBikeResponse response) {
        startgp = new GeoPosition(response.start.lat, response.start.lon);
        endgp = new GeoPosition(response.end.lat, response.end.lon);

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