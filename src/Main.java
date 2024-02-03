import CampoMinado.Campo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
            System.err.println("Não foi possível aplicar o tema Nimbus");
        }
        ImageIcon mina = new ImageIcon("src/Icones/mina.png");
        ImageIcon code = new ImageIcon("src/Icones/code.png");

        JFrame frame = new JFrame(); // nova interface swing
        // expandir a tela
        frame.setExtendedState(JFrame.MAXIMIZED_VERT);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setResizable(false);
        frame.setTitle("Campo Minado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(mina.getImage());
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        Campo tabuleiro = new Campo();

        JPanel controles = new JPanel();
        controles.add(Campo.getLabelContador());
        controles.add(Campo.getBotaoReiniciar());


        frame.add(controles, BorderLayout.PAGE_START);
        frame.add(tabuleiro, BorderLayout.CENTER);

        frame.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                Point mouseLocation = e.getLocationOnScreen();
                Point frameLocation = frame.getLocationOnScreen();
                // Verifica se o mouse está no canto inferior esquerdo
                if (mouseLocation.x - frameLocation.x < 50 && mouseLocation.y - frameLocation.y > frame.getHeight() - 50) {
                    // Substitui a interface do jogo pelo disfarce
                    frame.getContentPane().removeAll();
                    System.out.print("Entrou no canto inferior esquerdo");
                    JLabel disfarce = getDisfarce(frame, controles, tabuleiro);
                    frame.add(disfarce);
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setIconImage(code.getImage());
                    frame.setTitle("");
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
        //frame.setSize(550, 550);

        frame.setVisible(true);
        //frame.setSize(550, 550);

    }

    private static JLabel getDisfarce(JFrame frame, JPanel controles, Campo tabuleiro) {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon iconeVscode = new ImageIcon("src/Icones/telaDisfarce.jpeg");
        Image imagem = iconeVscode.getImage();
        Image novaImagem = imagem.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        ImageIcon vscode = new ImageIcon(novaImagem);

        JLabel disfarce = new JLabel(vscode);

        disfarce.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Remove todos os componentes
                frame.getContentPane().removeAll();

                // Adiciona de volta os componentes originais
                frame.add(controles, BorderLayout.PAGE_START);
                frame.add(tabuleiro, BorderLayout.CENTER);
                frame.setIconImage(new ImageIcon("src/Icones/mina.png").getImage());
                frame.setTitle("Campo Minado");

                // Atualiza o frame
                frame.revalidate();
                frame.repaint();
                frame.setSize(500, 500);
                frame.setLocationRelativeTo(null);
            }
        });
        return disfarce;
    }
}