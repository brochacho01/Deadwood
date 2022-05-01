import java.util.HashMap;

public class SceneCard {
    public int budget;

    // This hashMap stores all the roles on the sceneCard as the keys, and the value
    // is either null or the number representing the player on the role, however
    // this is public unlike the one on Set as Set will need to access this
    public HashMap<Role, Integer> playersOnCard;

    public boolean isFlipped;

    public void flip() {

    }

}
