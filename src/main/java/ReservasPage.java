import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservasPage extends JFrame{
    private static ReservasPage mainFrame;
    private JPanel mainPanel;
    private JButton requisicoesButton;
    private JButton sociosButton;
    private JButton publicacoesButton;
    private JButton definicoesButton;
    private JButton paginaPrincipalButton;
    private JRadioButton nºSócioRadioButton;
    private JTextField numSocio;
    private JButton pesquisarButton;
    private JButton visualizarButton;
    private JTable table1;
    private JButton voltarButton;
    private int width;
    private int height;
    public ReservasPage() {
        super("Página de Reservas");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.show(false);
            }
        });
    }
    public static void showReqPage() {
        if (mainFrame == null) {
            mainFrame = new ReservasPage();
        }
        if (!mainFrame.isVisible()) {
            mainFrame.setVisible(true);
        } else {
            mainFrame.toFront();
        }
    }

}
