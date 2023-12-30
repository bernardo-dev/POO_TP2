package CampoMinado;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class Campo extends JPanel {
    private static Bloco[][] blocos;

    public Campo() {
        this(10, 10);
    }

    public Campo(int linhas, int colunas) {
        blocos = new Bloco[linhas][colunas];
        this.setLayout(new GridLayout(linhas, colunas));

        for (short i = 0; i < linhas; i++){
            for (short j = 0; j < colunas; j++){
                blocos[i][j] = new Bloco(i, j);
                this.add(blocos[i][j]);
            }
        }
    }

    // Gera coordenadas unicas para as minas exceto onde o usuario clicou pela primeira vez
    public static void gerarMinas(int quantidade, int linha, int coluna){
        Set<Point> coordenadasMinas = new HashSet<>();

        while(coordenadasMinas.size() < quantidade){
            int x = (int) (Math.random() * 10); // Intervalo de 0 a 9
            int y = (int) (Math.random() * 10);

            // Se as coordenadas geradas aleatoriamente sao iguais as coordenadas do bloco onde
            // o usuario clicou pela primeira vez gera outra coordenada
            if (x == linha && y == coluna){
                continue;
            }

            Point coordenada = new Point();

            //System.out.println("Gerou [" + x + ", " + y + "]");
            coordenada.setLocation(x, y);
            coordenadasMinas.add(coordenada);
        }

        for (Point p : coordenadasMinas){
            System.out.println("Mina [" + p.x + ", " + p.y + "]");
            blocos[p.x][p.y].setText("B");
        }
    }
}
