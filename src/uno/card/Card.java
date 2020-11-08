package uno.card;

import uno.enums.Color;
import uno.enums.Direction;
import uno.enums.Type;

public class Card {
    private Color color;
    private Type cardType;
    private int value = -1;

    public Card(Color color, Type type, int value) {
        this.color = color;
        this.cardType = type;
        this.value = value;
    }
    public Card(Color color, Type type) {
        this(color, type, -1);
    }

    public Card(Color color, int value) {
        this(color, Type.VALUE, value);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        switch (color) {
            case RED:
                buffer.append("RED");
                break;
            case YELLOW:
                buffer.append("YELLOW");
                break;
            case GREEN:
                buffer.append("GREEN");
                break;
            case BLUE:
                buffer.append("BLUE");
                break;
            case NONE:
                buffer.append("WILD");
                break;
        }

        buffer.append(":");

        switch (cardType) {
            case VALUE:
                buffer.append(value);
                break;
            case SKIP:
                buffer.append("SKIP");
                break;
            case DRAW2:
                buffer.append("+2");
                break;
            case REVERSE:
                buffer.append("REVERSE");
                break;
            case WILD:
                buffer.append("");
                break;
            case WILD_DRAW4:
                buffer.append("+4");
                break;
        }

        return buffer.toString();
    }

    public Color getColor() {
        return color;
    }

    public Type getCardType() {
        return cardType;
    }

    public int getValue() {
        return value;
    }

    /**
     * Checks if current card be played against the given card. Here are the conditions
     * 1. If the current card is a Wild or Wild Draw 4, it can be played regardless of the given card.
     * 2. If both the cards have same color, the current card can be played
     * 3. If both the card have same type (draw2, skip) but not value card (1,2,..) the current card can be played
     * 4. If both the card have same value (1,2,,3) the current card can be played
     * @param card
     * @return
     */
    public boolean isMatch(Card card) {
        if (cardType == Type.WILD
                || cardType == Type.WILD_DRAW4
                || color == card.color
                || (cardType == card.getCardType() && cardType != Type.VALUE)
                || (value == card.getValue() && cardType == Type.VALUE && cardType == Type.VALUE)
        ) {
            return true;
        }

        return false;
    }

    public boolean isMatch(Color color) {
        return this.color == color || cardType.isWild();
    }

    public boolean isWild() {
        return cardType.isWild();
    }

    public boolean shouldNextPlayerDraw() {
        return cardType.isDrawable();
    }

    public Direction getGameDirection() {
        if (cardType == Type.REVERSE) {
            return Direction.BACKWARDS;
        }

        return Direction.FORWARDS;
    }
}
