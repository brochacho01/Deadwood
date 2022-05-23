import java.util.Arrays;

public class Trailer extends Room {
    private int[] area;
    private int[] offSet;

    // Constructor
    public Trailer(String name, String[] neighbors, int[] area) {
        super(name, neighbors, area);
        this.offSet = new int[2];
        Arrays.fill(this.offSet, 0);
    }

    // special printSet method as the Trailer is different from a normal set
    public void printSet() {
        System.out.println("The trailer is the starting place for all players!");
    }
}
