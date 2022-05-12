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
            System.out.println("\nPlayer " + i + ", what is your name?");
            name = br.readLine();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return name;
    }

    public static void doTurn(Player curPlayer) {
        System.out.println("\n" + curPlayer.getName().toUpperCase() + ", it is your turn!");
        View.getAction(curPlayer);
    }

    public static void getAction(Player curPlayer) {
        Board b = Board.getBoard();
        String action = "";
        while (curPlayer.isTurn()) {
            System.out.print("\nYou can: ");
            ArrayList<String> actions = curPlayer.getAvailableActions();
            System.out.println(actions.toString().replace("[", "").replace("]", ""));
            System.out.println("\nWhat would you like to do?");
            try {
                action = br.readLine();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
            if (actions.stream().anyMatch(action::equalsIgnoreCase)) {
                switch (action.toUpperCase()) {
                    case "MOVE":
                        View.getMove(curPlayer);
                        break;
                    case "TAKE ROLE":
                        View.getRole(curPlayer);
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
                    case "VIEW SET":
                        View.getSet(curPlayer);
                        break;
                    case "VIEW PLAYER":
                        View.getPlayer(curPlayer);
                        break;
                    case "VIEW MAP":
                        b.printMap();
                        break;
                    case "EXIT":
                        System.exit(0);
                }
                // break;
            } else {
                System.out.println("Not a valid action!");
            }
        }
        System.out.println("\n----------------------------------------------");
        // Set the taken action flag back to false for when it's that players next turn
        curPlayer.setTakenAction();
    }

    private static void getPlayer(Player curPlayer)
    {
        Board b = Board.getBoard();
        String[] playerNames = b.getPlayerNames();
        System.out.println("\nWhich player would you like to view?");
        while (true)
        {
            // Tell names to player, get input
            System.out.print("You can view: ");
            System.out.println(Arrays.toString(playerNames).replace("[", "").replace("]", "").toUpperCase() + ", SELF");
            String desiredPlayer = "";
            try {
                desiredPlayer = br.readLine();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
            if(Arrays.asList(playerNames).stream().anyMatch(desiredPlayer::equalsIgnoreCase))
            {
                System.out.println("");
                b.getPlayer(b.matchPlayerToIndex(desiredPlayer)).printPlayer();
                break;
            }
            else if (desiredPlayer.toLowerCase().equals("self"))
            {
                System.out.println("");
                curPlayer.printPlayer();
                break;
            }
            else
            {
                System.out.println("Not a valid player!");
            }
        }
    }

    private static void getSet(Player curPlayer)
    {
        Board b = Board.getBoard();
        String[] roomNames = b.getRoomNames();
        int pLocation = curPlayer.getLocation();
        // Tell sets to player, get input
        System.out.println("\nWhat set would you like to view?");
        while (true)
        {
            // Tell names to player, get input
            System.out.print("You can view: ");
            System.out.println(Arrays.toString(roomNames).replace("[", "").replace("]", "").toUpperCase() + ", PLAYER" + ", ALL");
            String desiredSet = "";
            try {
                desiredSet = br.readLine();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
            if(Arrays.asList(roomNames).stream().anyMatch(desiredSet::equalsIgnoreCase))
            {
                System.out.println("");
                b.getRoom(b.matchNameToIndex(desiredSet)).printSet();
                break;
            }
            else if (desiredSet.toLowerCase().equals("player"))
            {
                System.out.println("\nYou are in " + b.getRoom(pLocation).getName());
                b.getRoom(pLocation).printSet();
                break;
            }
            else if (desiredSet.toLowerCase().equals("all"))
            {
                System.out.println("");
                Room[] rooms = b.getRooms();
                for (int i = 2; i < rooms.length; i++)
                {
                    rooms[i].printSet();
                }
                System.out.println("");
                break;
            }
            else
            {
                System.out.println("Not a valid room!");
            }
        }
    }

    // Prompt the player for their desired role, then upon successful input, calls
    // the takeRole method in player class
    private static void getRole(Player curPlayer) {
        Board b = Board.getBoard();
        int pLocation = curPlayer.getLocation();
        int playerRank = curPlayer.getRank();
        // Print all available roles and their details to player
        ((Set) b.getRoom(pLocation)).printExtraRoles(playerRank);
        // ((Set) Board.getRoom(pLocation)).getScene().printStarRoles(playerRank);
        // Get names of all available roles
        String[] extraRoles = ((Set) b.getRoom(pLocation)).getExtraRoles(playerRank);
        String[] starRoles = ((Set) b.getRoom(pLocation)).getScene().getStarRoles(playerRank);
        while (true) {
            // Tell names to player, get input
            System.out.println("\nExtra Roles you can take are: " + Arrays.toString(extraRoles).toUpperCase().replace("[", "").replace("]", ""));
            System.out.println("\nStarring Roles you can take are: " + Arrays.toString(starRoles).toUpperCase().replace("[", "").replace("]", ""));
            System.out.println("\nWhat Role would you like to take?");
            String desiredRole = "";
            try {
                desiredRole = br.readLine();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
            if(Arrays.asList(extraRoles).stream().anyMatch(desiredRole::equalsIgnoreCase)){
                // Update playerRole
                curPlayer.takeExtraRole(desiredRole);
                System.out.println("You are now playing as " + desiredRole.toUpperCase() + "!");
                break;
            } else if(Arrays.asList(starRoles).stream().anyMatch(desiredRole::equalsIgnoreCase)){
                // Update playerRole
                curPlayer.takeStarRole(desiredRole);
                System.out.println("\nYou are now playing as " + desiredRole.toUpperCase() + "!");
                break;
            } else {
                System.out.println("Not a valid move!");
            }
        }
    }

    // Prompt the player for their desired location to move to, then upon successful
    // input, calls the move method in player class
    private static void getMove(Player curPlayer) {
        Board b = Board.getBoard();
        int pLocation = curPlayer.getLocation();
        String[] neighbors = b.getRoom(pLocation).getNeighbors();
        while (true) {
            System.out.println("\nRooms you can move to are: " + Arrays.toString(neighbors).toUpperCase().replace("[", "").replace("]", ""));
            System.out.println("\nWhere would you like to move to?");
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
                    System.out.println("\nNot a valid move!");
                    break;
                }
                break;
            } else {
                System.out.println("\nNot a valid move!");
            }
        }
    }
}