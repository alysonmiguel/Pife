package pife;

import java.util.Random;

public class Baralho {

    private final Carta[] CARTAS;
    private final Carta[] BOLO;
    private final Random ALEATORIO;
    private int indexLatter;// Indice da ultima carta do bolo
    private int contador;

    public Baralho() {
        ALEATORIO = new Random();
        CARTAS = new Carta[52]; //Total de cartas do Baralho
        BOLO = new Carta[32]; //Tamanho do Array = Total de cartas do baralho - qtdCartas de jogador * 2
        String[] naipe = {"copas", "espadas", "ouros", "paus"};
        indexLatter = 0;
        int cont = 0;
        for (String n : naipe) {
            for (Face f : Face.values()) {
                CARTAS[cont++] = new Carta(f, n);
            }
        }
    }

    public void embaralhar() {
        int num, num2;
        Carta temp;
        for (int i = 0; i < CARTAS.length; i++) {
            num = ALEATORIO.nextInt(CARTAS.length);
            num2 = ALEATORIO.nextInt(CARTAS.length);
            temp = CARTAS[num];
            CARTAS[num] = CARTAS[num2];
            CARTAS[num2] = temp;
        }
    }

    public Carta[] distribuirCartas(int qtdCartas) {
        Carta[] cartasJogador = new Carta[qtdCartas];

        for (int i = 0; i < qtdCartas; i++) {
            cartasJogador[i] = CARTAS[contador];
            contador++;
        }
        return cartasJogador;
    }

    public void montarBolo() {
        for (int i = 0; i < BOLO.length; i++) {
            BOLO[i] = CARTAS[contador];
            contador++;
        }
    }

    public void mostrarBaralho() {
        for (Carta carta : CARTAS) {
            System.out.println(carta.toString());
        }
    }

    public Carta comprarCarta() {
        Carta cartaComprada = BOLO[indexLatter++];
        return cartaComprada;
    }

    public int getIndexLastLatter() {
        return indexLatter;
    }

}
