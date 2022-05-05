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
            //Reset the roles of players and move them to the trailers
            players[i].resetRole();
            players[i].move(false, 0);
            }
            // Need to also reset each set on the board
            for(int j = 0; j < rooms.length; j++){
                if(rooms[j] instanceof Set){
                    ((Set) rooms[j]).reset();
                }
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

    //Create the array of players
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

    public Room[] getRooms(){
        return this.rooms;
    }
}
