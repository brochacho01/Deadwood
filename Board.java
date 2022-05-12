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

    // Setup the board
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

    public Player[] getPlayers() {
        return players;
    }
    // add more actions that the player can do, such as view their own stats,
    // location, etc.

    // Create the array of players
    public void setPlayers(int num) {
        players = new Player[num];
        for (int i = 0; i < num; i++) {
            players[i] = new Player(num, i + 1);
        }
    }

    public void setRooms(Room[] newRooms) {
        rooms = newRooms;
        // -2 because office and trailer aren't sets
        this.activeSets = rooms.length - 2;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public String[] getRoomNames() {
        String[] roomNames = new String[rooms.length];
        for (int i = 0; i < rooms.length; i++) {
            roomNames[i] = rooms[i].getName();
        }
        return roomNames;
    }

    public String[] getPlayerNames() {
        String[] playerNames = new String[players.length];
        for (int i = 0; i < players.length; i++) {
            playerNames[i] = players[i].getName();
        }
        return playerNames;
    }

    public Player getPlayer(int i) {
        return players[i];
    }

    public int getPlayerIndex(Player p) {
        return Arrays.asList(players).indexOf(p);
    }

    public String getLocation(int i) {
        return rooms[i].getName();
    }

    public Room getRoom(int location) {
        return rooms[location];
    }

    public int getMaxRehearsalTokens(int location) {
        if (rooms[location] instanceof Set) {
            return ((Set) rooms[location]).getSceneBudget() - 1;
        } else {
            return -1;
        }
    }

    public int matchNameToIndex(String roomName) {
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].getName().toLowerCase().equals(roomName.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    public int matchPlayerToIndex(String playerName) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getName().toLowerCase().equals(playerName.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    public int getActiveSets() {
        return this.activeSets;
    }

    public void printMap() {
    }

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
