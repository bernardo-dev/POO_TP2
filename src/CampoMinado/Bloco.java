package CampoMinado;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Image;

public class Bloco extends JButton {
    // serve para detectar quando o usuario clicou pela primeira vez na rodada
    private static boolean primeiroClick = false;
    private EstadoBloco estado;
    private TipoBloco tipo;
    private int numero; // Quantide de bombas ao redor do bloco

    static ImageIcon bandeira = new ImageIcon("src/Icones/bandeira.png");
    static ImageIcon mina = new ImageIcon("src/Icones/mina.png");

    public Bloco(int linha, int coluna) {
        estado = EstadoBloco.FECHADO;
        tipo = TipoBloco.VAZIO;

        numero = 0;
        this.setFont(new Font("Arial", Font.PLAIN, 20));
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (Campo.jogoAcabou) {
                    return;
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (!primeiroClick && (tipo == TipoBloco.VAZIO)) {
                        Campo.gerarMinas(10, linha, coluna);
                        primeiroClick = true;
                    }

                    if (estado == EstadoBloco.FECHADO) {
                        switch (tipo) {
                            case VAZIO:
                                Campo.revelarBlocos(linha, coluna);
                                break;
                            case NUMERICO:
                                setEnabled(false);
                                setEstado(EstadoBloco.ABERTO);
                                setText(String.valueOf(getNumero()));
                                break;
                            case MINA:
                                Campo.revelarMinas(); // colocar um pop-up indicando q a pessoa perdeu
                                ImageIcon emojiTriste = new ImageIcon("src/Icones/emojiTriste.png");
                                Image image = emojiTriste.getImage(); // transforma o ícone em uma imagem
                                Image newimg = image.getScaledInstance(33, 33,  java.awt.Image.SCALE_SMOOTH); // redimensiona a imagem
                                emojiTriste = new ImageIcon(newimg);  // transforma a imagem de volta em um ícone
                                Campo.botaoReiniciar.setIcon(emojiTriste); // define o ícone redimensionado 
                                JOptionPane.showMessageDialog(null, "Que pena, você perdeu!", "Derrota!", JOptionPane.ERROR_MESSAGE);
                                // colocando um icon de carinha triste
                               
                                break;
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    alterarMarcado();
                }
            }
        });
    }

    // Logica das bandeiras, coloca bandeira apenas em blocos que nao foram abertos pelo usuario ainda
    private void alterarMarcado() {
        switch (estado) {
            case FECHADO:
            if (Campo.getContador() > 0) {
                this.setIconeMarcado();
                this.setEstado(EstadoBloco.MARCADO);
                this.setIcon(bandeira); // Adiciona o ícone da bandeira
                if (Campo.verificarVitoria()) {
                    Campo.mostrarVitoria();
                }
                
            }
                break;
            case MARCADO:
                this.setIconeVazio();
                this.setEstado(EstadoBloco.FECHADO);
                this.setIcon(null); // Remove o ícone da bandeira

                break;
        }
    }

    public static void setPrimeiroClick(boolean primeiroClick) {
        Bloco.primeiroClick = primeiroClick;
    }

    public static boolean getPrimeiroClick() {
        return primeiroClick;
    }

    public EstadoBloco getEstado() {
        return estado;
    }

    public void setEstado(EstadoBloco estado) {
        this.estado = estado;
    }

    public TipoBloco getTipo() {
        return tipo;
    }

    public void setTipo(TipoBloco tipo) {
        this.tipo = tipo;
    }

    public int getNumero() {
        return numero;
    }

    public void incrementarNumero() {
        numero++;
    }

    public void setIconeMina() {
        this.setIcon(mina);
    }

    public void setIconeMarcado() {
        this.setIcon(bandeira);
        this.setEstado(EstadoBloco.MARCADO);
        Campo.decrementarContador();
        

    }

    public void setIconeVazio() {
        this.setIcon(null);
        this.setEstado(EstadoBloco.FECHADO);
        Campo.incrementarContador();

    }

    public void setNumero(int numero) {
        this.numero = numero;
    }


    public void reset() {
        // Redefine o tipo e o estado do bloco
        this.setEnabled(true);
        this.setTipo(TipoBloco.VAZIO);
        this.setEstado(EstadoBloco.FECHADO); // todos os blocos tem que ficar fechados
        this.setNumero(0);

        // Remove qualquer ícone ou texto que possa estar presente
        this.setIcon(null);
        this.setText("");

        // Redefine o estado do jogo
        setPrimeiroClick(false);
    }


}
