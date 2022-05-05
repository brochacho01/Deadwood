import java.util.*;

class Board
{
    private Room[] rooms;
    private Player[] players;
    private int activeSets;
    private int playerTurn;

    //Setup the board
    public void setBoard()
    {
        for(int i = 0; i < players.length; i++)
        {
            players[i].resetRole();
            players[i].move(false, 0);
        }
    }

    //randomize the order of players
    public void randomizePlayers()
    {
        List<Player> playersList = Arrays.asList(players);
		Collections.shuffle(playersList);
		playersList.toArray(players);
    }

    public Player[] getPlayers() 
    {
        return players;
    }

    public void setPlayers(int num)
    {
        this.players = new Player[num];
        for(int i = 0; i < num; i++)
        {
            players[i] = new Player(num, i+1);
        }
    }

    public void setRooms(Room[] rooms)
    {
        this.rooms = rooms;
        // -2 because office and trailer aren't sets
        this.activeSets = rooms.length - 2;
    }
}
