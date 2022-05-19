import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

class View {
    private static View view = null;
    private JFrame frame;

    public void setupView() throws IOException {
        frame = new JFrame("Deadwood");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(null);
        Container c = frame.getContentPane();

        JPanel boardPanel = new JPanel();
        JPanel controlPanel = new JPanel();
        JPanel statsPanel = new JPanel();
        boardPanel.setLayout(null);
        controlPanel.setLayout(null);
        statsPanel.setLayout(null);

        boardPanel.setBounds(0, 0, 1200, 900);
        BufferedImage boardPicture = ImageIO.read(new File("./images/board.jpg"));
        JLabel boardPicLabel = new JLabel(new ImageIcon(boardPicture));
        JLayeredPane lp = new JLayeredPane();
        lp.setPreferredSize(new Dimension(1200, 900));
        lp.setBorder(BorderFactory.createTitledBorder("The Board!"));
        boardPicLabel.setBounds(0, 0, 1200, 900);
        boardPanel.add(boardPicLabel);

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

        c.add(boardPanel);
        c.add(controlPanel);
        c.add(statsPanel);

        frame.setSize(1920, 1080);
        frame.setVisible(true);
    }
    
    public static View getView() {
        if (view == null)
            view = new View();

        return view;
    }
}

// //frame.setSize(1600, 900);
// JPanel controlPanel = new JPanel();
// lp.add(boardPicLabel);
// frame.add(lp);