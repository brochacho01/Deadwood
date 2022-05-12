import java.util.Arrays;

public class Office extends Room {
    private int[][] upgrades;
    
    public Office(String name, String[] neighbors, int[][] upgrades) {
        super(name, neighbors);
        this.upgrades = upgrades;
    }

    public int[][] getUpgrades(){
        return this.upgrades;
    }

    public void printSet()
    {
        System.out.println("\nThis is the office, here you can upgrade your rank!");
        System.out.println("Upgrade costs:");
        System.out.println("Rank   Dollars   Credits");
        System.out.println(Arrays.deepToString(upgrades).replace("],", "\n").replace("[", "").replace(",", "      ").replace("]", ""));

    }
}
