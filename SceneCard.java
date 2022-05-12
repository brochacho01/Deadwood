import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class SceneCard {
    private String name;
    private int sceneNumber;
    private String sceneDescription;
    private int budget;
    // This hashMap stores all the roles on the sceneCard as the keys, and the value
    // is either null or the number representing the player on the role, however
    // this is public unlike the one on Set as Set will need to access this
    private HashMap<Role, Integer> onCardRoles;

    public SceneCard(String name, int sceneNumber, String sceneDescription, int budget,
            HashMap<Role, Integer> onCardRoles) {
        this.name = name;
        this.sceneNumber = sceneNumber;
        this.sceneDescription = sceneDescription;
        this.budget = budget;
        this.onCardRoles = onCardRoles;
    }

    public int getBudget() {
        return this.budget;
    }

    // Print roles available to player trying to take a role
    public void printStarRoles(int playerRank) {
        System.out.println("\nAvailable Star Roles are: ");
        for (Role key : onCardRoles.keySet()) {
            if ((playerRank >= key.rank) && (onCardRoles.get(key) == -1)) {
                key.printRole();
                ;
                System.out.println("");
            }
        }
    }

    // Get roles available to player trying to take a role
    public String[] getStarRoles(int playerRank) {
        ArrayList<String> stars = new ArrayList<String>();
        for (Role key : onCardRoles.keySet()) {
            if ((playerRank >= key.rank) && (onCardRoles.get(key) == -1)) {
                stars.add(key.roleName);
            }
        }
        // Convert arraylist to array
        String[] starRoles = new String[stars.size()];
        for (int i = 0; i < starRoles.length; i++) {
            starRoles[i] = stars.get(i);
        }
        return starRoles;
    }

    public Role getRole(String roleName) {
        for (Role key : onCardRoles.keySet()) {
            if (roleName.equals(key.roleName.toLowerCase())) {
                return key;
            }
        }
        return null;
    }

    // Returns an array consisting of all the starring roles
    public HashMap<Role, Integer> getRoles() {
        return this.onCardRoles;
    }

    public void updateRole(String desiredRole, int playerNumber) {
        for (Role key : onCardRoles.keySet()) {
            if (key.roleName.toLowerCase().equals(desiredRole.toLowerCase())) {
                onCardRoles.put(key, playerNumber);
            }
        }
    }


    public boolean onCardpayout() {
        boolean isStars = false;
        // Get players on card
        Board b = Board.getBoard();
        // Get players from hash map
        ArrayList<Player> playersToPay = new ArrayList<Player>();
        ArrayList<int[]> orderedRoles = new ArrayList<int[]>();
        for (Role key : onCardRoles.keySet()) {
            int[] playerRanks = { onCardRoles.get(key), key.getRank() };
            orderedRoles.add(playerRanks);
        }
        Collections.sort(orderedRoles, Comparator.comparingInt(o -> o[1]));
        // reverse the array
        int[] t;
        for (int i = 0; i < orderedRoles.size() / 2; i++) {
            t = orderedRoles.get(i);
            orderedRoles.set(i, orderedRoles.get(orderedRoles.size() - i - 1));
            orderedRoles.set(orderedRoles.size() - i - 1, t);
        }
        for (int i = 0; i < orderedRoles.size(); i++) {
            if (orderedRoles.get(i)[0] == -1) {
                playersToPay.add(null);
            } else {
                playersToPay.add(b.getPlayer(orderedRoles.get(i)[0]));
            }
        }
        // calculate payout from payout roll
        int[] payouts = Dice.payoutRoll(budget);
        // pay them
        for (int i = 0; i < budget; i++) {
            Player toPay = playersToPay.get(i % onCardRoles.keySet().size());
            if (toPay != null) {
                int amount = payouts[i % onCardRoles.keySet().size()];
                playersToPay.get(i).pay(amount, 0);
                System.out.println(playersToPay.get(i).getName() + " has been paid $" + amount + " as a bonus for the scene wrapping!");
            }
        }
        //reset the players roles
        for(int i = 0; i < playersToPay.size(); i++)
        {
            Player toReset = playersToPay.get(i);
            if (toReset != null) {
                playersToPay.get(i).resetRole();
                isStars = true;
            }
        }
        return isStars;
    }
}
