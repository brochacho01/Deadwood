class Board
{
    private Set[] sets;
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

    public void setSets(Set[] sets)
    {
        this.sets = sets;
        this.activeSets = sets.length;
    }
}
