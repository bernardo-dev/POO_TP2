import CampoMinado.Campo;

import javax.swing.*;
import java.awt.*;

public class Main {
    enum tipos {VAZIO, MINA, NUMERO}
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(550, 550);
        frame.setResizable(false);
        frame.setTitle("Campo Minado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        Campo tabuleiro = new Campo();
        frame.add(tabuleiro);

        frame.setVisible(true);
    }
}