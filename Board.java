class Board
{
    private Set[] sets;
    private Player[] players;
    private int activeSets;
    private int playerTurn;
    private int[][] map;

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

    //Verify a move was valid
    public boolean verifyMove(Player p, int[][] map)
    {
        return true;
    }

    //Verify a player has taken their turn correctly
    public boolean verifyTurn(Player p)
    {
        return true;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public Player[] getPlayers()
    {
        return players;
    }
}
