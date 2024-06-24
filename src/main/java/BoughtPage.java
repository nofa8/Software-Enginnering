import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoughtPage extends JFrame{
    private JPanel mainPanel;
    private JButton requisicoesButton;
    private JButton sociosButton;
    private JButton publicacoesButton;
    private JButton definicoesButton;
    private JButton paginaPrincipalButton;
    private int width;
    private int height;
    public BoughtPage() {
        super("Bought Page");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        requisicoesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RequisicoesPage();

            }
        });
        publicacoesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PublicacoesPage();
            }
        });
        sociosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SociosPage();
            }
        });
        paginaPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Casa();
            }
        });
        definicoesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConfiguracoesPage();
            }
        });
    }

    public static void main(String[] args) {
        Casa firstPage1 = new Casa();
    }
}
