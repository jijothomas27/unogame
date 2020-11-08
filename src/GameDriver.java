import uno.Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameDriver {

    private static BufferedReader input;

    public static void main(String[] args) {
        ArrayList<String> playerNames;
        Game game;

        input = getInputReader();
        displayMessage("Welcome to the Uno Game!");

        try {
            playerNames = getPlayerNames();
            game = new Game(playerNames);

            pressAnyKeyToContinue();

            game.play();

        } catch (Exception e) {
            displayError(e.getMessage());
        } finally {
            displayMessage("Game has ended!");
        }

    }

    private static BufferedReader getInputReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    private static void displayMessage(String message) {
        System.out.println(message);
    }

    private static void displayError(String message) {
        System.err.println(message);
    }

    private static ArrayList<String> getPlayerNames() throws Exception {
        int numberOfPlayers;
        ArrayList<String> playerNames = new ArrayList<>();

        displayMessage("How many players are playing?");
        numberOfPlayers = Integer.parseInt(input.readLine());

        if (numberOfPlayers <2) {
            throw new Exception("At least two players are required to play this game");
        }

        if(numberOfPlayers > 10) {
            throw new Exception("Too many players!");
        }

        displayMessage("Please enter player names");
        for(int i=1; i<= numberOfPlayers; i++) {
            String playerName = getPlayer(i);
            playerNames.add(playerName);
        }

        return playerNames;
    }

    private static String getPlayer(int playerNumber) throws Exception {
        String playerName;

        while(true) {
            displayMessage(String.format("Name of player %d: ", playerNumber));
            playerName = input.readLine();

            if(playerName != null && playerName.trim() != "") {
                return playerName;
            }

            displayError("Player name can't be empty. Please try again");
        }
    }

    private static void pressAnyKeyToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }
}
