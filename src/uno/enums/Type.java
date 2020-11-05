package uno.enums;

public enum Type {
    VALUE(false, false, 0),

    SKIP(true, false, 0),

    REVERSE(false, false, 0),

    WILD(false, true, 0),

    DRAW2(true, false, 2),

    WILD_DRAW4(true, true,4);

    private boolean skippable;
    private boolean wild;
    private int drawableCardCount;

    Type(boolean skippable, boolean wild, int drawableCardCount) {
        this.skippable = skippable;
        this.wild = wild;
        this.drawableCardCount = drawableCardCount;
    }

    public boolean isSkippable() {
        return this.skippable;
    }

    public boolean isWild() {
        return wild;
    }

    public int getDrawableCardCount() {
        return drawableCardCount;
    }
}
