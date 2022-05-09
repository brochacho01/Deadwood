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
            players[i].move(0);
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
        //add more actions that the player can do, such as view their own stats, location, etc.

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

    public Player getPlayer(int i)
    {
        return players[i];
    }

    public String getLocation(int i)
    {
        return this.rooms[i].getName();
    }

    public Room getRoom(int location){
        return rooms[location];
    }

    public int getMaxRehearsalTokens(int location)
    {
        if(rooms[location] instanceof Set)
        {
            return ((Set) rooms[location]).getSceneBudget() - 1;
        }
        else
        {
            return -1;
        }
    }

    public int matchNameToIndex(String roomName){
        for(int i = 0; i < rooms.length; i++){
            if(rooms[i].getName().equals(roomName)){
                return i;
            }
        }
        return -1;
    }

    public int getActiveSets(){
        return this.activeSets;
    }

    public void printSets() {
    }

    public void printMap() {
    }
}
