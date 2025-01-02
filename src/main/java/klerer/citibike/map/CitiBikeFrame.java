package klerer.citibike.map;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import klerer.citibike.lambda.CitiBikeRequest;
import klerer.citibike.lambda.CitiBikeResponse;
import klerer.citibike.lambda.Location;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;


public class CitiBikeFrame extends JFrame {

    CitiBikeComponent view = new CitiBikeComponent();
    CitiBikeController controller;
    JLabel fromLabel;
    JLabel toLabel;
    JButton map;

    public CitiBikeFrame() {
        setTitle("CitiBike Map");
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        fromLabel = new JLabel();
        toLabel = new JLabel();
        controller = new CitiBikeController(fromLabel, toLabel, view);
        controller.setFromAndTo();

        map = new JButton("Map");


        map.addActionListener(e -> controller.drawMap());


    }


}
