package CampoMinado;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class Campo extends JPanel {
    private static Bloco[][] blocos;
    private static Set<Point> coordenadasMinas = new HashSet<>();

    public Campo() {
        this(10, 10);
    }

    public Campo(int linhas, int colunas) {
        blocos = new Bloco[linhas][colunas];
        this.setLayout(new GridLayout(linhas, colunas));

        for (int i = 0; i < linhas; i++){
            for (int j = 0; j < colunas; j++){
                blocos[i][j] = new Bloco(i, j);
                this.add(blocos[i][j]);
            }
        }
    }

    // Gera coordenadas unicas para as minas exceto onde o usuario clicou pela primeira vez
    public static void gerarMinas(int quantidade, int linha, int coluna){
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
            blocos[p.x][p.y].setTipo(TipoBloco.MINA);
            blocos[p.x][p.y].setText("B");
            blocos[p.x][p.y].setFont(new Font("Arial", Font.BOLD, 20));
            for (int i = p.x - 1; i <= p.x + 1; i++){
                for (int j = p.y - 1; j <= p.y + 1; j++){
                    if ((i >= 0 && i < blocos.length) && (j >= 0 && j < blocos.length)){
                        if((i != p.x) || (j != p.y)) {
                            if (blocos[i][j].getTipo() != TipoBloco.MINA){
                                blocos[i][j].setTipo(TipoBloco.NUMERICO);
                                blocos[i][j].incrementarNumero();
//                                System.out.println("Foi incrementado o bloco " + i + "," + j);
//                                blocos[i][j].setText(String.valueOf(blocos[i][j].getNumero()));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void revelarBlocos(int linha, int coluna) {
        for (int i = linha - 1; i <= linha + 1; i++) {
            for (int j = coluna - 1; j <= coluna + 1; j++) {
                if ((i >= 0 && i < blocos.length) && (j >= 0 && j < blocos.length)) {
                    if (i != linha || j != coluna) {
                        switch (blocos[i][j].getTipo()) {
                            case VAZIO:
                                break;

                        }
                        if (blocos[i][j].getTipo() == TipoBloco.NUMERICO) {
                            blocos[i][j].setEstado(EstadoBloco.ABERTO);
                            blocos[i][j].setEnabled(false);
                            blocos[i][j].setText(String.valueOf(blocos[i][j].getNumero()));
                        }

                        if (blocos[i][j].getTipo() == TipoBloco.VAZIO) {
                            blocos[i][j].setEstado(EstadoBloco.ABERTO);
                            blocos[i][j].setEnabled(false);
//                        revelarBlocos(i, j);
                        }
                    }
                }
            }
        }
    }
    public static void revelarMinas() {
        for (Point p : coordenadasMinas) {
            Bloco bloco = blocos[p.x][p.y];
            bloco.setEstado(EstadoBloco.ABERTO);
            bloco.setEnabled(false);
            bloco.setText("B");
            bloco.setFont(new Font("Arial", Font.BOLD, 20));
        }
    }

    
}
