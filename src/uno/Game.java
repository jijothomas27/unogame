package uno;

import uno.card.Card;
import uno.enums.Color;
import uno.enums.Direction;
import uno.exceptions.EmptyDeckException;

import java.util.ArrayList;

public class Game {

    private static final int INITIAL_NUMBER_OF_CARDS_PER_HAND = 7;

    Deck deck;

    ArrayList<Hand> players;

    Direction currentDirection = Direction.FORWARDS;

    Hand currentPlayer;
    int currentPlayerPosition;

    Card currentCard;
    Color currentColor;

    public Game(ArrayList<String> playerNames) throws Exception {

        deck = new Deck();
        if (playerNames == null || playerNames.size() == 0) {
            throw new Exception("No players selected");
        }

        players = new ArrayList<Hand>();
        for (String playerName : playerNames) {
            players.add(new Hand(playerName));
        }

        /* for each player, draw 7 cards from deck */
        for (int distributionRound=1; distributionRound<= INITIAL_NUMBER_OF_CARDS_PER_HAND; distributionRound++) {
            for (Hand player : players) {
                Card card = deck.draw();
                player.addCard(card);
            }
        }

        currentCard = deck.draw();
        currentColor = Color.NONE;

        currentPlayerPosition = 0;
        currentPlayer = players.get(0);
    }

    public void play() {
        tellCurrentCard();

        try {
            while(true) {
                Card playedCard = currentPlayer.play(this);

                /* current player has no card to play. so draw one from deck */
                if (playedCard == null) {
                    Card card = deck.draw();
                    currentPlayer.addCard(card);

                    /* try again */
                    playedCard = currentPlayer.play(this);
                }

                if (playedCard != null) {
                    /* discard the previous card */
                    deck.discard(currentCard);
                    currentCard = playedCard;

                    if (currentCard.isWild()) {
                        currentColor = currentPlayer.pickColor();
                    } else {
                        currentColor = Color.NONE;
                    }

                    tellCurrentCard();
                }
            }
        } catch (EmptyDeckException emptyDeckException) {
            System.out.println("Deck is empty. Game is drawn!");
        } catch (Exception exception) {
            System.out.println("Something wrong happened");
            exception.printStackTrace();
        }

    }

    public Hand getNextPlayer() {
        int position = (currentPlayerPosition + currentDirection.value() * 1) % players.size();
        return players.get(position);
    }

    public Hand advancePlayer() {
                           /* forwards = 1, back = -1 */    /* if skippable, jump one player */
        int movePlayerBy = currentDirection.value()     *   (currentCard.getCardType().isSkippable() ? 2 : 1);
        int totalPlayers = players.size();

        currentPlayerPosition = (currentPlayerPosition + movePlayerBy) % totalPlayers;
        currentPlayer = players.get(currentPlayerPosition);

        return currentPlayer;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void sayUno(Hand player) {
        String message = String.format("%s said 'Uno!'", player.getPlayerName());
        System.out.println(message);
    }


    private void tellCurrentCard() {
        String message = String.format("Current card is: '%s'", currentCard);
        System.out.println(message);

        if(currentCard.getCardType().isWild()) {
            System.out.println(String.format("Current color is %s", currentColor));
        }
    }
}
