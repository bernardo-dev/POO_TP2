package CampoMinado;

import javax.swing.*;

public class BotaoReiniciar extends JButton {
    public BotaoReiniciar() {
        this.setIconeFeliz();
        this.setFocusable(false);

        this.addActionListener(evento -> reiniciar());
    }

    public void reiniciar() {
        // Passa reiniciando cada bloco
        // Reinicia o contador
        // Exclui as coordenadas das minas
        Bloco.setPrimeiroClick(false);

        this.setIconeFeliz();

        for (Bloco[] bloco : Campo.getBlocos()) {
            for (Bloco value : bloco) {
                value.reset();
            }
        }

        Campo.getCoordenadasMinas().clear();
        Campo.setJogoAcabou(false);

        Campo.getLabelContador().reset();
        Campo.getLabelContador().atualizarContador();
    }

    public void setIconeFeliz() {
        ImageIcon tuglife = new ImageIcon("src/Icones/feliz.png");
        this.setIcon(tuglife);
    }

    public void setIconeTriste() {
        ImageIcon triste = new ImageIcon("src/Icones/triste.png");
        this.setIcon(triste);
    }

    public void setIconeTugLife() {
        ImageIcon tuglife = new ImageIcon("src/Icones/tuglife.png");
        this.setIcon(tuglife);
    }
}




