package CampoMinado;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Bloco extends JButton {
    private static boolean primeiroClick = false; // Impede que gere mais minas
    private EstadoBloco estado;
    private TipoBloco tipo;

    public Bloco(short linha, short coluna) { // construtor
        estado = EstadoBloco.FECHADO;
        this.setEnabled(false);
        this.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e)  { // Quando o botao for clicado
                if (SwingUtilities.isLeftMouseButton(e)){
                    if (!primeiroClick) { // Se for o primeiro clique gera as minas
                        Campo.gerarMinas(99, linha, coluna);
                        primeiroClick = true; // Impede que gere mais minas
                    }
                    System.out.println("Bloco: [" + linha + ", " + coluna + "]"); 
                } else if (SwingUtilities.isRightMouseButton(e)) { // Se for o botao direito
                    alterarEstado(); // Altera o estado do bloco
                }
            }
        });
    }

    private void alterarEstado(){
        switch (estado){
            case FECHADO:
                estado = EstadoBloco.ABERTO;
                System.out.println("[MARCADO]"); // 
                break;
            case ABERTO:
                estado = EstadoBloco.FECHADO;
                System.out.println("[OCULTO]");
                break;
        }
    }

    // setter e getter
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
