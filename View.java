import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle.Control;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;

import java.awt.event.*;

class View {
    private static View view = null;
    private static JFrame frame;
    private static JLayeredPane boardLP;
    private static JPanel container;
    private static JPanel controlPanel;
    private static JScrollPane statsPanel;
    // Need to store the JLabels for each player somewhere, so that their images can
    // be updated dynamically, the String is their name and the JLabel is their
    // associated image
    private static HashMap<String, JLabel> playerRepresentation = new HashMap<>();
    private static HashMap<String, JLabel> sceneRepresentation = new HashMap<>();
    private static HashMap<String, JTable> playerStats = new HashMap<>();
    protected static Integer selection;
    protected static String name;
    protected static boolean gotAction;

    public void setupView() throws IOException {
        // Initialize our outermost frame
        frame = new JFrame("Deadwood");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        Container c = frame.getContentPane();
        container = new JPanel();
        container.setLayout(null);
        container.setSize(1920, 1080);
        container.setVisible(true);
        // Initialize our 3 mane panes
        boardLP = new JLayeredPane();
        controlPanel = new JPanel();
        statsPanel = new JScrollPane();
        boardLP.setLayout(null);
        controlPanel.setLayout(null);
        statsPanel.setLayout(null);

        // Setup the boardPanel
        // Get board picture
        boardLP.setBounds(0, 0, 1200, 900);
        BufferedImage boardPicture = ImageIO.read(new File("./images/board.jpg"));
        boardLP.setPreferredSize(new Dimension(1200, 900));
        JLabel boardPicLabel = new JLabel(new ImageIcon(boardPicture));
        // Add picture to lp
        boardPicLabel.setBounds(0, 0, 1200, 900);
        boardPicLabel.setSize(boardPicLabel.getPreferredSize());
        boardLP.add(boardPicLabel, 0);

        controlPanel.setBounds(1200, 0, 720, 1000);
        JLabel controlLabel = new JLabel("Controls");
        controlLabel.setBounds(0, 0, 720, 1000);
        controlPanel.add(controlLabel);

        statsPanel.setBounds(0, 900, 1920, 140);
        // JLabel statsLabel = new JLabel("Stats");
        // statsLabel.setBounds(0, 0, 1920, 140);
        // statsPanel.add(statsLabel);

        container.add(boardLP);
        container.add(controlPanel);
        container.add(statsPanel);

        c.add(container);

        frame.setSize(1920, 1080);
        frame.setVisible(true);
    }

    public static View getView() {
        if (view == null)
            view = new View();

        return view;
    }

    // TODO Somehow this is actually displaying the card. It only worked when adding
    // in the new Integer(1) clause however
    public void drawSceneCards() throws IOException {
        Board b = Board.getBoard();
        Room[] rooms = b.getRooms();
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] instanceof Set) {
                Room curSet = rooms[i];
                int[] curSetArea = curSet.getArea();
                JLabel setImage = new JLabel(new ImageIcon(((Set) curSet).getCardImage()));
                setImage.setBounds(curSetArea[0], curSetArea[1], curSetArea[2], curSetArea[3]);
                setImage.setLocation(curSetArea[0], curSetArea[1] - 90);
                setImage.setSize(curSetArea[2] + 90, curSetArea[3] + 90);
                sceneRepresentation.put(curSet.getName(), setImage);
                boardLP.add(setImage, new Integer(1));
                boardLP.revalidate();
                boardLP.repaint();
            }
        }
    }

    public void removeSceneCard(String setName) {
        Board b = Board.getBoard();
        JLabel setImage = sceneRepresentation.get(setName);
        Room curSet = b.getRoomFromName(setName);
        int[] curSetArea = curSet.getArea();
        setImage.setIcon(null);
        setImage.setBounds(curSetArea[0], curSetArea[1], curSetArea[2], curSetArea[3]);
        setImage.setLocation(curSetArea[0], curSetArea[1] - 90);
        setImage.setSize(curSetArea[2] + 90, curSetArea[3] + 90);
        sceneRepresentation.put(curSet.getName(), setImage);
        boardLP.add(setImage, new Integer(1));
        boardLP.revalidate();
        boardLP.repaint();
    }

    // Flips the sceneCard when player gets to scene for the first time
    public void flipScene(String sceneName, Image cardFront) {
        JLabel setImage = sceneRepresentation.get(sceneName);
        setImage.setIcon(new ImageIcon(cardFront));
        boardLP.remove(sceneRepresentation.get(sceneName));
        sceneRepresentation.put(sceneName, setImage);
        boardLP.add(setImage);
        boardLP.revalidate();
        boardLP.repaint();
    }

    // Update curPlayer's die image when they upgrade
    public void upgradePImage(Player curPlayer) throws IOException {
        Board b = Board.getBoard();
        String playerName = curPlayer.getName();
        // Define where the Label should be
        int[] roomArea = b.getRoom(curPlayer.getLocation()).getArea();
        // Need to do some incrementing and decrementing so that when a player upgrades,
        // they still respect the offset but appear in the same place as they were
        // before upgrading
        b.getRoom(curPlayer.getLocation()).decrementOffSet();
        int[] offSet = b.getRoom(curPlayer.getLocation()).getOffSet();
        JLabel playerImageJLabel = playerRepresentation.get(playerName);
        playerImageJLabel.setIcon(new ImageIcon(getPImage(curPlayer)));
        playerImageJLabel.setBounds(roomArea[0], roomArea[1], roomArea[2], roomArea[3]);
        playerImageJLabel.setLocation(roomArea[0] + offSet[0], roomArea[1] + offSet[1] + 120);
        // Dice images are 40x40 pixels
        playerImageJLabel.setSize(40, 40);
        boardLP.remove(playerRepresentation.get(playerName));
        playerRepresentation.put(playerName, playerImageJLabel);
        boardLP.add(playerImageJLabel, new Integer(2));
        boardLP.revalidate();
        boardLP.repaint();
        b.getRoom(curPlayer.getLocation()).incrememntOffSet();
    }

    // Get die image associated to specific player/their rank
    public static Image getPImage(Player curPlayer) throws IOException {
        String dieIndex = "./images/dice/dice/" + curPlayer.getColor() + Integer.toString(curPlayer.getRank()) + ".png";
        // System.out.println(dieIndex);
        BufferedImage pDie = ImageIO.read(new File(dieIndex));
        return pDie;

    }

    // Display the images of the players dice in the trailer
    public void setPlayerImages() throws IOException {
        Board b = Board.getBoard();
        Player[] p = b.getPlayers();
        for (int i = 0; i < p.length; i++) {
            // placePlayerInRoom(playerImages.get(i), "Trailer");
            int[] roomArea = b.getRoomFromName("Trailer").getArea();
            b.getRoomFromName("Trailer").incrememntOffSet();
            int[] offSet = b.getRoomFromName("Trailer").getOffSet();
            // JLabel playerImageJLabel = new JLabel(new ImageIcon(playerImages.get(i)));
            JLabel playerImageJLabel = new JLabel(new ImageIcon(getPImage(p[i])));
            playerImageJLabel.setBounds(roomArea[0], roomArea[1], roomArea[2], roomArea[3]);
            playerImageJLabel.setLocation(roomArea[0] + offSet[0], roomArea[1] + offSet[1]);
            // Dice images are 40x40 pixels
            playerImageJLabel.setSize(40, 40);
            playerRepresentation.put(p[i].getName(), playerImageJLabel);
            boardLP.add(playerImageJLabel, new Integer(1));
            boardLP.revalidate();
            boardLP.repaint();
        }
    }

    // Display JTables showing the stats of each player
    public void displayPlayerStats(Player curPlayer){
        String pName = curPlayer.getName();
        // If a players' stats have been initialized, instead update them
        if(playerStats.containsKey(pName)){
            updatePlayerStats(curPlayer);
        } else {
            String pRank = String.valueOf(curPlayer.getRank());
        String pMoney = String.valueOf(curPlayer.getBalance());
        String pCredits = String.valueOf(curPlayer.getCredits());
        String pRehearsalTokens = String.valueOf(curPlayer.getRehearalTokens());
        String color = Character.toString(curPlayer.getColor());
        String[] primMetaData = {"Name" , "Rank" , "Money" , "Credits", "Rehearsal Tokens", "Color"};
        String[] primData = {pName, pRank, pMoney, pCredits, pRehearsalTokens, color};
        String[][] pData = new String[6][2];
        for(int i = 0; i < 6; i++) {
            pData[i][0] = primMetaData[i];
            pData[i][1] = primData[i];
        }
        String[] colNames = {"a", curPlayer.getName()};
        JTable curPStats = new JTable(pData, colNames);
        curPStats.setBounds(curPlayer.getStatOffset(), 1, 232, 96);
        playerStats.put(curPlayer.getName(), curPStats);
        statsPanel.add(curPStats);
        statsPanel.revalidate();
        statsPanel.repaint();
        }
    }

    // Once a players stars have been initialized they will need to be updated when various events occur
    public void updatePlayerStats(Player curPlayer) {
        String pRank = String.valueOf(curPlayer.getRank());
        String pMoney = String.valueOf(curPlayer.getBalance());
        String pCredits = String.valueOf(curPlayer.getCredits());
        String pRehearsalTokens = String.valueOf(curPlayer.getRehearalTokens());
        JTable pStats = playerStats.get(curPlayer.getName());
        pStats.setValueAt(pRank, 1, 1);
        pStats.setValueAt(pMoney, 2, 1);
        pStats.setValueAt(pCredits, 3, 1);
        pStats.setValueAt(pRehearsalTokens, 4, 1);
        pStats.repaint();
    }


    public void takeStarRole(String playerName, String roomName, String roleName) {
        Board b = Board.getBoard();
        int[] curSetArea = b.getRoomFromName(roomName).getArea();
        int[] roleArea = ((Set) b.getRoomFromName(roomName)).getScene().getRole(roleName).getArea();
        JLabel playerImageJLabel = playerRepresentation.get(playerName);
        playerImageJLabel.setBounds(roleArea[0], roleArea[1], roleArea[2], roleArea[3]);
        playerImageJLabel.setLocation(roleArea[0] + curSetArea[0] + 1, roleArea[1] + curSetArea[1] + 1);
        playerImageJLabel.setSize(40, 40);
        // Decrement offset because player is going from "waiting area" to a role
        // therefore the offset needs to be changed
        b.getRoomFromName(roomName).decrementOffSet();
        boardLP.remove(playerRepresentation.get(playerName));
        playerRepresentation.put(playerName, playerImageJLabel);
        boardLP.add(playerImageJLabel, new Integer(3));
        boardLP.revalidate();
        boardLP.repaint();
    }

    // Place a player on their desired role on the set, decrement room offset and
    // make sure the image moves
    public void takeExtraRole(String playerName, String roomName, String roleName) {
        Board b = Board.getBoard();
        int[] roleArea = ((Set) b.getRoomFromName(roomName)).getRole(roleName).getArea();
        JLabel playerImageJLabel = playerRepresentation.get(playerName);
        playerImageJLabel.setBounds(roleArea[0], roleArea[1], roleArea[2], roleArea[3]);
        playerImageJLabel.setLocation(roleArea[0] + 3, roleArea[1] + 3);
        playerImageJLabel.setSize(40, 40);
        // Decrement offset because player is going from "waiting area" to a role
        // therefore the offset needs to be changed
        b.getRoomFromName(roomName).decrementOffSet();
        boardLP.remove(playerRepresentation.get(playerName));
        playerRepresentation.put(playerName, playerImageJLabel);
        boardLP.add(playerImageJLabel, new Integer(2));
        boardLP.revalidate();
        boardLP.repaint();
    }

    // Place a player in a specified location, and remove their die from the old
    // location
    public void placePlayerInRoom(String playerName, String roomName) {
        Board b = Board.getBoard();
        int[] roomArea = b.getRoomFromName(roomName).getArea();
        int[] offSet = b.getRoomFromName(roomName).getOffSet();
        JLabel playerImageJLabel = playerRepresentation.get(playerName);
        playerImageJLabel.setBounds(roomArea[0], roomArea[1], roomArea[2], roomArea[3]);
        playerImageJLabel.setLocation(roomArea[0] + offSet[0], roomArea[1] + offSet[1] + 120);
        // Dice images are 40x40 pixels
        playerImageJLabel.setSize(40, 40);
        b.getRoomFromName(roomName).incrememntOffSet();
        boardLP.remove(playerRepresentation.get(playerName));
        playerRepresentation.put(playerName, playerImageJLabel);
        boardLP.add(playerImageJLabel, new Integer(2));
        boardLP.revalidate();
        boardLP.repaint();
    }

    public static int getNumPlayersInput() throws InterruptedException {
        // Add all the ui elements
        JLabel askPlayers = new JLabel("How many players are playing?");
        askPlayers.setBounds(0, 0, 500, 100);
        askPlayers.setLocation(180, 100);
        controlPanel.add(askPlayers);
        Integer[] playerChoices = { 2, 3, 4, 5, 6, 7, 8 };
        JComboBox<Integer> jComboBox = new JComboBox<>(playerChoices);
        jComboBox.setBounds(80, 50, 140, 20);
        jComboBox.setLocation(180, 200);
        controlPanel.add(jComboBox);
        final JButton jButton = new JButton("Start!");
        jButton.setBounds(100, 100, 90, 20);
        jButton.setLocation(180, 250);
        controlPanel.add(jButton);
        controlPanel.revalidate();
        controlPanel.repaint();
        frame.setVisible(true);
        // create an action listener (weird)
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent aActionEvent) {
                selection = jComboBox.getItemAt(jComboBox.getSelectedIndex());
            }
        });
        // wait for the player to select the number of players
        while (selection == null) {
            Thread.sleep(1);
        }
        controlPanel.remove(askPlayers);
        controlPanel.remove(jComboBox);
        controlPanel.remove(jButton);
        controlPanel.revalidate();
        controlPanel.repaint();
        frame.setVisible(true);
        return selection;
    }

    public static String getPlayerName(int i) throws InterruptedException {
        String localName;
        // Add all the ui elements
        JLabel askName = new JLabel("Player " + i + ", what is your name?");
        askName.setBounds(0, 0, 500, 100);
        askName.setLocation(180, 100);
        controlPanel.add(askName);
        JTextField textField = new JTextField();
        textField.setBounds(80, 50, 140, 20);
        textField.setLocation(180, 200);
        controlPanel.add(textField);
        final JButton jButton = new JButton("Submit");
        jButton.setBounds(100, 100, 90, 20);
        jButton.setLocation(180, 250);
        controlPanel.add(jButton);
        controlPanel.revalidate();
        controlPanel.repaint();
        frame.setVisible(true);
        // create an action listener (weird)
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent aActionEvent) {
                name = textField.getText();
            }
        });
        // wait for the player to select the number of players
        while (name == null || name == "") {
            Thread.sleep(1);
        }
        controlPanel.remove(askName);
        controlPanel.remove(textField);
        controlPanel.remove(jButton);
        controlPanel.revalidate();
        controlPanel.repaint();
        frame.setVisible(true);
        localName = name;
        name = null;
        return localName;
    }

    public static void getPlayerAction(Player curPlayer, ArrayList<String> actions) throws InterruptedException {
        Board b = Board.getBoard();
        int pLocation = curPlayer.getLocation();
        int playerRank = curPlayer.getRank();
        // Add all the ui elements
        JLabel turnInfo = new JLabel(curPlayer.getName() + ", it is your turn!\nWhat would you lie to do?");
        turnInfo.setBounds(0, 0, 500, 100);
        turnInfo.setLocation(180, 100);
        controlPanel.add(turnInfo);
        final JButton moveButton = new JButton("Move");
        // moveButton.setBounds(100, 100, 90, 20);
        // moveButton.setLocation(180, 250);
        moveButton.setEnabled(false);
        controlPanel.add(moveButton);
        JComboBox<String> moveOptions = new JComboBox<>(b.getRoom(pLocation).getNeighbors());
        // moveOptions.setBounds(80, 50, 140, 20);
        // moveOptions.setLocation(180, 200);
        controlPanel.add(moveOptions);
        if (actions.stream().anyMatch("move"::equalsIgnoreCase))
        {
            moveButton.setEnabled(true);
            moveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent aActionEvent) {
                    String moveChoice = moveOptions.getItemAt(moveOptions.getSelectedIndex());
                    if (moveChoice != null && moveChoice != "") {
                        try {
                            gotAction = true;
                            b.getRoom(pLocation).decrementOffSet();
                            curPlayer.move(b.matchNameToIndex(moveChoice));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        final JButton roleButton = new JButton("Take Role");
        roleButton.setEnabled(false);
        JComboBox<String> extraRoles = new JComboBox<>(((Set) b.getRoom(pLocation)).getExtraRoles(playerRank));
        JComboBox<String> starRoles = new JComboBox<>(((Set) b.getRoom(pLocation)).getScene().getStarRoles(playerRank));
        // String[] extraRoles = ((Set) b.getRoom(pLocation)).getExtraRoles(playerRank);
        // String[] starRoles = ((Set) b.getRoom(pLocation)).getScene().getStarRoles(playerRank);
        controlPanel.add(roleButton);
        controlPanel.add(extraRoles);
        controlPanel.add(starRoles);
        if (actions.stream().anyMatch("take role"::equalsIgnoreCase))
        {
            roleButton.setEnabled(true);
            roleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent aActionEvent) {
                    String extraChoice = extraRoles.getItemAt(moveOptions.getSelectedIndex());
                    String starChoice = starRoles.getItemAt(moveOptions.getSelectedIndex());
                    if (extraChoice != null && extraChoice != "") {
                        gotAction = true;
                        curPlayer.takeExtraRole(extraChoice);
                    }
                    else if (starChoice != null && starChoice != "") {
                        gotAction = true;
                        curPlayer.takeStarRole(starChoice);
                    }
                }
            });
        }
        controlPanel.revalidate();
        controlPanel.repaint();
        while (!gotAction) {
            Thread.sleep(1);
        }
    }
}