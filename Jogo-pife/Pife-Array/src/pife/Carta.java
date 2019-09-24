package pife;

public class Carta implements Comparable {

    private final Face FACE; //Face do tipo enum
    private final String NAIPE;

    public Carta(Face face, String naipe) {
        this.FACE = face;
        this.NAIPE = naipe;
    }

    public String getNomeFace() {
        return FACE.getNomeFace();
    }

    public int getValorFace() {
        return FACE.getValorFace();
    }

    public String getNaipe() {
        return NAIPE;
    }

    @Override
    public String toString() {
        return FACE.getNomeFace() + " de " + NAIPE;
    }

    @Override
    public int compareTo(Object t) { //Método para ordenar utilizando o Arrays.sort(Object x[])
        Carta c = (Carta) t;
        return this.getValorFace() - c.getValorFace();
    }
}
