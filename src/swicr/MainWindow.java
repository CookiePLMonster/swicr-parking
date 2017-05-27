package swicr;

import swicr.logic.Car;
import swicr.logic.ParkingGrid;
import swicr.view.CarSpriteCanvas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created on 2017-05-26.
 *
 * @author Adrian Zdanowicz
 */
public class MainWindow {
    private JPanel Main;
    private JButton symulujButton;
    private JPanel canvas;
    private JTextField requestedCar;
    private JButton retrieve;

    private ParkingGrid grid;

    private int carNum = 0;

    public MainWindow() {
        symulujButton.addActionListener(e -> {
            if ( grid.addCar( new Car(carNum++) ) ) {
                canvas.repaint();
            }
        });
        retrieve.addActionListener(e -> {
            try {
                int carID = Integer.parseUnsignedInt(requestedCar.getText());
                if ( grid.retrieveCarById(carID) ) {
                    canvas.repaint();
                }
            } catch ( NumberFormatException ex ) { };
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
        grid = new ParkingGrid();

        canvas = new CarSpriteCanvas(grid);
    }
}
