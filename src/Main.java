import CampoMinado.Campo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    public static void main(String[] args) {
        try {
            // Tenta aplicar o tema Nimbus
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) { 
                // Se encontrar o tema Nimbus
                if ("Nimbus".equals(info.getName())) { 
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Não foi possível aplicar o tema Nimbus");
        }

        // Cria ícones a partir de arquivos de imagem

        ImageIcon mina = new ImageIcon("src/Icones/mina.png");
        ImageIcon code = new ImageIcon("src/Icones/code.png");

        // Cria uma nova janela JFrame
        JFrame frame = new JFrame(); 
        // expandir a tela
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setResizable(false);
        frame.setTitle("Campo Minado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(mina.getImage());
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // Centraliza a janela

        // Cria o campo de jogo
        Campo tabuleiro = new Campo();

        JPanel controles = new JPanel();
        controles.add(Campo.getLabelContador());
        controles.add(Campo.getBotaoReiniciar());


        // Cria um painel para os controles do jogo
        frame.add(controles, BorderLayout.PAGE_START);
        frame.add(tabuleiro, BorderLayout.CENTER);

        JLabel instrucoes = new JLabel("<html>Use as setas para mover o foco<br>Pressione espaço para marcar um bloco<br>Pressione Enter para abrir um bloco<br>Clicar R para reiniciar</html>");
        // mostrar um texto indicando os comandos do teclado
        instrucoes.setHorizontalAlignment(JLabel.CENTER);
        frame.add(instrucoes, BorderLayout.PAGE_END);

        // Adiciona um listener para quando o mouse se mover
        frame.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                Point mouseLocation = e.getLocationOnScreen();
                Point frameLocation = frame.getLocationOnScreen();
                // Verifica se o mouse está no canto inferior esquerdo
                if (mouseLocation.x - frameLocation.x < 50 && mouseLocation.y - frameLocation.y > frame.getHeight() - 50) {
                    // Substitui a interface do jogo pelo disfarce
                    frame.getContentPane().removeAll();
                    System.out.print("Entrou no canto inferior esquerdo");
                    // Cria o disfarce
                    JLabel disfarce = getDisfarce(frame, controles, tabuleiro, instrucoes); 
                    frame.add(disfarce);
                    // Maximiza a janela
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                    frame.setIconImage(code.getImage());
                    frame.setTitle("");
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        frame.setVisible(true);

    }

    private static JLabel getDisfarce(JFrame frame, JPanel controles, Campo tabuleiro, JLabel instrucoes) {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon iconeVscode = new ImageIcon("src/Icones/telaDisfarce.jpeg");
        Image imagem = iconeVscode.getImage();
        Image novaImagem = imagem.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        ImageIcon vscode = new ImageIcon(novaImagem);


        //Cria um JLabel com a imagem do disfarce
        JLabel disfarce = new JLabel(vscode); 

        // Adiciona um listener para quando o usuário clicar na tela
        disfarce.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Remove todos os componentes
                frame.getContentPane().removeAll();

                // Adiciona de volta os componentes originais
                frame.add(controles, BorderLayout.PAGE_START);
                frame.add(tabuleiro, BorderLayout.CENTER);
                frame.add(instrucoes, BorderLayout.PAGE_END);
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