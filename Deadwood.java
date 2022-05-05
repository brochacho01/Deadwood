import javax.xml.parsers.ParserConfigurationException;

class Deadwood {
    // This will contain our system logic
    private static Board b;
    private static int day;
    private static int maxDays;

    public static void main(String[] args) throws ParserConfigurationException {
        XMLParse xml = new XMLParse();
        b = xml.parseBoard();
        Deck d = xml.parseDeck();
        //Print welcome message
        System.out.println("\n\n\n        :::       ::: :::::::::: :::        ::::::::   ::::::::    :::   :::   ::::::::::      ::::::::::: ::::::::                                                                     \n       :+:       :+: :+:        :+:       :+:    :+: :+:    :+:  :+:+: :+:+:  :+:                 :+:    :+:    :+:                                                                     \n      +:+       +:+ +:+        +:+       +:+        +:+    +:+ +:+ +:+:+ +:+ +:+                 +:+    +:+    +:+                                                                      \n     +#+  +:+  +#+ +#++:++#   +#+       +#+        +#+    +:+ +#+  +:+  +#+ +#++:++#            +#+    +#+    +:+                                                                       \n    +#+ +#+#+ +#+ +#+        +#+       +#+        +#+    +#+ +#+       +#+ +#+                 +#+    +#+    +#+                                                                        \n    #+#+# #+#+#  #+#        #+#       #+#    #+# #+#    #+# #+#       #+# #+#                 #+#    #+#    #+#                                                                         \n    ###   ###   ########## ########## ########   ########  ###       ### ##########          ###     ########                                                                           \n          :::::::::  ::::::::::     :::     :::::::::  :::       :::  ::::::::   ::::::::  :::::::::          :::::::: ::::::::::: :::    ::: ::::::::: ::::::::::: ::::::::   :::::::: \n         :+:    :+: :+:          :+: :+:   :+:    :+: :+:       :+: :+:    :+: :+:    :+: :+:    :+:        :+:    :+:    :+:     :+:    :+: :+:    :+:    :+:    :+:    :+: :+:    :+: \n        +:+    +:+ +:+         +:+   +:+  +:+    +:+ +:+       +:+ +:+    +:+ +:+    +:+ +:+    +:+        +:+           +:+     +:+    +:+ +:+    +:+    +:+    +:+    +:+ +:+         \n       +#+    +:+ +#++:++#   +#++:++#++: +#+    +:+ +#+  +:+  +#+ +#+    +:+ +#+    +:+ +#+    +:+        +#++:++#++    +#+     +#+    +:+ +#+    +:+    +#+    +#+    +:+ +#++:++#++   \n      +#+    +#+ +#+        +#+     +#+ +#+    +#+ +#+ +#+#+ +#+ +#+    +#+ +#+    +#+ +#+    +#+               +#+    +#+     +#+    +#+ +#+    +#+    +#+    +#+    +#+        +#+    \n     #+#    #+# #+#        #+#     #+# #+#    #+#  #+#+# #+#+#  #+#    #+# #+#    #+# #+#    #+#        #+#    #+#    #+#     #+#    #+# #+#    #+#    #+#    #+#    #+# #+#    #+#     \n    #########  ########## ###     ### #########    ###   ###    ########   ########  #########          ########     ###      ########  ######### ########### ########   ########       \n\n\n");
        setupGame(b, d);
    }

    // Setup the game
    public static void setupGame(Board b, Deck d) {
        // getPlayers
        int numPlayers = View.getNumPlayers();

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
        // beginDay
        day = 0;
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