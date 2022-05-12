import java.util.ArrayList;
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

    public boolean canUpgrade(int rank, int dollars, int credits)
    {
        return upgrades[rank-1][1] <= dollars || upgrades[rank-1][2] <= credits;
    }

    public ArrayList<String> getAvailableUpgrades(int rank, int dollars, int credits)
    {
        ArrayList<String> availableUpgrades = new ArrayList<String>();
        for (int i = rank-1; i < 6; i++)
        {
            if (upgrades[i][1] <= dollars || upgrades[i][2] <= credits)
            {
                availableUpgrades.add(String.valueOf(upgrades[i][0]));
            }
            else
            {
                break;
            }
        }
        return availableUpgrades;
    }

    public ArrayList<String> getUpgradeTypes(int rank, int dollars, int credits)
    {
        ArrayList<String> upgradeTypes = new ArrayList<String>();
        if (upgrades[rank-2][1] <= dollars)
        {
            upgradeTypes.add("DOLLARS");
        }
        if (upgrades[rank-2][2] <= credits)
        {
            upgradeTypes.add("CREDITS");
        }
        return upgradeTypes;
    }

    public int getCost(int rank, boolean useCredits)
    {
        if (useCredits)
        {
            return upgrades[rank-2][2];
        }
        else
        {
            return upgrades[rank-2][1];
        }
    }
}
