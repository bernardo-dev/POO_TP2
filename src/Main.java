import CampoMinado.Campo;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        ImageIcon flag = new ImageIcon("src/Icones/mina.png");

        JFrame frame = new JFrame(); // nova interface swing
        frame.setSize(550, 550);
        frame.setResizable(false);
        frame.setTitle("Campo Minado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(flag.getImage());
        frame.setLayout(new BorderLayout());

        Campo tabuleiro = new Campo();
        frame.add(Campo.getLabelContador(), BorderLayout.NORTH);
        frame.setLocationRelativeTo(null);
        frame.add(Campo.getBotaoReiniciar(), BorderLayout.SOUTH);

        frame.add(tabuleiro);

        frame.setVisible(true);
    }
}