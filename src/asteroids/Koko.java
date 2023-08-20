package asteroids;

public enum Koko {
    X(1366), Y(768);
    private int i;

    private Koko(int i) {
        this.i = i;
    }

    public int I() {
        return i;
    }

}
