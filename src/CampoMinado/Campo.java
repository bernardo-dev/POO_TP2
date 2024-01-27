package CampoMinado;


import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Campo extends JPanel{
    private static Bloco[][] blocos;
    // Foi usado o HashSet para que cada coordenada gerada seja diferente
    private static final Set<Point> coordenadasMinas = new HashSet<>();
    private static JLabel labelContador;
    private static JButton botaoReiniciar;
    private static int contador;
    //private static JLabel labelVitoria;

    public Campo() {
        this(9, 9);
        labelContador = new JLabel();
        botaoReiniciar = new JButton();
        botaoReiniciar.setSize(50, 50);
        ImageIcon tuglife = new ImageIcon("src/Icones/tuglife.png");
        botaoReiniciar.setIcon(tuglife);
        contador = 10;
        labelContador.setText(String.valueOf(contador));
        labelContador.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(labelContador);
        this.add(botaoReiniciar);

        // Passa reiniciando cada bloco, o contador e excluindo as coordenadas de mina
        botaoReiniciar.addActionListener(evento -> {
            Bloco.setPrimeiroClick(false);
            for (Bloco[] bloco : blocos) {
                for (Bloco value : bloco) {
                    value.reset();
                }
            }

            coordenadasMinas.clear();

            contador = 10;
            atualizarContador();
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

    // Revela os blocos vazios recursivamente parando nos blocos numericos e nos blocos marcados
    public static void revelarBlocos(int linha, int coluna) {
        if ((linha < 0) || (linha >= blocos.length)) {
            return;
        }

        if ((coluna < 0) || (coluna >= blocos.length)) {
            return;
        }

        if (blocos[linha][coluna].getEstado() == EstadoBloco.ABERTO) {
           // verificarVitoria();
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
            // bloco.setEnabled(false);
            bloco.setIconeMina();
        }
    }

    public static void incrementarContador() {
        if (contador < 10) {
            contador++;
            atualizarContador();
        }
    }

    public static void decrementarContador() {
        if (contador > 0) {
            contador--;
            atualizarContador();
        }
    }

    public static void atualizarContador() {
        labelContador.setText(String.valueOf(contador));
    }

    public static JLabel getLabelContador() {
        return labelContador;
    }

    public static JButton getBotaoReiniciar() {
        return botaoReiniciar;
    }

    public static int getContador(){
        return contador;
    }


    // verificar se o usuario ganhou
    // quando ele nao clica em nenhum bloco que nao seja mina



    public static boolean verificarVitoria() {
        int contador = 0; // conta o numero de minas que foram marcadas ou abertas
        int contarBlocoAbero = 0;

        for (int i =0; i < blocos.length; i++){
            for (int j =0; j < blocos[i].length; j++){
               if (blocos[i][j].getEstado() == EstadoBloco.MARCADO || blocos[i][j].getEstado() == EstadoBloco.ABERTO){
                   if (blocos[i][j].getTipo() == TipoBloco.MINA){
                       contador++;
                       System.out.println("Contador: " + contador);
                   }
               }


                else if (blocos[i][j].getEstado() == EstadoBloco.ABERTO){ // se os blocos abertos nao forem minas
                    contarBlocoAbero++;
                    System.out.println("Contar bloco aberto: " + contarBlocoAbero);
                }
            }
        }

       int totalBlocos = blocos.length * blocos[0].length;
       int totalMinas = coordenadasMinas.size();
       
       // se o numero de bloco aberto for todos os blocos porem sem ser as minas e 
       // se o numero de minas marcadas ou abertas for igual ao numero de minas
       boolean vitoria = contarBlocoAbero == (totalBlocos - totalMinas) && contador == totalMinas;
       System.out.println("Verificando vitória: " + vitoria);
       return vitoria;
    }

    public static void mostrarVitoria(){
        System.out.println("Mostrando mensagem de vitória");
        JOptionPane.showMessageDialog(null, "Parabéns, você ganhou!", "Vitória", JOptionPane.INFORMATION_MESSAGE);
    }

    

}
