import java.io.*;
import java.util.*;

class View {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // Get the number of players that are going to play the game
    public static int getNumPlayers() {
        int numPlayers = 0;
        try {
            System.out.println("How many players are playing?");
            numPlayers = Integer.parseInt(br.readLine());
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (NumberFormatException nfe) {
        }
        if (numPlayers < 2 || numPlayers > 8) {
            System.out.println("Invalid number! You can play with 2-8 players.");
            numPlayers = getNumPlayers();
        }
        return numPlayers;
    }

    // Get the name of player i
    public static String getName(int i) {
        String name = "";
        try {
            System.out.println("Player " + i + ", what is your name?");
            name = br.readLine();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return name;
    }

    public static void doTurn(Player curPlayer, Board b) {
        System.out.println(curPlayer.getName() + ", it is your turn!");
        View.getAction(curPlayer, b);
    }

    public static void getAction(Player curPlayer, Board b) {
        String action = "";
        while (true) {
            System.out.print("You can: ");
            ArrayList<String> actions = curPlayer.getAvailableActions(b);
            System.out.println(actions.toString());
            System.out.println("\nWhat would you like to do?");
            try {
                action = br.readLine();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
            if (actions.stream().anyMatch(action::equalsIgnoreCase)) {
                switch (action.toUpperCase()) {
                    case "MOVE":
                        View.getMove(curPlayer, b);
                        break;
                    case "TAKE ROLE":
                        View.getRole(curPlayer, b);
                        break;
                    case "ACT":
                        curPlayer.act();
                        break;
                    case "REHEARSE":
                        curPlayer.rehearse();
                        break;
                    case "UPGRADE":
                        curPlayer.upgrade();
                        break;
                    case "END TURN":
                        curPlayer.endTurn();
                        break;
                    case "VIEW SETS":
                        b.printSets();
                        break;
                    case "VIEW MAP":
                        b.printMap();
                        break;
                }
                // break;
            } else {
                System.out.println("Not a valid action!");
            }
        }

    }

    // Prompt the player for their desired role, then upon successful input, calls
    // the takeRole method in player class
    private static void getRole(Player curPlayer, Board b) {
        int pLocation = curPlayer.getLocation();
        int playerRank = curPlayer.getRank();
        // Print all available roles and their details to player
        ((Set) Board.getRoom(pLocation)).printExtraRoles(playerRank);
        ((Set) Board.getRoom(pLocation)).getScene().printStarRoles(playerRank);
        // Get names of all available roles
        String[] extraRoles = ((Set) Board.getRoom(pLocation)).getExtraRoles(playerRank);
        String[] starRoles = ((Set) Board.getRoom(pLocation)).getScene().getStarRoles(playerRank);
        while (true) {
            // Tell names to player, get input
            System.out.println("Extra Roles you can take are: " + Arrays.toString(extraRoles));
            System.out.println("Starring Roles you can take are: " + Arrays.toString(starRoles));
            String desiredRole = "";
            try {
                desiredRole = br.readLine();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
            if(Arrays.asList(extraRoles).stream().anyMatch(desiredRole::equalsIgnoreCase)){
                // Update playerRole
                curPlayer.takeRole(desiredRole);
                break;
            } else if(Arrays.asList(starRoles).stream().anyMatch(desiredRole::equalsIgnoreCase)){
                // Update playerRole
                curPlayer.takeRole(desiredRole);
                break;
            } else {
                System.out.println("Not a valid move!");
            }
        }
    }

    // Prompt the player for their desired location to move to, then upon successful
    // input, calls the move method in player class
    private static void getMove(Player curPlayer, Board b) {
        int pLocation = curPlayer.getLocation();
        String[] neighbors = Board.getRoom(pLocation).getNeighbors();
        while (true) {
            System.out.println("Rooms you can move to are: " + Arrays.toString(neighbors));
            String desiredLocation = "";
            try {
                desiredLocation = br.readLine();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
            if (Arrays.asList(neighbors).stream().anyMatch(desiredLocation::equalsIgnoreCase)) {
                int location = b.matchNameToIndex(desiredLocation);
                if (location > -1) {
                    curPlayer.move(location);
                } else {
                    System.out.println("Not a valid move!");
                    break;
                }
                break;
            } else {
                System.out.println("Not a valid move!");
            }
        }
    }
}