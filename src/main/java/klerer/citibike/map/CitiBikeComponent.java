package klerer.citibike.map;

import org.jxmapviewer.*;
import org.jxmapviewer.input.*;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;

public class CitiBikeComponent extends JComponent {
    private final JXMapViewer mapViewer;
    private GeoPosition from;
    private GeoPosition to;

    public CitiBikeComponent() {
        mapViewer = new JXMapViewer();
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        tileFactory.setThreadPoolSize(8);

        GeoPosition nyc = new GeoPosition(40.7128, -74.0060);

        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(nyc);


        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));

        setLayout(new BorderLayout());
        add(mapViewer, BorderLayout.CENTER);

        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                Point2D.Double point = new Point2D.Double(x, y);
                GeoPosition position = mapViewer.convertPointToGeoPosition(point);
                if (from == null) {
                    from = position;
                } else {
                    to = position;
                }
            }
        });

    }

    public void drawRoutes(RoutePainter routePainter, WaypointPainter<Waypoint> waypointPainter) {
        List<Painter<JXMapViewer>> painter = List.of(routePainter, waypointPainter);
        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(painter);
        mapViewer.setOverlayPainter(compoundPainter);
    }

    public GeoPosition getFrom() {
        return from;
    }

    public void setFrom(GeoPosition from) {
       this.from = from;
    }

    public GeoPosition getTo() {
        return to;
    }

    public void setTo(GeoPosition to) {
        this.to = to;
    }

    public JXMapViewer getMapViewer() {
        return mapViewer;
    }
}