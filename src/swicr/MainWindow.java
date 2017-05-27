package swicr;

import swicr.logic.Car;
import swicr.logic.ParkingGrid;
import swicr.view.CarSpriteCanvas;

import javax.swing.*;

/**
 * Created on 2017-05-26.
 *
 * @author Adrian Zdanowicz
 */
public class MainWindow {
    private JPanel Main;
    private JButton symulujButton;
    private JPanel canvas;

    private ParkingGrid grid;

    private int carNum = 0;

    public MainWindow() {
        symulujButton.addActionListener(e -> {
            if ( grid.addCar( new Car(Integer.toString(carNum++)) ) ) {
                canvas.repaint();
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
        grid = new ParkingGrid();

        canvas = new CarSpriteCanvas(grid);
    }
}
