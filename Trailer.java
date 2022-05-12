public class Trailer extends Room{
    public Trailer(String name, String[] neighbors) {
        super(name, neighbors);
    }

    public void printSet()
    {
        System.out.println("The trailer is the starting place for all players!");
    }
}
