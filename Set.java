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

    // Returns an array consisting of all the extra roles
    public HashMap<Role,Integer> getRoles(){
        return this.offCardRoles;
    }

    public void flip() {
        isFlipped = !isFlipped;
    }

    public boolean isFlipped()
    {
        return isFlipped;
    }

    // uses the HashMap of players, calculates their payout and calls setter to
    // update player money/credits
    private void offCardPayout() {

    }

    // Delete sceneCard once set wraps
    private void removeSceneCard() {
        this.scene = null;
    }

    // Decrement shot counters upon successful act
    public void decrementShotCounters() {
        this.shotsLeft--;
    }

    private void sceneWrap() {

    }

    public void printExtraRoles(int playerRank){
        System.out.println("Available Extra Roles are: ");
        for(Role key: offCardRoles.keySet()){
            if((playerRank >= key.rank) && (offCardRoles.get(key) == -1)){
                key.printRole();
                System.out.println("");
            }
        }
        scene.printStarRoles(playerRank);
    }

    public String[] getExtraRoles(int playerRank){
        ArrayList<String> extras = new ArrayList<String>();
        for(Role key: offCardRoles.keySet()){
            if((playerRank >= key.rank) && (offCardRoles.get(key) == -1)){
                extras.add(key.roleName);
            }
        }
        // Convert arraylist to array
        String[] extraRoles = new String[extras.size()];
        for(int i = 0; i < extraRoles.length; i++){
            extraRoles[i] = extras.get(i);
        }
        return extraRoles;
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

    public void updateRole(String desiredRole, int playerNumber){
        for(Role key: offCardRoles.keySet()){
            if(key.roleName.equals(desiredRole)){
                offCardRoles.put(key, offCardRoles.get(key) + 1);
            }
        }
    }

    public void setSceneCard(SceneCard card){
        this.scene = card;
    }

    public SceneCard getScene(){
        return this.scene;
    }

    // Removes sceneCard from the board, and removes all players from set
    public void reset(){
        this.scene = null;
        // For each role on the set, set its value back to -1 as all players are moved back to trailer
        for(int value : offCardRoles.values()){
            value = -1;
        }
    }
 
    public int getSceneBudget()
    {
        if (!this.isFlipped || this.scene == null)
        {
            return 0;
        }
        else
        {
            return this.scene.getBudget();
        }
    }

    //Print information about the set
    public void printSet() {
        if (!isFlipped)
        {
            System.out.println("Card has not been revealed");
        }
        else
        {
            System.out.println("There are " + shotsLeft + " shots remaining.");
            System.out.println("There are " + offCardRoles.keySet().size() + " roles on this set.");
            //print each role and the player that is on each one
            for(Role key: offCardRoles.keySet()){
                key.printRole();
                int ifP = offCardRoles.get(key);
                if(ifP == -1){
                    System.out.println("This role is empty");
                } else {
                    System.out.println(Board.getPlayer(ifP).getName() + " is on this role");
                }
            }
        }
    }
}