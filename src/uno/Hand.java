package uno;

import uno.card.Card;
import uno.enums.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Hand {
    private ArrayList<Card> cards;
    private String playerName;

    public Hand(String playerName) {
        this.playerName = playerName;
        cards = new ArrayList<Card>();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card play(Game game) {
        Card card = nextPlayableCard(game.getCurrentCard(), game.getCurrentColor());

        if (card != null) {
            cards.remove(card);

            /* if you have only one card left, say uno */
            if(cards.size() == 1) {
                game.sayUno(this);
            }
        }

        return card;
    }

    public boolean hasMoreCards() {
        return cards.size() > 0;
    }

    public Color pickColor() {
        Color color = getAvailableColor();

        if (color == null) {
            color = getRandomColor();
        }

        return color;
    }
    /**
     * This method will return the current player's hand as below
     * Tom: [RED:5, BLUE:+2, WILD:+4]
     * @return
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(playerName);

        builder.append(" [");

        for (int i=0; i< cards.size(); i++) {
            builder.append(cards.get(i));
            builder.append(", ");
        }

        builder.append("]");

        return builder.toString();
    }

    private Card nextPlayableCard(Card currentCard, Color currentColor) {
        /* if the current card is of type wild, then we need to play a card matched by the color */
        if (currentCard.getCardType().isWild()) {
            return getMatchingColorCard(currentColor);
        }

        return getMatchingValueCard(currentCard);
    }

    private Card getMatchingColorCard(Color currentColor) {
        Card playableCard = null;

        for (Card card : cards) {
            if (card.isMatch(currentColor)) {
                playableCard = card;
                break;
            }
        }

        return playableCard;
    }

    private Card getMatchingValueCard(Card currentCard) {
        Card playableCard = null;

        for (Card card : cards) {
            if (card.isMatch(currentCard)) {
                playableCard = card;
                break;
            }
        }

        return playableCard;
    }

    private Color getAvailableColor() {
        Color color = null;

        for (Card card : cards) {
            if (!card.isWild()) {
                color = card.getColor();
                break;
            }
        }

        return color;
    }

    private Color getRandomColor() {
        Random random = new Random();
        int colorIndex = random.nextInt(Color.values().length);

        return Color.values()[colorIndex];
    }
}
