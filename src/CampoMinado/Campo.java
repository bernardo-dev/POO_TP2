package CampoMinado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class Campo extends JPanel {
    private static Bloco[][] blocos;
    // Foi usado o HashSet para que cada coordenada gerada seja diferente
    private static final Set<Point> coordenadasMinas = new HashSet<>();
    public static boolean jogoAcabou = false;
    //private static JLabel labelVitoria;
    private static LabelContador labelContador;
    private static BotaoReiniciar botaoReiniciar;

    public Campo() {
        this(9, 9);
    }

    public Campo(int linhas, int colunas) {
        blocos = new Bloco[linhas][colunas];
        this.setLayout(new GridLayout(linhas, colunas));

        // Inicializa cada bloco e adiciona o controle por teclado
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                blocos[i][j] = new Bloco(i, j);
                int finalI = i;
                int finalJ = j;
                blocos[i][j].addKeyListener(new KeyAdapter() {
                    final int linhaAtual = finalI;
                    final int colunaAtual = finalJ;

                    @Override
                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_UP:
                                System.out.println("Cima");
                                if (linhaAtual == 0) {
                                    blocos[linhaAtual + linhas - 1][colunaAtual].requestFocus();
                                } else {
                                    blocos[linhaAtual - 1][colunaAtual].requestFocus();
                                }
                                break;
                            case KeyEvent.VK_DOWN:
                                System.out.println("Baixo");
                                if (linhaAtual == linhas - 1) {
                                    blocos[0][colunaAtual].requestFocus();
                                } else {
                                    blocos[linhaAtual + 1][colunaAtual].requestFocus();
                                }
                                break;
                            case KeyEvent.VK_LEFT:
                                System.out.println("Esquerda");
                                if (colunaAtual == 0) {
                                    blocos[linhaAtual][colunas - 1].requestFocus();
                                } else {
                                    blocos[linhaAtual][colunaAtual - 1].requestFocus();
                                }
                                break;
                            case KeyEvent.VK_RIGHT:
                                System.out.println("Direita");
                                if (colunaAtual == colunas - 1) {
                                    blocos[linhaAtual][0].requestFocus();
                                } else {
                                    blocos[linhaAtual][colunaAtual + 1].requestFocus();
                                }
                                break;
                            case KeyEvent.VK_R:
                                botaoReiniciar.reiniciar();
                                break;
                        }
                    }
                });
                this.add(blocos[i][j]);
            }
        }

        labelContador = new LabelContador();
        botaoReiniciar = new BotaoReiniciar();
        botaoReiniciar.requestFocus(false);

        this.add(labelContador);
        this.add(botaoReiniciar);
    }

    // Gera coordenadas unicas para as minas exceto onde o usuario clicou pela primeira vez
    public static void gerarMinas(int quantidade, int linha, int coluna) {
        while (coordenadasMinas.size() < quantidade) {
            int x = (int) (Math.random() * 9); // Intervalo de 0 a 8
            int y = (int) (Math.random() * 9);

            // Se as coordenadas geradas aleatoriamente sao iguais as coordenadas do bloco onde
            // o usuario clicou pela primeira vez gera outra coordenada
            if (x == linha && y == coluna) {
                continue;
            }

            Point coordenada = new Point();

            coordenada.setLocation(x, y);
            coordenadasMinas.add(coordenada);
        }

        // Passa por cada mina incrementando o valor numerico dos blocos em volta da mina
        for (Point p : coordenadasMinas) {
            blocos[p.x][p.y].setTipo(TipoBloco.MINA);
            for (int i = Math.max(0, p.x - 1); i <= Math.min(blocos.length - 1, p.x + 1); i++) {
                for (int j = Math.max(0, p.y - 1); j <= Math.min(blocos[0].length - 1, p.y + 1); j++) {
                    if (blocos[i][j].getTipo() != TipoBloco.MINA) {
                        blocos[i][j].setTipo(TipoBloco.NUMERICO);
                        blocos[i][j].incrementarNumero();
                    }
                }
            }
        }
    }

    // Revela os blocos vazios recursivamente parando nos blocos numericos e nos blocos marcados
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
            // blocos[linha][coluna].setEnabled(false);
            blocos[linha][coluna].setBackground(Color.GRAY);
            blocos[linha][coluna].setText(String.valueOf(blocos[linha][coluna].getNumero()));
            return;
        } else if (blocos[linha][coluna].getTipo() == TipoBloco.VAZIO) {
            blocos[linha][coluna].setEstado(EstadoBloco.ABERTO);
            // blocos[linha][coluna].setEnabled(false);
            blocos[linha][coluna].setBackground(Color.GRAY);
        }

        // Chama recursivamente a funcao ao redor do bloco
        revelarBlocos(linha - 1, coluna - 1);
        revelarBlocos(linha - 1, coluna);
        revelarBlocos(linha - 1, coluna + 1);

        revelarBlocos(linha, coluna - 1);
        revelarBlocos(linha, coluna + 1);

        revelarBlocos(linha + 1, coluna - 1);
        revelarBlocos(linha + 1, coluna);
        revelarBlocos(linha + 1, coluna + 1);
    }

    // Revela a posicao de todas as minas no campo
    public static void revelarMinas() {

        for (Point p : coordenadasMinas) {
            Bloco bloco = blocos[p.x][p.y];
            bloco.setEstado(EstadoBloco.ABERTO);
            bloco.setIconeMina();
        }

        jogoAcabou = true;
    }

    public static LabelContador getLabelContador() {
        return labelContador;
    }

    public static BotaoReiniciar getBotaoReiniciar() {
        return botaoReiniciar;
    }

    public static LabelContador getContador() {
        return labelContador;
    }

    // verificar se o usuario ganhou
    // quando ele nao clica em nenhum bloco que nao seja mina
    public static boolean verificarVitoria() {
        int contador = 0; // conta o numero de minas que foram marcadas ou abertas
        int contarBlocoAbero = 0;

        // // Se as minas ainda não foram geradas, não verifique a vitória
        if (!Bloco.getPrimeiroClick()) {
            return false;
        }

        for (Bloco[] bloco : blocos) {
            for (Bloco value : bloco) {
                if (value.getEstado() == EstadoBloco.MARCADO) {
                    if (value.getTipo() == TipoBloco.MINA) {
                        contador++;
                    }
                } else if (value.getEstado() == EstadoBloco.ABERTO) { // se os blocos abertos nao forem minas
                    contarBlocoAbero++;
                }
            }
        }

        int totalBlocos = blocos.length * blocos[0].length;
        int totalMinas = coordenadasMinas.size();

        // se o numero de bloco aberto for todos os blocos porem sem ser as minas e
        // se o numero de minas marcadas ou abertas for igual ao numero de minas
        boolean vitoria = contarBlocoAbero == totalBlocos - totalMinas || contador == totalMinas;
        if (vitoria) {
            jogoAcabou = true;
        }
        return vitoria;
    }

    public static Bloco[][] getBlocos() {
        return blocos;
    }

    public static Set<Point> getCoordenadasMinas() {
        return coordenadasMinas;
    }


    public static boolean getJogoAcabou() {
        return jogoAcabou;
    }

    public static void setJogoAcabou(boolean jogoAcabou) {
        Campo.jogoAcabou = jogoAcabou;
    }
}