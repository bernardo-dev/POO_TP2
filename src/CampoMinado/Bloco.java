package CampoMinado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Bloco extends JButton {
    private static boolean primeiroClick = false;
    private EstadoBloco estado;
    private TipoBloco tipo;

    private int numero;

    public Bloco(int linha, int coluna) {
        estado = EstadoBloco.FECHADO;
        tipo = TipoBloco.VAZIO;
        numero = 0;
        this.setFont(new Font("Arial", Font.PLAIN, 20));
        this.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)){
                    if (!primeiroClick) {
                        Campo.gerarMinas(10, linha, coluna);
                        primeiroClick = true;
                    }
                    System.out.println("Bloco" + tipo + ": [" + linha + ", " + coluna + "] Numero: " + numero);
                    if (estado == EstadoBloco.FECHADO) {
                        switch (tipo) {
                            case VAZIO:
                                setEstado(EstadoBloco.ABERTO);
                                setEnabled(false);
                                Campo.revelarBlocos(linha, coluna);
                                break;
                            case NUMERICO:
                                setEstado(EstadoBloco.ABERTO);
                                setEnabled(false);
                                setText(String.valueOf(getNumero()));
                                break;
                            case MINA:
                                Campo.revelarMinas();
                                break;
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    alterarEstado();
                }
            }
        });
    }

    private void alterarEstado(){
        switch (estado){
            case FECHADO:
                estado = EstadoBloco.ABERTO;
                System.out.println("[MARCADO]");
                break;
            case ABERTO:
                estado = EstadoBloco.FECHADO;
                System.out.println("[OCULTO]");
                break;
        }
    }

    public static boolean getPrimeiroClick(){
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

    public void incrementarNumero(){
        numero++;
    }
    public int getNumero(){
        return numero;
    }
}
