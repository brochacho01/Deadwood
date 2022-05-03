public class Office extends Room {
    private int[][] upgrades;
    
    public Office(String name, String[] neighbors, int[][] upgrades) {
        super(name, neighbors);
        this.upgrades = upgrades;
    }

    public int[][] getUpgrades(){
        return this.upgrades;
    }
}
