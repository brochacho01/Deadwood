import java.util.ArrayList;
import java.util.HashMap;

import java.util.HashMap;

public class Set extends Room {
    private String name;
    private String[] neighbors;
    // Tracks how many shots are left on the set
    private int shotsLeft;
    // This hashMap stores all the roles on the sceneCard as the keys, and the value
    // is either null or the number representing the player on the role
    private HashMap<Role, Integer> offCardRoles;
    // Every set has a scene card
    private SceneCard scene;
    private boolean isFlipped;

    public Set(String name, String[] neighbors, int shotsLeft) {
        super(name, neighbors);
        this.shotsLeft = shotsLeft;
        offCardRoles = new HashMap<Role, Integer>();
    }

    // Returns an array consisting of all the extra roles
    public HashMap<Role, Integer> getRoles() {
        return this.offCardRoles;
    }

    public void flip() {
        isFlipped = !isFlipped;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    // uses the HashMap of players, calculates their payout and calls setter to
    // update player money/credits
    private void offCardPayout() {
        Board b = Board.getBoard();
        // Get players from hash map
        ArrayList<Player> playersToPay = new ArrayList<Player>();
        for (Role key : offCardRoles.keySet())
        {
            if (offCardRoles.get(key) != -1)
            {
                playersToPay.add(b.getPlayer(offCardRoles.get(key)));
            }
        }
        // pay them
        for(int i = 0; i < playersToPay.size(); i++)
        {
            int amount = ((Set) b.getRoom(playersToPay.get(i).getLocation())).getRole(playersToPay.get(i).getRole()).getRank();
            playersToPay.get(i).pay(amount, 0);
            System.out.println(playersToPay.get(i).getName() + " has been paid $" + amount + ".");
            //reset the players roles
            playersToPay.get(i).resetRole();
        }
    }

    // Delete sceneCard once set wraps
    private void removeSceneCard() {
        this.scene = null;
    }

    // Decrement shot counters upon successful act
    public void decrementShotCounters() {
        this.shotsLeft--;
        if (shotsLeft > 1) {
            System.out.println("There are " + shotsLeft + " shots left!");
        }
        if (shotsLeft == 1) {
            System.out.println("There is " + shotsLeft + " shot remaining!");
        }
        if (shotsLeft == 0) {
            sceneWrap();
        }
    }

    // First do payouts
    // Make sure to have check for endDay
    private void sceneWrap() {
        Board b = Board.getBoard();
        // offCardPayout
        offCardPayout();
        // onCardpayout
        scene.onCardpayout();
        removeSceneCard();
        b.decrementActiveSets();
    }

    // Print roles available to player trying to take a role
    public void printExtraRoles(int playerRank) {
        System.out.println("\nAvailable Extra Roles are: ");
        for (Role key : offCardRoles.keySet()) {
            if ((playerRank >= key.rank) && (offCardRoles.get(key) == -1)) {
                key.printRole();
                System.out.println("");
            }
        }
        scene.printStarRoles(playerRank);
    }

    // Get roles available to player trying to take a role
    public String[] getExtraRoles(int playerRank) {
        ArrayList<String> extras = new ArrayList<String>();
        for (Role key : offCardRoles.keySet()) {
            if ((playerRank >= key.rank) && (offCardRoles.get(key) == -1)) {
                extras.add(key.roleName);
            }
        }
        // Convert arraylist to array
        String[] extraRoles = new String[extras.size()];
        for (int i = 0; i < extraRoles.length; i++) {
            extraRoles[i] = extras.get(i);
        }
        return extraRoles;
    }

    public Role getRole(String roleName) {
        for (Role key : offCardRoles.keySet()) {
            if (roleName.equals(key.roleName.toLowerCase())) {
                return key;
            }
        }
        return scene.getRole(roleName);
    }

    public void addRole(Role r) {
        offCardRoles.put(r, -1);
        return;
    }

    public void updateRole(String desiredRole, int playerNumber) {
        for (Role key : offCardRoles.keySet()) {
            if (key.roleName.toLowerCase().equals(desiredRole)) {
                offCardRoles.put(key, playerNumber);
            }
        }
    }

    public void setSceneCard(SceneCard card) {
        this.scene = card;
    }

    public SceneCard getScene() {
        return this.scene;
    }

    // Removes sceneCard from the board, and removes all players from set
    public void reset() {
        this.scene = null;
        // For each role on the set, set its value back to -1 as all players are moved
        // back to trailer
        for (int value : offCardRoles.values()) {
            value = -1;
        }
    }

    public int getSceneBudget() {
        if (!this.isFlipped || this.scene == null) {
            return 0;
        } else {
            return this.scene.getBudget();
        }
    }

    // Print information about the set
    public void printSet() {
        if (this.scene == null)
        {
            System.out.println(this.getName() + " has " + shotsLeft + " shots remaining, so it has been removed from the board.\n");
            return;
        }
        Board b = Board.getBoard();
        System.out.println(this.getName() + " has " + shotsLeft + " shots remaining.");
        if (isFlipped)
        {
            System.out.println("\nThe budget of this set is: " + this.getSceneBudget());
        }
        System.out.println("\nThere are " + offCardRoles.keySet().size() + " extra roles on this set:");
        // print each extra role and the player that is on each one
        for (Role key : offCardRoles.keySet()) {
            key.printRole();
            int ifP = offCardRoles.get(key);
            if (ifP == -1) {
                System.out.println("This role is empty.");
            } else {
                System.out.println(b.getPlayer(ifP).getName().toUpperCase() + " is on this role.");
            }
        }
        if (!isFlipped) {
            System.out.println(this.getName() + "'s card has not been revealed yet.");
        } else {
            HashMap<Role, Integer> sceneRoles = scene.getRoles();
            System.out.println("\nThere are " + sceneRoles.keySet().size() + " star roles on this set:");
            // print each star role and the player that is on each one
            for (Role key : sceneRoles.keySet()) {
                key.printRole();
                int ifP = sceneRoles.get(key);
                if (ifP == -1) {
                    System.out.println("This role is empty.");
                } else {
                    System.out.println(b.getPlayer(ifP).getName().toUpperCase() + " is on this role.");
                }
            }
        }
        System.out.println("\n");
    }
}