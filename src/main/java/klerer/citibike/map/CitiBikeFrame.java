package klerer.citibike.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CitiBikeFrame extends JFrame {

    CitiBikeComponent view = new CitiBikeComponent();
    CitiBikeController controller;
    JLabel fromLabel;
    JLabel toLabel;
    JButton map;
    JButton clear;

    public CitiBikeFrame() {
        setTitle("CitiBike Map");
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        fromLabel = new JLabel();
        toLabel = new JLabel();
        controller = new CitiBikeController(fromLabel, toLabel, view);

        map = new JButton("Map");
        map.addActionListener(new ActionListener()   {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.drawMap();
            }
        });

        clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clearMap();
            }
        });

        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(4, 1));
        controls.add(fromLabel);
        controls.add(toLabel);
        controls.add(map);
        controls.add(clear);

        add(controls, BorderLayout.SOUTH);
        add(view, BorderLayout.CENTER);

        //controller.drawRoute();
    }


}
