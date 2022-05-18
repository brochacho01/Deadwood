import javax.swing.JFrame;

class View {
    private static View view = null;
    private JFrame frame;

    public void setupView() {
        frame = new JFrame("Deadwood");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static View getView() {
        if (view == null)
            view = new View();

        return view;
    }
}
