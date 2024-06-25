import Modelos.Reserva;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

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
    private JButton voltarButton;
    private JList listReservas;
    private DefaultListModel<String> listModel;
    private int width;
    private int height;
    public ReservasPage() {
        super("Página de Reservas");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        listModel = new DefaultListModel<>();
        listReservas.setModel(listModel);

        LinkedList<Reserva> reservas = AppData.getInstance().getReservas();

        for (Reserva reserva : reservas)
        {
            int count = 0;
            for (int i = 0; i < reservas.size(); i++) {
                count = 0;
                if(reserva.getObra() == reservas.get(i).getObra()){
                    count++;
                }
            }
            String detalhesReserva = reserva.getObra().getISBN() +
                    " | " + reserva.getObra().getTitulo() +
                    " | " + count;
            listModel.addElement(detalhesReserva);
        }


        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });
        visualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
