package CampoMinado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Bloco extends JButton {
    private static boolean primeiroClick = false;
    private EstadoBloco estado;
    private TipoBloco tipo;
    private int numero; // Quantide de bombas ao redor do bloco
    private int contador; 

    static ImageIcon bandeira = new ImageIcon("src/Icones/bandeira.png");
    static ImageIcon mina = new ImageIcon("src/Icones/mina.png");

    public Bloco(int linha, int coluna) {
        estado = EstadoBloco.FECHADO;
        tipo = TipoBloco.VAZIO;
        numero = 0;
        this.setFont(new Font("Arial", Font.PLAIN, 20));
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (!primeiroClick && (tipo == TipoBloco.VAZIO)) {
                        Campo.gerarMinas(10, linha, coluna);
                        primeiroClick = true;
                    }
                    System.out.println("Bloco " + tipo + ": [" + linha + ", " + coluna + "] Numero: " + numero);
                    if (estado == EstadoBloco.FECHADO) {
                        switch (tipo) {
                            case VAZIO:
                                setEnabled(false);
                                Campo.revelarBlocos(linha, coluna);
                                break;
                            case NUMERICO:
                                setEnabled(false);
                                setText(String.valueOf(getNumero()));
                                break;
                            case MINA:
                                Campo.revelarMinas();
                                break;
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    alterarMarcado();
                }
            }
        });
    }

    private void alterarMarcado() {
        switch (estado) {
            case FECHADO:
                this.setIconeMarcado();
                this.setEstado(EstadoBloco.MARCADO);
                break;
            case MARCADO:
                this.setIconeVazio();
                this.setEstado(EstadoBloco.FECHADO);
                break;
        }
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
        this.contador++; // contar o numero de bandeiras marcadas
    }

    public void setIconeVazio() {
        this.setIcon(null);
        this.setEstado(EstadoBloco.FECHADO);
        if (this.contador > 0) {
            this.contador--;
        }
    }

    public void setContador(int contador){
        this.contador = contador;
        
    }

    public int getContador(){
        return contador;
    }

    
}
