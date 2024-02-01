import CampoMinado.Campo;
import CampoMinado.Direcao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Se Nimbus não está disponível, você pode definir o L&F para qualquer um que você quiser.
        }
        ImageIcon flag = new ImageIcon("src/Icones/mina.png");

        JFrame frame = new JFrame(); // nova interface swing
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(600,600));
        frame.setResizable(false);
        frame.setTitle("Campo Minado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(flag.getImage());
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        Campo tabuleiro = new Campo();

        JPanel controles = new JPanel();
        controles.add(Campo.getLabelContador());
        controles.add(Campo.getBotaoReiniciar());


        frame.add(controles, BorderLayout.PAGE_START);
        frame.add(tabuleiro, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}