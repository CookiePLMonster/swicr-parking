package swicr;

import swicr.logic.*;
import swicr.view.CarSpriteCanvas;

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
    private Fifo tasks;

    private ParkingSimulation simulator;

    private int carNum = 0;

    public MainWindow() {
        simulator = new ParkingSimulation();

        addButton.addActionListener(e -> {
            if ( simulator.addCar( new Car(carNum++) ) ) {
                canvas.repaint();
            }
        });
        retrieve.addActionListener(e -> {
            int carID;
            try {
                carID = Integer.parseUnsignedInt(requestedCar.getText());
            } catch ( NumberFormatException ex ) { carID = 0; };
            simulator.findWay(carID);
        });

        symulujButton.addActionListener(e -> {
            while(tasks.get() != null) {
                canvas.repaint();
            }
        });

        simulator.getGrid().setupCanvas((CarSpriteCanvas)canvas);
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
    }
}
