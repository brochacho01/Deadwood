import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

class View {
    private static View view = null;
    private JFrame frame;
    private static JLayeredPane boardLP;
    private static JPanel container;
    private static List<Image> playerImages = new ArrayList<>();
    // Need to store the JLabels for each player somewhere, so that their images can
    // be updated dynamically
    private static HashMap<String, JLabel> playerRepresentation = new HashMap<>();
    private static HashMap<String, JLabel> sceneRepresentation = new HashMap<>();

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
        JPanel controlPanel = new JPanel();
        JPanel statsPanel = new JPanel();
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
        controlPanel.setBackground(Color.green);
        JLabel controlLabel = new JLabel("Controls");
        controlLabel.setBounds(0, 0, 720, 1000);
        controlPanel.add(controlLabel);

        statsPanel.setBounds(0, 900, 1920, 140);
        statsPanel.setBackground(Color.green);
        JLabel statsLabel = new JLabel("Stats");
        statsLabel.setBounds(0, 0, 1920, 140);
        statsPanel.add(statsLabel);

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
            }
        }
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

    // Get die image associated to specific player/their rank
    public static Image getPImage(Player curPlayer) throws IOException {
        String dieIndex = "./images/dice/dice/" + curPlayer.getColor() + Integer.toString(curPlayer.getRank()) + ".png";
        // System.out.println(dieIndex);
        BufferedImage pDie = ImageIO.read(new File(dieIndex));
        return pDie;

    }

    // Initialize arrayList containing images of all players' dice
    public void createPlayerImages() throws IOException {
        Board b = Board.getBoard();
        Player[] players = b.getPlayers();
        for (int i = 0; i < players.length; i++) {
            playerImages.add(getPImage(players[i]));
        }
    }

    // Display the images of the players dice in the trailer
    public void setPlayerImages() {
        Board b = Board.getBoard();
        Player[] p = b.getPlayers();
        for (int i = 0; i < p.length; i++) {
            // placePlayerInRoom(playerImages.get(i), "Trailer");
            int[] roomArea = b.getRoomFromName("Trailer").getArea();
            b.getRoomFromName("Trailer").incrememntOffSet();
            int[] offSet = b.getRoomFromName("Trailer").getOffSet();
            JLabel playerImageJLabel = new JLabel(new ImageIcon(playerImages.get(i)));
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
}
