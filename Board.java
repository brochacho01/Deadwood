class Board
{
    private Room[] rooms;
    private Player[] players;
    private int activeSets;
    private int playerTurn;

    //Setup the board
    public void setBoard()
    {
        return;
    }

    //randomize the order of players
    public void randomizePlayers()
    {
        return;
    }

    public Player[] getPlayers() 
    {
        return players;
    }

    public void setRooms(Room[] rooms)
    {
        this.rooms = rooms;
        // -2 because office and trailer aren't sets
        this.activeSets = rooms.length - 2;
    }
}
