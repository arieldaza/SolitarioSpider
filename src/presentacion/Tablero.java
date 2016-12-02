package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * @author Ariel Daza
 * @version 1.0
 * @created 27-Nov.-2016 10:43:35 a.m.
 */
public class Tablero extends JFrame {

    private Juego juego = new Juego();

    public Tablero() {

        this.setLayout(new BorderLayout());
        this.setSize(1025, 730);
        this.setBackground(Color.yellow);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        this.getContentPane().add(juego, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        Tablero tablero = new Tablero();

    }

}
