package uno;

import uno.card.Card;
import uno.enums.Color;
import uno.enums.Type;
import uno.exceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    /* Number of cards of same value  in each color */
    private final int NUMBER_OF_SAME_VALUE_CARDS = 2;

    /* Number of special (skip, reverse, +2) value  in each color */
    private final int NUMBER_OF_SPECIAL_CARDS = 2;

    /* Number of total wild cards */
    private final int NUMBER_OF_WILD_CARDS = 4;

    /* Number of total wild +4 cards */
    private final int NUMBER_OF_WILD_DRAW4_CARDS = 4;

    private Random random;

    private ArrayList<Card> cards = new ArrayList<Card>();

    private ArrayList<Card> rejected = new ArrayList<Card>();

    public Deck() {
        cards = new ArrayList<Card>();
        random = new Random();

        initializeDeck();
        shuffle();
    }

    /**
     * Returns a card from the top of the deck
     * @return Card
     */
    public Card draw() throws EmptyDeckException {

        if (cards.size() == 0) {
            throw new EmptyDeckException();
        }

        return cards.remove(0);
    }

    public Deck discard(Card card) {
        rejected.add(card);
        return this;
    }

    private void initializeDeck() {

        /* add numbered cards for each color */
        for (int value=0; value<=9; value++) {
            for (int repetition=1; repetition<=NUMBER_OF_SAME_VALUE_CARDS; repetition++) {
                cards.add(new Card(Color.RED, value));
                cards.add(new Card(Color.YELLOW, value));
                cards.add(new Card(Color.BLUE, value));
                cards.add(new Card(Color.GREEN, value));
            }
        }

        /* add special cards for each color */
        for (int repetition=1; repetition<=NUMBER_OF_SPECIAL_CARDS; repetition++) {
            cards.add(new Card(Color.RED, Type.SKIP));
            cards.add(new Card(Color.RED, Type.REVERSE));
            cards.add(new Card(Color.RED, Type.DRAW2));

            cards.add(new Card(Color.YELLOW, Type.SKIP));
            cards.add(new Card(Color.YELLOW, Type.REVERSE));
            cards.add(new Card(Color.YELLOW, Type.DRAW2));

            cards.add(new Card(Color.GREEN, Type.SKIP));
            cards.add(new Card(Color.GREEN, Type.REVERSE));
            cards.add(new Card(Color.GREEN, Type.DRAW2));

            cards.add(new Card(Color.BLUE, Type.SKIP));
            cards.add(new Card(Color.BLUE, Type.REVERSE));
            cards.add(new Card(Color.BLUE, Type.DRAW2));
        }

        /* adding wild cards */
        for (int repetition=1; repetition<=NUMBER_OF_WILD_CARDS; repetition++) {
            cards.add(new Card(Color.NONE, Type.WILD));
        }

        /* adding wild draw 4 */
        for (int repetition=1; repetition<=NUMBER_OF_WILD_CARDS; repetition++) {
            cards.add(new Card(Color.NONE, Type.WILD_DRAW4));
        }
    }

    private void shuffle() {
        Collections.shuffle(cards, random);
    }
}
