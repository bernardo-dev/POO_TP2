import CampoMinado.Campo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.BorderLayout;

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
        frame.setSize(550, 550);
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

        // Cria um painel transparente
        JPanel glassPane = new JPanel();
        glassPane.setOpaque(false);
        glassPane.addMouseMotionListener(new MouseAdapter() {
        public void mouseMoved(MouseEvent e) {
            Point mouseLocation = e.getLocationOnScreen();
            Point frameLocation = frame.getLocationOnScreen();
            System.out.println("Mouse moved to (" + mouseLocation.x + ", " + mouseLocation.y + ")");
            // Verifica se o mouse está no canto inferior esquerdo
            if (mouseLocation.x - frameLocation.x < 50 && mouseLocation.y - frameLocation.y > frame.getHeight() - 50) {
                // Substitui a interface do jogo pelo disfarce
                frame.getContentPane().removeAll();
                System.out.print("Entrou no canto inferior esquerdo");
                JLabel disfarce = new JLabel(new ImageIcon("src/Icones/telaDisfarce.jpeg"));
                frame.add(disfarce);
                frame.revalidate();
                frame.repaint();
            }
        }
    });

// Define o painel como o GlassPane do JFrame
frame.setGlassPane(glassPane);
glassPane.setVisible(true);

        frame.setVisible(true);
    }
}