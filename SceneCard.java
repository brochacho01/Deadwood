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
}
