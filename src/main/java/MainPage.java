import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame{
    private JPanel mainPanel;
    private JButton requisiçõesButton;
    private JButton sóciosButton;
    private JButton publicaçõesButton;
    private JButton fornecedoresButton;
    private JButton definiçõesButton;

    public MainPage() {
        super("Main");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)(size.getWidth()/2);
        int height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        MainPage firstPage1 = new MainPage();
    }
}
