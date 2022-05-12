import javax.xml.parsers.ParserConfigurationException;

class Deadwood {
    // This will contain our system logic
    private static Board b;
    private static Deck d;
    private static int day;
    private static int maxDays;
    private static int numPlayers;
    private static int pTurn;

    public static void main(String[] args) throws ParserConfigurationException {
        
        //Print welcome message
        System.out.println("\n\n\n        :::       ::: :::::::::: :::        ::::::::   ::::::::    :::   :::   ::::::::::      ::::::::::: ::::::::                                                                     \n       :+:       :+: :+:        :+:       :+:    :+: :+:    :+:  :+:+: :+:+:  :+:                 :+:    :+:    :+:                                                                     \n      +:+       +:+ +:+        +:+       +:+        +:+    +:+ +:+ +:+:+ +:+ +:+                 +:+    +:+    +:+                                                                      \n     +#+  +:+  +#+ +#++:++#   +#+       +#+        +#+    +:+ +#+  +:+  +#+ +#++:++#            +#+    +#+    +:+                                                                       \n    +#+ +#+#+ +#+ +#+        +#+       +#+        +#+    +#+ +#+       +#+ +#+                 +#+    +#+    +#+                                                                        \n    #+#+# #+#+#  #+#        #+#       #+#    #+# #+#    #+# #+#       #+# #+#                 #+#    #+#    #+#                                                                         \n    ###   ###   ########## ########## ########   ########  ###       ### ##########          ###     ########                                                                           \n          :::::::::  ::::::::::     :::     :::::::::  :::       :::  ::::::::   ::::::::  :::::::::          :::::::: ::::::::::: :::    ::: ::::::::: ::::::::::: ::::::::   :::::::: \n         :+:    :+: :+:          :+: :+:   :+:    :+: :+:       :+: :+:    :+: :+:    :+: :+:    :+:        :+:    :+:    :+:     :+:    :+: :+:    :+:    :+:    :+:    :+: :+:    :+: \n        +:+    +:+ +:+         +:+   +:+  +:+    +:+ +:+       +:+ +:+    +:+ +:+    +:+ +:+    +:+        +:+           +:+     +:+    +:+ +:+    +:+    +:+    +:+    +:+ +:+         \n       +#+    +:+ +#++:++#   +#++:++#++: +#+    +:+ +#+  +:+  +#+ +#+    +:+ +#+    +:+ +#+    +:+        +#++:++#++    +#+     +#+    +:+ +#+    +:+    +#+    +#+    +:+ +#++:++#++   \n      +#+    +#+ +#+        +#+     +#+ +#+    +#+ +#+ +#+#+ +#+ +#+    +#+ +#+    +#+ +#+    +#+               +#+    +#+     +#+    +#+ +#+    +#+    +#+    +#+    +#+        +#+    \n     #+#    #+# #+#        #+#     #+# #+#    #+#  #+#+# #+#+#  #+#    #+# #+#    #+# #+#    #+#        #+#    #+#    #+#     #+#    #+# #+#    #+#    #+#    #+#    #+# #+#    #+#     \n    #########  ########## ###     ### #########    ###   ###    ########   ########  #########          ########     ###      ########  ######### ########### ########   ########       \n\n\n");
        setupGame();
        System.out.println("Break line for debugging");
    }

    // Setup the game
    public static void setupGame() throws ParserConfigurationException {
        //Create the xml parser
        XMLParse xml = new XMLParse();
        //Create the board and the deck
        b = xml.parseBoard();
        d = xml.parseDeck();

        //Get the players
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
        //Start the first day
        day = 0;
        pTurn = 0;
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
            d.dealCards();
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
    public static void turnFlow() 
    {
        //While there are still more than 1 active sets
        while(b.getActiveSets() > 1)
        {
            Player curPlayer = b.getPlayer(pTurn);
            curPlayer.startTurn();
            View.doTurn(curPlayer);
            pTurn = (pTurn + 1) % numPlayers;
        }
        endDay();
    }

    public void printSet()
    {
    }
}