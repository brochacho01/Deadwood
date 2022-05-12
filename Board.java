import java.util.*;

class Board {
    private static Room[] rooms;
    private static Player[] players;
    private int activeSets;
    // Singleton board instance
    private static Board board = null;

    // Private constructor
    private Board() {
    }

    // When a scene wraps want to notify the board of this
    public void decrementActiveSets() {
        activeSets--;
        if (activeSets > 2) {
            System.out.println("There are " + activeSets + " sets left!");
        }
        if (activeSets == 2) {
            System.out.println("There are only " + activeSets + " sets remaining!");
        }
        if (activeSets == 1) {
            System.out.println("There is only " + activeSets + " set remaining!");
            System.out.println("The day comes to an end...");
        }
    }

    // Get the singleton instance of the board
    public static Board getBoard() {
        if (board == null)
            board = new Board();

        return board;
    }

    // Setup the board at beginning of game as well as reset the board on new days
    public void setBoard() {
        for (int i = 0; i < players.length; i++) {
            // Reset the roles of players and move them to the trailers
            players[i].resetRole();
            players[i].resetLocation();
        }
        // Need to also reset each set on the board
        for (int j = 0; j < rooms.length; j++) {
            if (rooms[j] instanceof Set) {
                ((Set) rooms[j]).reset();
            }
        }
        // Reset the active sets
        activeSets = 10;
    }

    // randomize the order of players
    public void randomizePlayers() {
        List<Player> playersList = Arrays.asList(players);
        Collections.shuffle(playersList);
        playersList.toArray(players);
    }

    // Return the array of players
    public Player[] getPlayers() {
        return players;
    }

    // Create the array of players
    public void setPlayers(int num) {
        players = new Player[num];
        for (int i = 0; i < num; i++) {
            players[i] = new Player(num, i + 1);
        }
    }

    // Create the room array containing all the rooms for the board
    public void setRooms(Room[] newRooms) {
        rooms = newRooms;
        // -2 because office and trailer aren't sets
        this.activeSets = rooms.length - 2;
    }

    // Return the array of rooms
    public Room[] getRooms() {
        return rooms;
    }

    // Returns a string array containing the name of every room
    public String[] getRoomNames() {
        String[] roomNames = new String[rooms.length];
        for (int i = 0; i < rooms.length; i++) {
            roomNames[i] = rooms[i].getName();
        }
        return roomNames;
    }

    // Returns a string array of all player names
    public String[] getPlayerNames() {
        String[] playerNames = new String[players.length];
        for (int i = 0; i < players.length; i++) {
            playerNames[i] = players[i].getName();
        }
        return playerNames;
    }

    // Returns a player at specified index in the player array
    public Player getPlayer(int i) {
        return players[i];
    }

    // Gets the index of a player in the player array based on the name of the
    // player
    public int getPlayerIndex(Player p) {
        return Arrays.asList(players).indexOf(p);
    }

    // Returns the name of a room based on its index in the room array
    public String getLocation(int i) {
        return rooms[i].getName();
    }

    // Return a room based off of its index in the room array
    public Room getRoom(int location) {
        return rooms[location];
    }

    // Gets the max rehearsal tokens a player can have for a given set
    public int getMaxRehearsalTokens(int location) {
        if (rooms[location] instanceof Set) {
            return ((Set) rooms[location]).getSceneBudget() - 1;
        } else {
            return -1;
        }
    }

    // Gets the index of a room based off of its name
    public int matchNameToIndex(String roomName) {
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].getName().toLowerCase().equals(roomName.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    // returns index of player based off of the name of the player
    public int matchPlayerToIndex(String playerName) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getName().toLowerCase().equals(playerName.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    // Returns the number of active sets left
    public int getActiveSets() {
        return this.activeSets;
    }

    // Calculates and returns the winner of the game based off of their balance
    // (money), credits, and rank
    public Player calculateWinner() {
        Player winner = players[0];
        int winBalance = (winner.getRank() * 5) + winner.getBalance() + winner.getCredits();
        for (int i = 1; i < players.length; i++) {
            if (((players[i].getRank() * 5) + players[i].getBalance() + players[i].getCredits()) > winBalance) {
                winner = players[i];
                winBalance = (players[i].getRank() * 5) + players[i].getBalance() + players[i].getCredits();
            }
        }
        return winner;
    }
}
