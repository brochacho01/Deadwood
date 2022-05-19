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
    private boolean isTurn;
    private char color;

    // Constructor
    public Player(int num, int i) {
        // Get the name of the player
        this.name = Controller.getName(i);
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
        this.isTurn = false;
        this.color = Controller.getColor(i);
    }

    // resets the taken action of a player
    public void setTakenAction() {
        this.hasTakenAction = false;
    }

    // End the player's turn
    public void endTurn() {
        this.hasMoved = false;
        this.hasTakenAction = false;
        this.isTurn = false;
    }

    // resets the players turn logic
    public void startTurn() {
        this.isTurn = true;
    }

    // Sets a flag for the players' turn
    public boolean isTurn() {
        return this.isTurn;
    }

    // Move the player
    public void move(int location) {
        Board b = Board.getBoard();
        this.location = location;
        Room pRoom = b.getRoom(location);
        // Print the set where the player is
        System.out.println("\nYou are now in: " + pRoom.getName());
        // If they are in a set, flip it and print its information
        if (pRoom instanceof Set) {
            Set pSet = ((Set) pRoom);
            // If the scene card for the set isn't flipped yet, flip it
            if (!pSet.isFlipped()) {
                pSet.flip();
            }
            ((Set) pRoom).printSet();
        }
        // Update moved flag for turn logic
        this.hasMoved = true;
    }

    // Take an available role
    public void takeExtraRole(String role) {
        Board b = Board.getBoard();
        this.role = role.toLowerCase();
        // Update the set
        ((Set) b.getRoom(location)).updateRole(role, b.getPlayerIndex(this));
        // Update flags for turn logic
        hasRole = true;
        hasTakenAction = true;
        endTurn();
    }

    public void takeStarRole(String role) {
        Board b = Board.getBoard();
        this.role = role.toLowerCase();
        // Update the set
        ((Set) b.getRoom(location)).getScene().updateRole(role, b.getPlayerIndex(this));
        // update flags for turn logic
        hasRole = true;
        hasTakenAction = true;
        endTurn();
    }

    // Returns the name of the players's role
    public String getRole() {
        return this.role;
    }

    // Act in a given role
    public void act() {
        Board b = Board.getBoard();
        hasTakenAction = true;
        int rollResult = Dice.actRoll(rehearsalTokens);
        Role curRole = ((Set) b.getRoom(location)).getRole(role);
        // For success
        if (rollResult >= ((Set) b.getRoom(location)).getSceneBudget()) {
            System.out.println("Your roll plus practice chips resulted in a: " + rollResult + ", Success!");
            // Offcard bonuses
            if (curRole.getRoleType().equals("Extra")) {
                System.out.println("You have have been paid $1 and 1 credit.");
                this.balance++;
                this.credits++;
                ((Set) b.getRoom(location)).decrementShotCounters();
                // Oncard bonuses
            } else {
                System.out.println("You have have been paid $2.");
                this.balance += 2;
                ((Set) b.getRoom(location)).decrementShotCounters();
            }
            // Failure
        } else {
            System.out.println("Your roll plus practice chips resulted in a: " + rollResult + ", Fail!");
            if (curRole.getRoleName().equals("Extra")) {
                this.balance++;
            }
        }
        // End turn once acting is over
        this.endTurn();
        return;
    }

    // Rehearse for a role
    public void rehearse() {
        this.rehearsalTokens++;
        System.out.println("You now have " + rehearsalTokens + " rehearsal tokens.");
        hasTakenAction = true;
        this.endTurn();
        return;
    }

    // Upgrade a player's rank
    public void upgrade(int desiredRank, int dollars, int creds) {
        System.out.println("Congratulations, you are now rank " + desiredRank + "!");
        this.rank = desiredRank;
        this.balance -= dollars;
        this.credits -= creds;
        return;
    }

    // Reset the player's role
    public void resetRole() {
        this.rehearsalTokens = 0;
        this.hasRole = false;
        this.role = null;
    }

    // Update players's currency based on input
    public void pay(int dollars, int creds) {
        this.balance += dollars;
        this.credits += creds;
    }

    // Returns the name of the player
    public String getName() {
        return this.name;
    }

    // Moves the player back to the trailer
    public void resetLocation() {
        this.location = 0;
    }

    // returns the players location as an int for the index of the room in the
    // room[] for board
    public int getLocation() {
        return this.location;
    }

    // Returns the rank of the player as an int
    public int getRank() {
        return this.rank;
    }

    // Gets all the actions currently available to a player and returns it as an
    // arraylist of strings
    public ArrayList<String> getAvailableActions() {
        Board b = Board.getBoard();
        ArrayList<String> actions = new ArrayList<String>();
        if (!this.hasMoved && !this.hasRole) {
            actions.add("MOVE");
        }
        if (this.location > 1 && ((Set) b.getRoom(location)).getScene() != null && !this.hasRole
                && !((((Set) b.getRoom(this.location)).getExtraRoles(this.rank).length == 0)
                        && (((Set) b.getRoom(this.location)).getScene().getStarRoles(this.rank).length == 0))) {
            actions.add("TAKE ROLE");
        }
        if (this.hasRole && !hasTakenAction) {
            actions.add("ACT");
            if (this.rehearsalTokens < b.getMaxRehearsalTokens(this.location)) {
                actions.add("REHEARSE");
            }
        }
        if (this.location == 1 && rank < 6 && ((Office) b.getRoom(1)).canUpgrade(rank, balance, credits)) {
            actions.add("UPGRADE");
        }
        actions.add("Controller SET");
        actions.add("Controller PLAYER");
        actions.add("END TURN");
        actions.add("EXIT");
        return actions;
    }

    // Prints all the information about a player
    public void printPlayer() {
        Board b = Board.getBoard();
        System.out.println(this.getName().toUpperCase() + " is at " + b.getLocation(this.location) + ".");
        System.out.println("They are rank " + this.rank + ".");
        System.out.println("They have " + this.balance + " dollars, " + this.credits + " credits, and "
                + this.rehearsalTokens + " rehearsal tokens.");
        if (this.hasRole) {
            System.out.println("They are currently playing in role " + this.role.toUpperCase() + ".");
        } else {
            System.out.println("They currently are not playing in any roles.");
        }
    }

    // returns the balance (money) of current player
    public int getBalance() {
        return this.balance;
    }

    // returns the credits of current player
    public int getCredits() {
        return this.credits;
    }

    public char getColor(){
        return this.color;
    }
}
