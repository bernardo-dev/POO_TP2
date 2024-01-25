package CampoMinado;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class Campo extends JPanel {
    private static Bloco[][] blocos;
    private static Set<Point> coordenadasMinas = new HashSet<>();
    private static JLabel labelContador;
    private static JButton botaoReiniciar;
    private static int contador;

    public Campo() {
        this(10, 10);
        labelContador = new JLabel();
        botaoReiniciar = new JButton("Reiniciar");
        botaoReiniciar.setSize(50, 50);
        contador = 0;
        labelContador.setText("Bandeiras: " + String.valueOf(contador));
        labelContador.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(labelContador);
        this.add(botaoReiniciar);
    
        botaoReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento){
                for (int i = 0; i < blocos.length; i++) {
                    for (int j = 0; j < blocos[i].length; j++) {
                        blocos[i][j].reset();
                    }
                }
    
                coordenadasMinas.clear();
    
                contador = 0;
                atualizarContador();
            }
        });
    }



    public Campo(int linhas, int colunas) {
        blocos = new Bloco[linhas][colunas];
        this.setLayout(new GridLayout(linhas, colunas));

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                blocos[i][j] = new Bloco(i, j);
                this.add(blocos[i][j]);
            }
        }
    }

    // Gera coordenadas unicas para as minas exceto onde o usuario clicou pela primeira vez
    public static void gerarMinas(int quantidade, int linha, int coluna) {
        while (coordenadasMinas.size() < quantidade) {
            int x = (int) (Math.random() * 10); // Intervalo de 0 a 9
            int y = (int) (Math.random() * 10);

            // Se as coordenadas geradas aleatoriamente sao iguais as coordenadas do bloco onde
            // o usuario clicou pela primeira vez gera outra coordenada
            if (x == linha && y == coluna) {
                continue;
            }

            Point coordenada = new Point();

            coordenada.setLocation(x, y);
            coordenadasMinas.add(coordenada);
        }

        for (Point p : coordenadasMinas) {
            blocos[p.x][p.y].setTipo(TipoBloco.MINA);
            for (int i = p.x - 1; i <= p.x + 1; i++) {
                for (int j = p.y - 1; j <= p.y + 1; j++) {
                    if ((i >= 0 && i < blocos.length) && (j >= 0 && j < blocos.length)) {
                        if ((i != p.x) || (j != p.y)) {
                            if (blocos[i][j].getTipo() != TipoBloco.MINA) {
                                blocos[i][j].setTipo(TipoBloco.NUMERICO);
                                blocos[i][j].incrementarNumero();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void revelarBlocos(int linha, int coluna) {
        if ((linha < 0) || (linha >= blocos.length)) {
            return;
        }

        if ((coluna < 0) || (coluna >= blocos.length)) {
            return;
        }

        if (blocos[linha][coluna].getEstado() == EstadoBloco.ABERTO) {
            return;
        }

        if (blocos[linha][coluna].getEstado() == EstadoBloco.MARCADO) {
            return;
        }

        if (blocos[linha][coluna].getTipo() == TipoBloco.NUMERICO) {
            blocos[linha][coluna].setEstado(EstadoBloco.ABERTO);
            blocos[linha][coluna].setEnabled(false);
            blocos[linha][coluna].setText(String.valueOf(blocos[linha][coluna].getNumero()));
            return;
        } else if (blocos[linha][coluna].getTipo() == TipoBloco.VAZIO) {
            blocos[linha][coluna].setEstado(EstadoBloco.ABERTO);
            blocos[linha][coluna].setEnabled(false);
        }

        revelarBlocos(linha - 1, coluna - 1);
        revelarBlocos(linha - 1, coluna);
        revelarBlocos(linha - 1, coluna + 1);

        revelarBlocos(linha, coluna - 1);
        revelarBlocos(linha, coluna + 1);

        revelarBlocos(linha + 1, coluna - 1);
        revelarBlocos(linha + 1, coluna);
        revelarBlocos(linha + 1, coluna + 1);
    }

    public static void revelarMinas() {

        for (Point p : coordenadasMinas) {
            Bloco bloco = blocos[p.x][p.y];
            bloco.setEstado(EstadoBloco.ABERTO);
            // bloco.setEnabled(false);
            bloco.setIconeMina();
        }
    }

    public static void incrementarContador(){
        contador++;
        atualizarContador();
    }

    public static void decrementarContador(){
        if (contador > 0) {  
            contador--;
            atualizarContador();
        }
    }

    public static void atualizarContador(){
        labelContador.setText("Bandeiras: " + contador);
    }

    public static JLabel getLabelContador() {
        return labelContador;
    }


    public static JButton getBotaoReiniciar() {
        return botaoReiniciar;
    }
    
    public static void setBotaoReiniciar(JButton botaoReiniciar) {
        Campo.botaoReiniciar = botaoReiniciar;
    }


 }
