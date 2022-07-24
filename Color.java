

public enum Color {

    WHITE,
    BLACK;

    @Override
    public String toString() {
        if (this == WHITE) {
            return "white";
        }
        return "black";
    }

}
