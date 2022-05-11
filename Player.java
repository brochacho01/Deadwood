import java.util.ArrayList;

class Player {
    private String name;
    private boolean hasMoved;
    private boolean hasTakenAction;
    private boolean hasRole;
    private int rank;
    private int location;
    private String role;
    private int balance;
    private int credits;
    private int rehearsalTokens;

    // Constructor
    public Player(int num, int i) {
        // Get the name of the player
        this.name = View.getName(i);
        // Set all attributes to default values, some depending on number of players.
        this.hasMoved = false;
        this.hasTakenAction = false;
        this.hasRole = false;
        if (num > 6) {
            this.rank = 2;
        } else {
            this.rank = 1;
        }
        this.location = 0;
        this.role = null;
        this.balance = 0;
        if (num == 5) {
            this.credits = 2;
        } else if (num == 6) {
            this.credits = 4;
        } else {
            this.credits = 0;
        }
        this.rehearsalTokens = 0;
    }

    // Have the player take their turn
    public void takeTurn() {
        // Do stuff like move, act, etc...
        return;
    }

    // End the player's turn
    public void endTurn() {
        return;
    }

    // Move the player
    public void move(int location) {
        this.location = location;
        Room pRoom = Board.getRoom(location);
        // Print the set where the player is
        System.out.println("You are now in: " + pRoom.getName());
        // If they are in a set, flip it and print its information
        if (pRoom instanceof Set) {
            Set pSet = ((Set) pRoom);
            if (!pSet.isFlipped()) {
                pSet.flip();
            }
            ((Set) pRoom).printSet();
        }
        this.hasMoved = true;
    }

    // Take an available role
    public void takeRole(String role) {
        this.role = role;
        // Update the set
        ((Set) Board.getRoom(location)).updateRole(role, Board.getPlayerIndex(this));
        hasRole = true;
        return;
    }

    // Act in a given role
    public void act() {
        // Acting ends the turn!
        int rollResult = Dice.actRoll(rehearsalTokens);
        Role curRole = ((Set) Board.getRoom(location)).getRole(role);
        // For success
        if (rollResult >= curRole.rank) {
            System.out.println("Your roll resulted in a: " + rollResult + ", Success!");
            // Offcard bonuses
            if (curRole.roleType.equals("Extra")) {
                this.balance++;
                this.credits++;
                ((Set) Board.getRoom(location)).decrementShotCounters();
                // Oncard bonuses
            } else {
                this.balance += 2;
                ((Set) Board.getRoom(location)).decrementShotCounters();
            }
            // Failure
        } else {
            System.out.println("Your roll resulted in a: " + rollResult + ", Fail!");
            if (curRole.roleType.equals("Extra")) {
                this.balance++;
            }
        }
        // End turn once acting is over
        this.endTurn();
        return;
    }

    // Rehearse for a role
    public void rehearse() {
        return;
    }

    // Upgrade a player's rank
    public void upgrade() {
        // TODO FULLY IMPLEMENT (Needed this for debugging)
        this.rank++;
        return;
    }

    // Reset the player's role
    public void resetRole() {
        this.rehearsalTokens = 0;
        this.hasRole = false;
        this.role = null;
        // add more actions that the player can do, such as view their own stats,
        // location, etc.
    }

    public String getName() {
        return this.name;
    }

    public void resetLocation() {
        this.location = 0;
    }

    public int getLocation() {
        return this.location;
    }

    public int getRank() {
        return this.rank;
    }

    public ArrayList<String> getAvailableActions(Board b) {
        ArrayList<String> actions = new ArrayList<String>();
        if (!this.hasMoved && !this.hasRole) {
            actions.add("MOVE");
        }
        if (this.location > 1 && !this.hasRole) {
            actions.add("TAKE ROLE");
        }
        if (this.hasRole) {
            actions.add("ACT");
            if (this.rehearsalTokens < b.getMaxRehearsalTokens(this.location)) {
                actions.add("REHEARSE");
            }
        }
        if (this.location == 1) {
            actions.add("UPGRADE");
        }
        actions.add("VIEW SETS");
        actions.add("END TURN");
        // add more actions that the player can do, such as view their own stats,
        // location, etc.
        return actions;
    }
}
