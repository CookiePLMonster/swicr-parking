package swicr;

import swicr.logic.ParkingSimulation;
import swicr.view.CarSpriteCanvas;
import swicr.view.ClockLabel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.InputMethodListener;

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
            try {
                int carID = Integer.parseUnsignedInt(requestedCar.getText());
                simulator.callCarOut(carID);
            } catch (NumberFormatException ex) {}
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
