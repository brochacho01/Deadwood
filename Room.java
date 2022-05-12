abstract class Room {
    private String name;
    private String[] neighbors;

    // Constructor
    public Room(String name, String[] neighbors) {
        this.name = name;
        this.neighbors = neighbors;
    }

    // Returns the name of the room
    public String getName() {
        return this.name;
    }

    // Returns a string[] containing the neighbors of the room
    public String[] getNeighbors() {
        return this.neighbors;
    }

    public void printSet(){
    }
}
