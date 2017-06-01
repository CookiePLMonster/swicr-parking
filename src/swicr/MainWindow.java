package swicr;

import swicr.logic.ParkingSimulation;
import swicr.view.CarSpriteCanvas;
import swicr.view.ClockLabel;

import javax.swing.*;

/**
 * Created on 2017-05-26.
 *
 * @author Adrian Zdanowicz
 */
public class MainWindow {
    private JPanel Main;
    private JButton addButton;
    private JPanel canvas;
    private JTextField requestedCar;
    private JButton retrieve;
    private JButton symulujButton;
    private JLabel clockField;
    private JButton zatrzymajZegarButton;

    private ParkingSimulation simulator;

    private int carNum = 0;

    public MainWindow() {
        simulator = new ParkingSimulation();

        addButton.addActionListener(e -> {
            simulator.insert(carNum++);
        });
        retrieve.addActionListener(e -> {
            int carID;
            try {
                carID = Integer.parseUnsignedInt(requestedCar.getText());
            } catch ( NumberFormatException ex ) { carID = 0; };
            simulator.callCarOut(carID);
        });

        symulujButton.addActionListener(e -> {
            simulator.maintenanceJob();
        });

        simulator.getGrid().setupCanvas((CarSpriteCanvas)canvas);
        simulator.setupTime((ClockLabel)clockField);
        simulator.start();
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        canvas = new CarSpriteCanvas();
        clockField = new ClockLabel();
    }
}
