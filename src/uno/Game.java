package uno;

import uno.card.Card;
import uno.enums.Color;
import uno.enums.Direction;
import uno.enums.Type;
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

        displayMessage("Game is set. Players are ready to play");
    }

    public void play() {

        int round = 1;
        try {
            while(true) {
                showHands(round);

                tellCurrentCard();
                Card playedCard = currentPlayer.play(this);

                /* current player has no card to play. so draw one from deck */
                if (playedCard == null) {
                    Card card = deck.draw();
                    currentPlayer.addCard(card);
                    displayMessage(String.format("Player: %s drew a card", currentPlayer.getPlayerName()));

                    /* try again */
                    playedCard = currentPlayer.play(this);
                }

                if (playedCard != null) {
                    /* discard the previous card */
                    deck.discard(currentCard);
                    currentCard = playedCard;

                    displayMessage(String.format("Player: %s played card: %s", currentPlayer.getPlayerName(), currentCard));
                    setCurrentColor();

                    if(!currentPlayer.hasMoreCards()) {
                        displayMessage(String.format("Player: %s has won the game!", currentPlayer.getPlayerName()));
                        return;
                    }

                    tellCurrentCard();

                    makeNextPlayerDrawCards();

                    currentDirection = currentCard.getGameDirection();
                    advancePlayer();
                } else {
                    displayMessage(String.format("Player: %s passed their turn", currentPlayer.getPlayerName()));
                    advanceFromPassedPlayer();
                }

                displayMessage(String.format("\n\n---- End of Round %d ----", round));
                round += 1;
            }
        } catch (EmptyDeckException emptyDeckException) {
            System.out.println("Deck is empty. Game is drawn!");
        } catch (Exception exception) {
            System.out.println("Something wrong happened: ");
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }

    }

    public Hand getNextPlayer() {
        int position = Math.abs(currentPlayerPosition + currentDirection.value() * 1) % players.size();
        return players.get(position);
    }

    /**
     * When a player passes his turn, the next player should not be determined by previous card.
     * @return
     */
    private Hand advanceFromPassedPlayer() {
        currentPlayerPosition = getNextPlayerPosition(1, currentDirection);
        currentPlayer = players.get(currentPlayerPosition);

        return currentPlayer;
    }

    private int getNextPlayerPosition(int advancyBy, Direction direction) {
        int movePlayerBy = direction.value() * advancyBy;
        int currentPosition = currentPlayerPosition;
        int totalPlayers = players.size();

        int newPosition = currentPosition + movePlayerBy;
        if (newPosition < 0 ) {
            currentPosition = totalPlayers + newPosition;

        } else {
            currentPosition = newPosition % totalPlayers;
        }

        return currentPosition;
    }

    public Hand advancePlayer() {

        int advanceBy = currentCard.getCardType().isSkippable() ? 2 : 1;
        currentPlayerPosition = getNextPlayerPosition(advanceBy, currentDirection);
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

    private void makeNextPlayerDrawCards() throws EmptyDeckException {
        Hand nextPlayer = getNextPlayer();
        Card currentCard = getCurrentCard();

        if (currentCard.shouldNextPlayerDraw()) {
            for(int i=0; i<currentCard.getCardType().numberOfCardsToDraw(); i++) {
                Card drawnCard = deck.draw();
                nextPlayer.addCard(drawnCard);

                displayMessage(String.format("Player: %s drew a card", nextPlayer.getPlayerName()));
            }
        }
    }

    private void setCurrentColor() {
        if (currentCard.isWild()) {
            currentColor = currentPlayer.pickColor();
        } else {
            Type cardType = currentCard.getCardType();

            if (cardType == Type.DRAW2 || cardType == Type.SKIP || cardType == Type.REVERSE) {
                currentColor = currentCard.getColor();
            } else {
                currentColor = Color.NONE;
            }
        }
    }

    private void displayMessage(String message) {
        System.out.println(message);
    }

    private void showHands(int round) {
        displayMessage(String.format("---- Round %d -----", round));
        for (Hand player : players) {
            displayMessage(player.toString());
        }
        displayMessage("\n\n");
    }
}
