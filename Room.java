import java.util.Arrays;

abstract class Room {
    private String name;
    private String[] neighbors;
    private int[] area;
    // This offsets the rendering of a player image on a room so players don't cover eachother
    private int[] offSet;

    // Constructor
    public Room(String name, String[] neighbors, int[] area) {
        this.name = name;
        this.neighbors = neighbors;
        this.area = area;
        this.offSet = new int[2];
        Arrays.fill(this.offSet, 0);
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

    public void incrememntOffSet(){
        if(offSet[0] == 120){
            offSet[0] = 0;
            offSet[1] += 40;
        } else {
            offSet[0] += 40;
        }
    }

    public void decrementOffSet(){
        if(offSet[0] == 0){
            offSet[0] = 120;
            offSet[1] -= 40;
        } else {
            offSet[0] -= 40;
        }
    }

    public int[] getOffSet(){
        return this.offSet;
    }
}
