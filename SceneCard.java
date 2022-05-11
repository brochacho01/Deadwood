import java.util.ArrayList;
import java.util.HashMap;

public class SceneCard {
    public String name;
    public int sceneNumber;
    public String sceneDescription;
    public int budget;
    // This hashMap stores all the roles on the sceneCard as the keys, and the value
    // is either null or the number representing the player on the role, however
    // this is public unlike the one on Set as Set will need to access this
    public HashMap<Role, Integer> playersOnCard;

    public SceneCard(String name, int sceneNumber, String sceneDescription, int budget, HashMap<Role,Integer> playersOnCard){
        this.name = name;
        this.sceneNumber = sceneNumber;
        this.sceneDescription = sceneDescription;
        this.budget = budget;
        this.playersOnCard = playersOnCard;
    }

    public int getBudget()
    {
        return this.budget;
    }

    // Print roles available to player trying to take a role
    public void printStarRoles(int playerRank){
        System.out.println("Available Star Roles are: ");
        for(Role key: playersOnCard.keySet()){
            if((playerRank >= key.rank) && (playersOnCard.get(key) == -1)){
                key.printRole();;
                System.out.println("");
            }
        }
    }

    // Get roles available to player trying to take a role
    public String[] getStarRoles(int playerRank){
        ArrayList<String> stars = new ArrayList<String>();
        for(Role key: playersOnCard.keySet()){
            if((playerRank >= key.rank) && (playersOnCard.get(key) == -1)){
                stars.add(key.roleName);
            }
        }
        // Convert arraylist to array
        String[] starRoles = new String[stars.size()];
        for(int i = 0; i < starRoles.length; i++){
            starRoles[i] = stars.get(i);
        }
        return starRoles;
    }

    public Role getRole(String roleName){
        for(Role key: playersOnCard.keySet()){
            if(roleName.equals(key.roleName)){
                return key;
            }
        }
        return null;
    }


    // Returns an array consisting of all the starring roles
    public HashMap<Role,Integer> getRoles(){
        return this.playersOnCard;
    }

    public void updateRole(String desiredRole, int playerNumber){
        for(Role key: playersOnCard.keySet()){
            if(key.roleName.equals(desiredRole)){
                playersOnCard.put(key, playersOnCard.get(key) + 1);
            }
        }
    }

    public void onCardpayout()
    {
    //Get players on card
    //calculate payout from payout roll
    //pay them
    }
}
