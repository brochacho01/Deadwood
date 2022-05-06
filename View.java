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
}