class Player
{
    private String name;
    private boolean hasRole;
    private int rank;
    private int location;
    private int role;
    private int balance;
    private int credits;
    private int rehearsalTokens;


    //Constructor
    public Player(int num, int i)
    {
        //Get the name of the player
        this.name = View.getName(i);
        this.hasRole = false;
        if(num > 6)
        {
            this.rank = 2;
        }
        else
        {
            this.rank = 1;
        }
        this.location = 0;
        this.role = -1;
        this.balance = 0;
        if(num == 5)
        {
            this.credits = 2;
        }
        else if(num == 6)
        {
            this.credits = 4;
        }
        else
        {
            this.credits = 0;
        }
        this.rehearsalTokens = 0;
    }

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
        this.location = location;
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

    //Reset the player's role
    public void resetRole()
    {
        this.hasRole = false;
        this.role = -1;
    }
}
