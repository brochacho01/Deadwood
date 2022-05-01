class Player
{
    private boolean hasRole;
    private int rank;
    private int location;
    private int role;
    private int balance;
    private int credits;
    private int rehearsalTokens;

    //Have the player take their turn
    public void takeTurn()
    {
        //Do stuff like move, act, etc...
        return;
    }
    //End the player's turn
    public void endTurn() 
    {
        return;
    }

    //Move the player
    public void move(boolean hasRole, int location)
    {
        return;
    }

    //Take an available role
    public void takeRole(boolean hasRole, int rank, int location)
    {
        return;
    }

    //Act in a given role
    public void act(boolean hasRole, int role, int rehearsalTokens, int location)
    {
        return;
    }

    //Rehearse for a role
    public void rehearse(int rehearsalTokens)
    {
        return;
    }

    //Upgrade a player's rank
    public void upgrade(int location, int balance, int credits, int rank)
    {
        return;
    }
}
