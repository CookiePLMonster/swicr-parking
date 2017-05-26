package swicr;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017-05-26.
 *
 * @author Adrian Zdanowicz
 */
public class CarSpriteCanvas extends JPanel {
    private List<Car> carList;

    public CarSpriteCanvas() {
        carList = new ArrayList<Car>();
        for (int i = 0; i < 5; i++ )
        {
            for ( int j = 0; j < 5; j++ )
            {
                carList.add( new Car( j * 75, i * 75 ));
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for ( Car car : carList )
            car.paint(g, 25, 25);
    }
}
