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
    private JSlider timescale;

    private ParkingSimulation simulator;

    public MainWindow() {
        simulator = new ParkingSimulation();

        addButton.addActionListener(e -> {
            simulator.insertRandom();
        });
        retrieve.addActionListener(e -> {
            int carID = -1;
            try {
                carID = Integer.parseUnsignedInt(requestedCar.getText());
            } catch (NumberFormatException ex) {}
            if ( carID != -1 ) {
                simulator.callCarOut(carID);
            }
            else {
                simulator.callRandomOut();
            }
        });

        symulujButton.addActionListener(e -> {
            simulator.maintenanceJob();
        });

        simulator.getGrid().setupCanvas((CarSpriteCanvas) canvas);
        simulator.setupTime((ClockLabel) clockField);
        simulator.start();
        zatrzymajZegarButton.addActionListener(e -> {
            String str;
            if (simulator.toggleTimeState()) {
                str = "Zatrzymaj zegar";
            } else {
                str = "Uruchom zegar";
            }

            SwingUtilities.invokeLater(() -> zatrzymajZegarButton.setText(str));
        });
        timescale.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                simulator.setTimeScale(source.getValue());
            }
        });
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
