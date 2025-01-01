package klerer.citibike.map;

import javax.swing.*;
import java.awt.*;

public class CitiBikeFrame extends JFrame {

    public CitiBikeFrame() {
        setTitle("CitiBike Map");
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextField from = new JTextField();
        JTextField to = new JTextField();

    }
}
