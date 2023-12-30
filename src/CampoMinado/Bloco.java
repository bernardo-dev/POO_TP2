package CampoMinado;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Bloco extends JButton {
    private static boolean primeiroClick = false;
    private EstadoBloco estado;
    private TipoBloco tipo;

    public Bloco(short linha, short coluna) {
        estado = EstadoBloco.FECHADO;
        this.setEnabled(false);
        this.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)){
                    if (!primeiroClick) {
                        Campo.gerarMinas(99, linha, coluna);
                        primeiroClick = true;
                    }
                    System.out.println("Bloco: [" + linha + ", " + coluna + "]");
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
}
