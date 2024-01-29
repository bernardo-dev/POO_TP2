package CampoMinado;

import javax.swing.JLabel;
import java.awt.Font;

public class LabelContador extends JLabel{
    private int contador;

    public LabelContador() {
        contador = 10;
        this.setFont(new Font("Arial", Font.PLAIN, 20));
        atualizarContador();
    }

    public void incrementarContador() {
        if (contador < 10) {
            contador++;
            atualizarContador();
        }
    }

    public void decrementarContador() {
        if (contador > 0) {
            contador--;
            atualizarContador();
        }
    }

    public void atualizarContador() {
        this.setText(String.valueOf(contador));
    }

    public int getValue(){
        return contador;
    }

    public void reset() {
        contador = 10;
        atualizarContador();
    }
}
