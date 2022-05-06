abstract class Room {
    private String name;
    private String[] neighbors;

    public Room(String name, String[] neighbors) {
        this.name = name;
        this.neighbors = neighbors;
    }

    public String getName()
    {
        return this.name;
    }

}
