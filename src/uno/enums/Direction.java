package uno.enums;

public enum Direction {
    FORWARDS(1),
    BACKWARDS(-1);

    private int directionValue;

    Direction(int directionValue) {
        this.directionValue = directionValue;
    }

    public int value() {
        return directionValue;
    }
}
