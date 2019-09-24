package pife;

import java.util.ArrayList;
import java.util.Scanner;

public class Jogo {

    private final Scanner entrada = new Scanner(System.in);
    private final Baralho BARALHO;
    private Jogador[] jogadores;
    private ArrayList<Carta> cartaLixeira = new ArrayList<>();

    public Jogo() {
        BARALHO = new Baralho();
        BARALHO.embaralhar();
        BARALHO.mostrarBaralho();
    }

    public void mostrarBaralho() {
        BARALHO.mostrarBaralho();
    }

    public void iniciarJogo() {
        jogadores = new Jogador[2];

        for (int i = 0; i < jogadores.length; i++) {
            System.out.println("Jogador " + (i + 1) + ", informe seu nome:");
            jogadores[i] = new Jogador(entrada.next());
        }
    }

    public void distribuirCartas(int qtdCartas) {
        for (Jogador jogadore : jogadores) {
            jogadore.setCartas(BARALHO.distribuirCartas(qtdCartas));
        }
        BARALHO.montarBolo();

    }

    public Carta jogada(int index) {
        Carta cartaR = null;
        int op;
        do {
            System.out.println("\n---- OPÇÕES PARA JOGADA DE " + jogadores[index].getNome().toUpperCase() + " ----");
            if (cartaLixeira.isEmpty()) {
                System.out.println("1 - Comprar carta do Bolo \n2 - Pegar carta da Lixeira " + cartaLixeira + "");
            } else {
                System.out.println("1 - Comprar carta do Bolo \n2 - Pegar carta da Lixeira [" + cartaLixeira.get(cartaLixeira.size()-1)+ "]");
            }
            System.out.print("Informe a sua opção: ");
            op = entrada.nextInt();
            switch (op) {
                case 1:
                    System.out.println();
                    if (BARALHO.getIndexLatter() >= 0) {
                        cartaR = BARALHO.comprarCarta();
                        jogadores[index].mostrarCartas();
                        System.out.println("[Nº 10] - " + cartaR.toString() + " [Comprada]");
                    } else {
                        BARALHO.setBolo(cartaLixeira);
                    }
                    break;
                case 2:
                    System.out.println();
                    if (!cartaLixeira.isEmpty()) {
                        cartaR = cartaLixeira.get(cartaLixeira.size() - 1);
                        jogadores[index].mostrarCartas();
                        System.out.println("[Nº 10] - " + cartaR.toString() + " [Lixeira]");
                    } else {
                        cartaR = BARALHO.comprarCarta(); //Se for no inicio do jogo e o jogador 1 tentar pegar da lixeira
                        jogadores[index].mostrarCartas();
                        System.out.println("[Nº 10] - " + cartaR.toString() + "[Comprada]");
                    }
                    break;
                default:
                    System.out.println("\nOpção inválida, digite novamente.");
            }
        } while (cartaR == null);
        return cartaR;
    }

    public void descartar(int indexJ, Carta cartaJ) {
        int indexC = 0;

        do {
            System.out.print("\nInforme o [Nº ?] da carta para descartar: ");
            indexC = entrada.nextInt();
            if (indexC < 10) { //Se for uma carta do baralho
                cartaLixeira.add(jogadores[indexJ].getCarta(indexC - 1));
                jogadores[indexJ].setCartas(indexC - 1, cartaJ); //Passando o indexC - 1, porque o jogador tem um vetor de tamanho 9
            } else { //Se for a carta escolhida na jogada
                cartaLixeira.add(cartaJ);
            }
        } while (indexC > 10 || indexC < 1);//Valores dos indices dentro do limite        
        System.out.println("[x] A carta foi descartada.\n\n--------------- CARTAS DE " + jogadores[indexJ].getNome().toUpperCase() + " ---------------");
        jogadores[indexJ].mostrarCartas();
    }

    public boolean checkTrinca(int indexJ, int i) {
        if ((jogadores[indexJ].getCarta(i).getValorFace() == jogadores[indexJ].getCarta(i + 1).getValorFace()) && (jogadores[indexJ].getCarta(i+1).getValorFace() == jogadores[indexJ].getCarta(i + 2).getValorFace())) {
            jogadores[indexJ].removerCartas(i);
            return true;
        }
        return false;
    }

    public boolean checkSequecia(int indexJ, int i) {
       if (jogadores[indexJ].getCarta(i).getValorFace() == (jogadores[indexJ].getCarta(i+1).getValorFace() - 1) && jogadores[indexJ].getCarta(i).getValorFace() == (jogadores[indexJ].getCarta(i+2).getValorFace() - 2)) { // Se for uma sequecia de cartas, ex.: A, 2, 3
            if (jogadores[indexJ].getCarta(i).getNaipe().equals(jogadores[indexJ].getCarta(i + 1).getNaipe()) && jogadores[indexJ].getCarta(i+1).getNaipe().equals(jogadores[indexJ].getCarta(i + 2).getNaipe())){ //Se todos os Naipes forem iguais
                 jogadores[indexJ].removerCartas(i); 
                return true;
            }
        }
        return false;
    }
   
    public boolean checkJogo(int index) {
        int jogo = 0;
      for (int i = 0; i < jogadores[index].getSizeCarta() - 3; i++) {
            if (checkTrinca(index, i)) { //Vez do jogador e o indice atual do for()
                System.out.println(++jogo + "º Trinca foi encontrada e removida!");  
            } else {
                jogadores[index].ordenaArray(0); //Ordena as cartas por Naipe, antes de verificar a sequencia
                if (checkSequecia(index, i)) {
                    System.out.println(++jogo + "º Sequência foi encontrada e removida! ");
                }
                jogadores[index].ordenaArray(1);
            }
            if (jogo == 3 && i == 6) { //Se percorreu o vetor inteiro e encontrou 3 jogos diferentes
                return true;
            }
        }
        return false;
    }

    public void jogar() {
        int index = 0; //Vez da jogada
        boolean acabou = false;
        do {
            System.out.println("--------------- VEZ DE " + jogadores[index].getNome().toUpperCase() + " ---------------");
            jogadores[index].mostrarCartas();
            descartar(index, jogada(index)); //Realiza a jogada(retorna a carta da jogada) e depois chama o descartar
            System.out.print("\n[x] Verificar vitória (1 - SIM / 2 - NÃO): ");
            if (entrada.nextInt() == 1) {
                acabou = checkJogo(index);
                if (acabou) {
                    System.out.println("--------------- PARABÉNS " + jogadores[index].getNome().toUpperCase() + " VOCÊ VENCEU ---------------");
                    jogadores[index].mostrarCartas();
                } else {
                    System.out.println("\t\t AINDA NÃO :D\n");
                }
            }
            index = index == 0 ? 1 : 0; //Mudar a jogada dos jogadores
        } while (!acabou);
    }

    public static void main(String[] args) {

        Jogo execute = new Jogo();
        execute.iniciarJogo();
        execute.distribuirCartas(9);
        execute.jogar();
    }

}
