package uno;

import uno.card.Card;

import java.util.ArrayList;

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
}
