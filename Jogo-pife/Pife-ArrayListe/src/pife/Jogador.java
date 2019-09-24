package pife;

import java.util.ArrayList;

import java.util.Collections;

public class Jogador {

    private final String NOME;
    private ArrayList<Carta> cartas = new ArrayList<>();

    public Jogador(String nome) {
        this.NOME = nome;
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas.addAll(cartas);
    }

    public void setCartas(int index, Carta novaCarta) { //Sobrecarga para trocar cartas
        cartas.remove(index);
        cartas.add(novaCarta);
    }

    public Carta getCarta(int index) {
        return cartas.get(index);
    }

    public String getNome() {
        return NOME;
    }

    public int getSizeCarta() {
        return cartas.size();
    }

    public void mostrarCartas() {
        int index = 0;
        ordenaArray(1);
        for (Carta carta : cartas) {
            System.out.println("[Nº " + ++index + "] - " + carta);
        }
    }

    public void removerCartas(int index) {
        for (int i = 0; i < 3; i++) {
            cartas.remove(index + i);
        }
    }

    public void ordenaArray(int x) {
        if (x == 1) {
            Collections.sort(cartas);
        } else {

            Collections.sort(cartas, (Carta c1, Carta c2) -> c1.getNaipe().compareTo(c2.getNaipe()) //TODO [x] estudar e entender como funciona esse método sort
            );
        }
    }

}
