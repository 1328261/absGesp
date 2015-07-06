import java.awt.*;
import javax.swing.*;

public class Mondrian extends JFrame {

    public Mondrian() {
        add(new Board());

        setResizable(false);
        pack();

        setTitle("Mondrian");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Mondrian window = new Mondrian();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
    }

}
