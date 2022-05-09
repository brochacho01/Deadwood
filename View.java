import java.io.*;
import java.util.*;

class View
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    //Get the number of players that are going to play the game
    public static int getNumPlayers()
    {
        int numPlayers = 0;
        try {
        System.out.println("How many players are playing?");
        numPlayers =  Integer.parseInt(br.readLine());
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (NumberFormatException nfe) {
        }
        if(numPlayers < 2 || numPlayers > 8)
        {
            System.out.println("Invalid number! You can play with 2-8 players.");
            numPlayers = getNumPlayers();
        }
        return numPlayers;
    }

    //Get the name of player i
    public static String getName(int i)
    {
        String name = "";
        try {
        System.out.println("Player " + i + ", what is your name?");
        name =  br.readLine();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        return name;
    }

    public static void startTurn(Player curPlayer)
    {
        System.out.println(curPlayer.getName() + ", it is your turn!");
    }

    public static void getAction(Player curPlayer, Board b)
    {
        String action = "";
        System.out.print("You can: ");
        ArrayList<String> actions = curPlayer.getAvailableActions(b);
        System.out.println(actions.toString());
        while(true){
            System.out.println("\nWhat would you like to do?");
            try {
                action =  br.readLine();
                } catch (IOException ioe) {
                    System.out.println(ioe);
                }
            if(actions.stream().anyMatch(action::equalsIgnoreCase))
            {
                switch (action.toUpperCase())
                {
                    case "MOVE": View.getMove(curPlayer,b);
                        break;
                    case "TAKE ROLE": View.getRole(curPlayer);
                        break;
                    case "ACT": curPlayer.act();
                        break;
                    case "REHEARSE": curPlayer.rehearse();
                        break;
                    case "UPGRADE": curPlayer.upgrade();
                        break;
                    case "END TURN": curPlayer.endTurn();
                        break;
                    case "VIEW SETS": b.printSets();
                        break;
                    case "VIEW MAP": b.printMap();
                        break;
                }
                break;
            }
            else
            {
                System.out.println("Not a valid action!");
            }
        }
        
    }

    private static void getRole(Player curPlayer) {
    }

    private static void getMove(Player curPlayer, Board b) {
        int pLocation = curPlayer.getLocation();
        String[] neighbors = b.getRoom(pLocation).getNeighbors();  
        System.out.println("Rooms you can move to are: " + Arrays.toString(neighbors));
        String desiredLocation = "";
        try {
            desiredLocation =  br.readLine();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        if(Arrays.asList(neighbors).stream().anyMatch(desiredLocation::equalsIgnoreCase)){
            curPlayer.move(b.matchNameToIndex(desiredLocation));
        } else {
            System.out.println("Not a valid move!");  
        }
    }
}