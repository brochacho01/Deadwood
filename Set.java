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
    public boolean isFlipped;

    public void flip() {
        isFlipped = !isFlipped;
    }

    // uses the HashMap of players, calculates their payout and calls setter to
    // update player money/credits
    private void offCardPayout() {

    }

    private void removeSceneCard() {

    }

    public void setShotCounters(int shotsLeft) {

    }

    private void sceneWrap() {

    }

    public Set(String name, String[] neighbors, int shotsLeft) {
        super(name, neighbors);
        this.shotsLeft = shotsLeft;
        offCardRoles = new HashMap<Role, Integer>();
    }

    public void addRole(Role r) {
        offCardRoles.put(r, -1);
        return;
    }
}