public class Trailer extends Room {
    // Constructor
    public Trailer(String name, String[] neighbors) {
        super(name, neighbors);
    }

    // special printSet method as the Trailer is different from a normal set
    public void printSet() {
        System.out.println("The trailer is the starting place for all players!");
    }
}
