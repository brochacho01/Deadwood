class Board
{
    private Set[] sets;
    private Player[] players;
    private int day;
    private int maxDays;
    private int activeSets;
    private int playerTurn;
    private int[][] map;

    //Setup the game
    public void setupGame()
    {
        day = 0;
        randomizePlayers(players);
        return;
    }

    //Setup the board
    public void setBoard()
    {
        return;
    }

    //randomize the order of players
    private void randomizePlayers(Player[] players)
    {
        return;
    }

    //Start a day
    public void startDay()
    {
        day = day + 1;
        if (day > maxDays)
        {
            endGame();
        }
        return;
    }

    //End a day
    public void endDay()
    {
        return;
    }

    //End the game
    public void endGame()
    {
        return;
    }

    //Verify a move was valid
    public boolean verifyMove(Player p, int[][] map, int location)
    {
        return true;
    }

    //Verify a player has taken their turn correctly
    public boolean verifyTurn(Player p)
    {
        return true;
    }
}
