package pife;

public enum Face {
    A(1, "A"),
    DOIS(2, "2"),
    TRÊS(3, "3"),
    QUATRO(4, "4"),
    CINCO(5, "5"),
    SEIS(6, "6"),
    SETE(7, "7"),
    OITO(8, "8"),
    NOVE(9, "9"),
    DEZ(10, "10"),
    J(11, "J"),
    Q(12, "Q"),
    K(13, "K");

    private final int VALOR_FACE;

    private final String NOME_FACE;

    private Face(int valorDaFace, String nomeDaFace) {
        VALOR_FACE = valorDaFace;
        NOME_FACE = nomeDaFace;
    }

    public int getValorFace() {
        return VALOR_FACE;
    }

    public String getNomeFace() {
        return NOME_FACE;
    }

}
