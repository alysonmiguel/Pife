package pife;

import java.util.ArrayList;
import java.util.Random;

public class Baralho {

    private final ArrayList<Carta> CARTAS = new ArrayList<>();
    private ArrayList<Carta> bolo = new ArrayList<>();
    private final Random ALEATORIO;
    private int indexLatter;// Indice da ultima carta do 
    private int contador;

    public Baralho() {
        ALEATORIO = new Random();
        indexLatter = 33;
        String[] naipe = {"copas", "espadas", "ouros", "paus"};
        for (String n : naipe) {
            for (Face f : Face.values()) {
                CARTAS.add(new Carta(f, n));
            }
        }
    }

    public void embaralhar() {
        int num, num2;
        Carta temp;
        for (Carta cartas : CARTAS) {
            num = ALEATORIO.nextInt(CARTAS.size());
            num2 = ALEATORIO.nextInt(CARTAS.size());
            temp = CARTAS.get(num);
            CARTAS.set(num, CARTAS.get(num2));
            CARTAS.set(num2, temp);
        }
    }

    public ArrayList<Carta> distribuirCartas(int qtdCartas) {
        ArrayList<Carta> cartasJogador = new ArrayList<>();

        for (int i = 0; i < qtdCartas; i++) {
            cartasJogador.add(CARTAS.get(contador++));
        }
        return cartasJogador;
    }

    public void montarBolo() {
        for (int i = 0; i < 34; i++) {
            bolo.add(CARTAS.get(contador++));
        }
    }

    public void mostrarBaralho() {
        for (Carta carta : CARTAS) {
            System.out.println(carta);
        }
    }

    public Carta comprarCarta() {;
        Carta cartaComprada = bolo.get(indexLatter);
        bolo.remove(indexLatter);
        indexLatter--;
        return cartaComprada;
    }
   
    public int getIndexLatter() {
        return indexLatter;
    }

    public void setBolo(ArrayList<Carta> bolo) {
        this.bolo = bolo;
        indexLatter = 0;
    }

}
