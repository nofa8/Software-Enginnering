import javax.swing.*;
import java.awt.*;

public class VisualizarReserva extends JFrame{
    private JPanel mainPanel;
    private JLabel nocio;
    private JLabel nedicao;
    private JLabel inicio;
    private JLabel nila;
    private JButton voltarButton;
    private JButton eliminarButton;
    private int width;
    private int height;
    public VisualizarReserva() {
        super("Visualizar Reserva");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

}
