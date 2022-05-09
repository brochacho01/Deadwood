import java.util.ArrayList;

class Player
{
    private String name;
    private boolean hasMoved;
    private boolean hasTakenAction;
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
        //Set all attributes to default values, some depending on number of players.
        this.hasMoved = false;
        this.hasTakenAction = false;
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
    public void move(int location)
    {
        this.location = location;
        Room pRoom = Board.getRoom(location);
        //Print the set where the player is
        System.out.println("You are now in: " + pRoom.getName());
        //If they are in a set, flip it and print its information
        if(pRoom instanceof Set)
        {
            Set pSet = ((Set) pRoom);
            if (!pSet.isFlipped())
            {
                pSet.flip();
            }
            ((Set) pRoom).printSet();
        }
        this.hasMoved = true;
    }

    //Take an available role
    public void takeRole()
    {
        return;
    }

    //Act in a given role
    public void act()
    {
        //Acting ends the turn!
        // TODO MAKE SURE TO END TURN
        return;
    }

    //Rehearse for a role
    public void rehearse()
    {
        return;
    }

    //Upgrade a player's rank
    public void upgrade()
    {
        return;
    }

    //Reset the player's role
    public void resetRole()
    {
        this.rehearsalTokens = 0;
        this.hasRole = false;
        this.role = -1;
        //add more actions that the player can do, such as view their own stats, location, etc.
    }

    public String getName()
    {
        return this.name;
    }

    public void resetLocation(){
        this.location = 0;
    }

    public int getLocation(){
        return this.location;
    }

    public ArrayList<String> getAvailableActions(Board b)
    {
        ArrayList<String> actions = new ArrayList<String>();
        if (!this.hasMoved && !this.hasRole)
        {
            actions.add("MOVE");
        }
        if (this.location > 1 && !this.hasRole)
        {
            actions.add("TAKE ROLE");
        }
        if (this.hasRole)
        {
            actions.add("ACT");
            if (this.rehearsalTokens < b.getMaxRehearsalTokens(this.location))
            {
                actions.add("REHEARSE");
            }
        }
        if (this.location == 1)
        {
            actions.add("UPGRADE");
        }
        actions.add("VIEW SETS");
        actions.add("END TURN");
        //add more actions that the player can do, such as view their own stats, location, etc.
        return actions;
    }
}
