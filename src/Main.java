import CampoMinado.Campo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

    // Salva o tamanho original do frame
    private static Dimension originalSize; 
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                // Verifica se o nome do tema atual é "Nimbus"
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    // Interrompe o loop, pois já encontramos e aplicamos o tema desejado
                    break;
                }
            }
        } catch (Exception e) {
            // // Se houver algum erro ao tentar aplicar o tema (por exemplo, o tema não está instalado), 
            // imprime uma mensagem de erro no console
            System.err.println("Não foi possível aplicar o tema Nimbus");
        }
        ImageIcon mina = new ImageIcon("src/Icones/mina.png");
        ImageIcon code = new ImageIcon("src/Icones/code.png");

        JFrame frame = new JFrame(); // nova interface swing
        // expandir a tela
        
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

        originalSize = frame.getSize();


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
                    frame.setSize(1500, 650);
                    frame.setIconImage(code.getImage());
                    frame.setTitle("");
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        frame.setVisible(true);

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
                frame.setSize(originalSize); // Redefina o tamanho do quadro para o tamanho original


                frame.setLocationRelativeTo(null);
            }
        });
        return disfarce;
    }
}