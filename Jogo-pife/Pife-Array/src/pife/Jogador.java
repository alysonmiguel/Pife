package pife;

import java.util.Arrays;
import java.util.Comparator;

public class Jogador {

    private final String NOME;
    private Carta[] cartas = new Carta[9];

    public Jogador(String nome) {
        this.NOME = nome;
    }

    public void setCartas(Carta[] cartas) {
        this.cartas = cartas;
    }

    public void setCartas(int index, Carta novaCarta) { //Sobrecarga para trocar cartas
        cartas[index] = novaCarta;
    }

    public Carta getCarta(int index) {
        return cartas[index];
    }

    public String getNome() {
        return NOME;
    }

    public void mostrarCartas() {
        int index = 0;
        ordenaArray(1);
        for (Carta carta : cartas) {
            System.out.println("[Nº " + ++index + "] - " + carta);
        }
    }

    public void ordenaArray(int x) {
        if (x == 1) {
            Arrays.sort(cartas); //Estudar metódo sort simples
        } else {
            Arrays.sort(cartas, new Comparator<Carta>() { //TODO [x] estudar e entender como funciona esse método sort
                @Override
                public int compare(Carta c1, Carta c2) {
                    return c1.getNaipe().compareTo(c2.getNaipe());
                }
            });
        }
    }
}
