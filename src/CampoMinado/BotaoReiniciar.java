package CampoMinado;

import javax.swing.*;

import com.sun.tools.javac.Main;

// Botão que reinicia o jogo
public class BotaoReiniciar extends JButton {
    public BotaoReiniciar() {
        this.setIconeFeliz();

        // Remove o foco do botão
        this.setFocusable(false); 

        // Adiciona um listener para quando o botão for clicado
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

        // Limpa as coordenadas das minas
        Campo.getCoordenadasMinas().clear();
        Campo.setJogoAcabou(false);

        // Atualiza o contador
        Campo.getLabelContador().reset();
        Campo.getLabelContador().atualizarContador();
    }
    
    // Define o ícone do botão para o ícone feliz
    public void setIconeFeliz() {
        ImageIcon tuglife = new ImageIcon(BotaoReiniciar.class.getResource("/Icones/feliz.png"));
        this.setIcon(tuglife);
    }

    // Define o ícone do botão para o ícone tristew
    public void setIconeTriste() {
        ImageIcon triste = new ImageIcon(BotaoReiniciar.class.getResource("/Icones/triste.png"));
        this.setIcon(triste);
    }

    // Define o ícone do botão para o ícone de tugs
    public void setIconeTugLife() {
        ImageIcon tuglife = new ImageIcon(BotaoReiniciar.class.getResource("/Icones/tuglife.png"));
        this.setIcon(tuglife);
    }
}




