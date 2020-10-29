package uno;

import uno.card.Card;

import java.util.ArrayList;

public class Game {

    private static final int INITIAL_NUMBER_OF_CARDS_PER_HAND = 7;

    public static void main(String[] args) {
        int numberOfPlayers = 2;

        Deck deck = new Deck();
        ArrayList<Hand> players = new ArrayList<Hand>();

        /* adding number of players */
        for (int player=1; player<=numberOfPlayers; player++) {
            players.add(new Hand("Player "+ player));
        }

        /* for each player, draw 7 cards from deck */
        for (int distributionRound=1; distributionRound<= INITIAL_NUMBER_OF_CARDS_PER_HAND; distributionRound++) {
            for (int playerId=0; playerId<players.size(); playerId++) {
                Card card = deck.draw();
                Hand player = players.get(playerId);

                player.addCard(card);
            }
        }

        System.out.println("Player Hands");
        for (int playerId=0; playerId<players.size(); playerId++) {
            Hand player = players.get(playerId);
            System.out.println(player);
        }
    }
}
