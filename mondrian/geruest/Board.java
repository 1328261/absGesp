import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class Board extends JPanel {

    private final int WIDTH = 200;
    private final int HEIGHT = 200;
//hallo

    public Board() {
        super();

        setBackground(Color.WHITE);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);
    }

}
