package pife;

import java.util.Scanner;

public class Jogo {

    private static final Scanner entrada = new Scanner(System.in);
    private final Baralho baralho;
    private Jogador[] jogadores;
    private Carta cartaLixeira;

    public Jogo() {
        baralho = new Baralho();
    }

    public void mostrarBaralho() {
        baralho.mostrarBaralho();
    }

    public void iniciarJogo() {
        cartaLixeira = null;
        baralho.embaralhar();
        jogadores = new Jogador[2];

        for (int i = 0; i < jogadores.length; i++) {
            System.out.println("Jogador " + (i + 1) + ", informe seu nome:");
            jogadores[i] = new Jogador(entrada.next());
        }
    }

    public void distribuirCartas(int qtdCartas) {
        for (Jogador jogadore : jogadores) {
            jogadore.setCartas(baralho.distribuirCartas(qtdCartas));
        }
        baralho.montarBolo();

    }

    public boolean checkTrinca(Carta c1, Carta c2, Carta c3, Carta extra) {
        return (c1.getValorFace() == c2.getValorFace() && c2.getValorFace() == c3.getValorFace() && !c1.getNaipe().equals(extra.getNaipe())); //Retorna um boolean - true/false
    }

    public boolean checkSequecia(Carta c1, Carta c2, Carta c3, Carta extra) {
        if (c2.getValorFace() == (c1.getValorFace() + 1) && c3.getValorFace() == (c1.getValorFace() + 2)) { // Se for uma sequecia de cartas, ex.: A de paus, 2 de paus, 3 de paus
            if (c1.getNaipe().equals(c2.getNaipe()) && c2.getNaipe().equals(c3.getNaipe()) && !(c1.getNaipe().equals(extra.getNaipe()) || !((c1.getValorFace() + 3) == extra.getValorFace()))) { //Se todos os Naipes forem iguais
                return true;
            }
        }
        return false;
    }

    public boolean checkJogo(int index) {
        int jogo = 0;
        Carta cartaExtra = jogadores[index].getCarta(8);

//        Até 6 porque nós iremos passar por parâmetros 3 cartas seguidas - carta 
//        na posição i, i+1 e i+2 Não podemos ultrapassar o i > 8
        for (int i = 0; i < 7; i++) {
            if (i < 6) {
                cartaExtra = jogadores[index].getCarta(i + 3);
            }
            //Chama o método checkTrinca() dentro do if, se o retorno for true ele executa a instrução                     
            if (checkTrinca(jogadores[index].getCarta(i), jogadores[index].getCarta(i + 1), jogadores[index].getCarta(i + 2), cartaExtra)) {
                System.out.println("["+ ++jogo + "º Trinca]");
                System.out.println(jogadores[index].getCarta(i) + "\n" + jogadores[index].getCarta(i + 1) + "\n" + jogadores[index].getCarta(i + 2) + "\n");
            } else {
                jogadores[index].ordenaArray(0); //Ordena as cartas por Naipe, antes de verificar a sequencia   
                if (checkSequecia(jogadores[index].getCarta(i), jogadores[index].getCarta(i + 1), jogadores[index].getCarta(i + 2), cartaExtra)) {
                    System.out.println("["+ ++jogo + "º Sequência]");
                    System.out.println(jogadores[index].getCarta(i) + "\n" + jogadores[index].getCarta(i + 1) + "\n" + jogadores[index].getCarta(i + 2) + "\n");
                }
                jogadores[index].ordenaArray(1);
            }
            if (jogo == 3 && i == 6) { //Se percorreu o vetor inteiro e encontrou 3 jogos diferentes
                return true;
            }
        }
        return false;
    }

    public void descartar(int indexJ, Carta cartaJ) {
        int indexC;

        do {
            System.out.print("\nInforme o [Nº ?] da carta para descartar: ");
            indexC = entrada.nextInt();
            if (indexC < 10) { //Se for uma carta do baralho
                cartaLixeira = jogadores[indexJ].getCarta(indexC - 1);
                jogadores[indexJ].setCartas(indexC - 1, cartaJ); //Passando o indexC - 1, porque o jogador tem um vetor de tamanho 9
            } else { //Se for a carta escolhida na jogada
                cartaLixeira = cartaJ;
            }
        } while (indexC > 10 || indexC < 1);//Valores dos indices dentro do limite        
        System.out.println("[x] A carta [" + cartaLixeira + "] foi descartada.\n\n--------------- CARTAS DE " + jogadores[indexJ].getNome().toUpperCase() + " ---------------");
        jogadores[indexJ].mostrarCartas();
    }

    public Carta jogada(int index) {
        Carta cartaR = null;
        int op;
        do {
            System.out.println("\n---- OPÇÕES PARA JOGADA DE " + jogadores[index].getNome().toUpperCase() + " ----");
            System.out.println("1 - Comprar carta do Bolo \n2 - Pegar carta da Lixeira [" + cartaLixeira + "]");
            System.out.print("Informe a sua opção: ");
            op = entrada.nextInt();
            switch (op) {
                case 1:
                    System.out.println();
                    if (baralho.getIndexLastLatter() < 32) {
                        cartaR = baralho.comprarCarta();
                        jogadores[index].mostrarCartas();
                        System.out.println("[Nº 10] - " + cartaR.toString() + " [Comprada]");
                    } else {
                        System.out.println("\nNão há mais cartas para comprar do bolo");
                    }
                    break;
                case 2:
                    System.out.println();
                    if (cartaLixeira != null) {
                        cartaR = cartaLixeira;
                        jogadores[index].mostrarCartas();
                        System.out.println("[Nº 10] - " + cartaR.toString() + " [Lixeira]");
                    } else {
                        cartaR = baralho.comprarCarta(); //Se for no inicio do jogo e o jogador 1 tentar pegar da lixeira
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

    public void jogar() {
        int index = 0; //Vez da jogada
        boolean acabou = false;

        do {
            System.out.println("--------------- VEZ DE " + jogadores[index].getNome().toUpperCase() + " ---------------");
            jogadores[index].mostrarCartas();
            descartar(index, jogada(index)); //Realiza a jogada(retorna a carta da jogada) e depois chama o descartar
            System.out.print("\n[x] Verificar vitória (1 - SIM / (2+) - NÃO): ");
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

        Jogo execute;
        int op;

        do {
            System.out.println("\n\n--------------- MENU PRINCIPAL ---------------");
            System.out.println("1 - INICIAR JOGO\n2 - EXIBIR CARTAS DO BARALHO\n3 - SAIR");
            System.out.println("----------------------------------------------");
            System.out.print("INFORME A OPÇÃO: ");
            op = Jogo.entrada.nextInt();
            switch (op) {
                case 1:
                    execute = new Jogo();

                    execute.iniciarJogo();
                    execute.distribuirCartas(9);
                    execute.jogar();
                    break;
                case 2:
                    execute = new Jogo();
                    System.out.println("--------------- CARTAS DO BARALHO ----------------");
                    execute.mostrarBaralho();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("\nOpção inválida, digite novamente.");
            }
        } while (op != 3);
    }

}
