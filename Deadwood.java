import javax.xml.parsers.ParserConfigurationException;

class Deadwood {
    // This will contain our system logic
    private static Board b;
    private static Deck d;
    private static int day;
    private static int maxDays;
    private static int numPlayers;

    public static void main(String[] args) throws ParserConfigurationException {
        
        //Print welcome message
        System.out.println("\n\n\n        :::       ::: :::::::::: :::        ::::::::   ::::::::    :::   :::   ::::::::::      ::::::::::: ::::::::                                                                     \n       :+:       :+: :+:        :+:       :+:    :+: :+:    :+:  :+:+: :+:+:  :+:                 :+:    :+:    :+:                                                                     \n      +:+       +:+ +:+        +:+       +:+        +:+    +:+ +:+ +:+:+ +:+ +:+                 +:+    +:+    +:+                                                                      \n     +#+  +:+  +#+ +#++:++#   +#+       +#+        +#+    +:+ +#+  +:+  +#+ +#++:++#            +#+    +#+    +:+                                                                       \n    +#+ +#+#+ +#+ +#+        +#+       +#+        +#+    +#+ +#+       +#+ +#+                 +#+    +#+    +#+                                                                        \n    #+#+# #+#+#  #+#        #+#       #+#    #+# #+#    #+# #+#       #+# #+#                 #+#    #+#    #+#                                                                         \n    ###   ###   ########## ########## ########   ########  ###       ### ##########          ###     ########                                                                           \n          :::::::::  ::::::::::     :::     :::::::::  :::       :::  ::::::::   ::::::::  :::::::::          :::::::: ::::::::::: :::    ::: ::::::::: ::::::::::: ::::::::   :::::::: \n         :+:    :+: :+:          :+: :+:   :+:    :+: :+:       :+: :+:    :+: :+:    :+: :+:    :+:        :+:    :+:    :+:     :+:    :+: :+:    :+:    :+:    :+:    :+: :+:    :+: \n        +:+    +:+ +:+         +:+   +:+  +:+    +:+ +:+       +:+ +:+    +:+ +:+    +:+ +:+    +:+        +:+           +:+     +:+    +:+ +:+    +:+    +:+    +:+    +:+ +:+         \n       +#+    +:+ +#++:++#   +#++:++#++: +#+    +:+ +#+  +:+  +#+ +#+    +:+ +#+    +:+ +#+    +:+        +#++:++#++    +#+     +#+    +:+ +#+    +:+    +#+    +#+    +:+ +#++:++#++   \n      +#+    +#+ +#+        +#+     +#+ +#+    +#+ +#+ +#+#+ +#+ +#+    +#+ +#+    +#+ +#+    +#+               +#+    +#+     +#+    +#+ +#+    +#+    +#+    +#+    +#+        +#+    \n     #+#    #+# #+#        #+#     #+# #+#    #+#  #+#+# #+#+#  #+#    #+# #+#    #+# #+#    #+#        #+#    #+#    #+#     #+#    #+# #+#    #+#    #+#    #+#    #+# #+#    #+#     \n    #########  ########## ###     ### #########    ###   ###    ########   ########  #########          ########     ###      ########  ######### ########### ########   ########       \n\n\n");
        setupGame();
        System.out.println("Break line for debugging");
    }

    // Setup the game
    public static void setupGame() throws ParserConfigurationException {
        XMLParse xml = new XMLParse();
        b = xml.parseBoard();
        d = xml.parseDeck();
        // getPlayers
        numPlayers = View.getNumPlayers();

        //Create the players in the board
        b.setPlayers(numPlayers);

        //Set the max number of days
        if (numPlayers < 4)
        {
            maxDays = 3;
        }
        else
        {
            maxDays = 4;
        }

        //Randomize the order of players
        b.randomizePlayers();

        //Setup the board
        b.setBoard();
        //d.dealCards(b);
        // beginDay
        day = 0;
        startDay();
        return;
    }

    // End the game
    public static void endGame() {
        return;
    }

    // Start a day
    public static void startDay() {
        day = day + 1;
        if (day > maxDays) {
            endGame();
        } else {
            // If not last day, deal new scenecards to each set
            d.dealCards(b);
        }
        //Game is ready to go, players can now take their turns.
        turnFlow();
        return;
    }

    // End a day
    public static void endDay() {
        // setBoard moves all the players to the trailer as well as resetting all of the sets on the board
        b.setBoard();
    }

    // controls the flow of turns, when not beginning/ending the day or game, the
    // program will be looping in here
    public static void turnFlow() {
        // While notEndDay
        for(int i = 0; i < numPlayers; i++)
        {
            Player curPlayer = b.getPlayer(i);
            // call Turn for curPlayer
            View.startTurn(curPlayer);
            View.getAction(curPlayer, b);
            // if dayEnd call endDay break out of loop
            // if curPlayer ends turn
            // next player turn
        }
    }

    // Verify a move was valid
    public boolean verifyMove(Player p, int[][] map) {
        return true;
    }

}