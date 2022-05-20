abstract class Room {
    private String name;
    private String[] neighbors;
    private int[] area; 

    // Constructor
    public Room(String name, String[] neighbors, int[] area) {
        this.name = name;
        this.neighbors = neighbors;
        this.area = area;
    }

    public int[] getArea(){
        return this.area;
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
