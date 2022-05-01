class Deadwood {
    // This will contain our system logic
    private Board board;
    private int day;
    private int maxDays;

    public static void main(String[] args) {
        System.out.println("Hello world");
    }

    // Setup the game
    public void setupGame() {
        // int players = 0;
        board.setDay(0);
        board.randomizePlayers();
        board.setBoard();
        // beginDay
        return;
    }

    // End the game
    public void endGame() {
        return;
    }

    // Start a day
    public void startDay() {
        day = day + 1;
        if (day > maxDays) {
            endGame();
        }
        return;
    }

    // End a day
    public void endDay() {
        return;
    }

    // controls the flow of turns, when not beginning/ending the day or game, the
    // program will be looping in here
    public void turnFlow() {
        // While notEndDay
        // call Turn for curPlayer
        // if dayEnd call endDay break out of loop
            // If last day endGame
            // else startDay
        // if curPlayer ends turn
        // next player turn
    }

    // Verify a move was valid
    public boolean verifyMove(Player p, int[][] map) {
        return true;
    }

}