package CampoMinado;

import javax.swing.*;

public class BotaoReiniciar extends JButton {
    public BotaoReiniciar() {
        ImageIcon tuglife = new ImageIcon("src/Icones/tuglife.png");
        this.setIcon(tuglife);
        this.setFocusable(false);

        // Passa reiniciando cada bloco
        // Reinicia o contador
        // Exclui as coordenadas das minas
        this.addActionListener(evento -> {
            Bloco.setPrimeiroClick(false);
            for (Bloco[] bloco : Campo.getBlocos()) {
                for (Bloco value : bloco) {
                    value.reset();
                }
            }

            Campo.getCoordenadasMinas().clear();

            Campo.getLabelContador().reset();
            Campo.getLabelContador().atualizarContador();
        });
    }
}
