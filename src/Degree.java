public enum Degree {
    BA(1),
    MA(2),
    DR(3),
    PROFESSOR(4);

    private int number;

    Degree(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public static Degree fromNumber(int number) {
        for (Degree d : Degree.values()) {
            if (d.getNumber() == number) {
                return d;
            }
        }
        return null;
    }
}
