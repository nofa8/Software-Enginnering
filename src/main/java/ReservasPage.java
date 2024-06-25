import Modelos.Emprestimo;
import Modelos.Reserva;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private static LinkedList<Reserva> reservas;
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

        reservas = AppData.getInstance().getReservas();

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
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mainFrame = null;
            }
        });
        atualizar();
        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int soc = 0;
                String socio = null;
                if ( nºSócioRadioButton.isSelected() && !numSocio.getText().isEmpty()){
                    socio = numSocio.getText();
                    try {
                        soc = Integer.parseInt(socio);
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(mainFrame,
                                "Sócio tem que ser um número inteiro!\n");
                        return;
                    }
                    if (soc < 0) {
                        JOptionPane.showMessageDialog(mainFrame,
                                "Sócio tem que ser um inteiro positivo!\n");
                        return;
                    }
                }else{
                    JOptionPane.showMessageDialog(mainFrame,
                            "Filtro não definido!\n");
                    return;
                }

                reservas= AppData.getInstance().filtrarReservas(nºSócioRadioButton.isSelected(),soc);
                atualizar();


            }
        });
    }
    private static void atualizarReservas(){
        reservas = AppData.getInstance().getReservas();
        SwingUtilities.invokeLater(() -> {
            if (mainFrame != null) {
                mainFrame.atualizar();
            }
        });

    }
    private void atualizar(){
        listModel = new DefaultListModel<>();
        listReservas.setModel(listModel);
        if ( reservas == null){
            return;
        }
        for(Reserva reserva : reservas){
            listModel.addElement(formatar(reserva));
        }

    }
    private String formatar(Reserva reserva){
        String detalhesReserva = "";
        int value = Math.min(34,Integer.toString(reserva.getSocio().getNumero()).length());
        for (int i = 0; i < 35- value; i++){
            detalhesReserva += " ";
        }
        detalhesReserva += reserva.getObra().getISBN()+ "  ";


        value = Math.min(64,(reserva.getObra().getTitulo() + " - "+ reserva.getSocio().getNumero()).length());
        for (int i = 0; i < 65- value; i++){
            detalhesReserva += " ";
        }
        detalhesReserva += reserva.getObra().getTitulo() + " - "+ reserva.getSocio().getNumero() + "  ";
        int count = 0;
        for (int i = 0; i < reservas.size(); i++) {
            count = 0;
            if(reserva.getObra() == reservas.get(i).getObra()){
                count++;
            }
        }

        value = Math.min(64,Integer.toString(count).length());
        for (int i = 0; i < 65- value; i++){
            detalhesReserva += " ";
        }
        detalhesReserva += count+"   ";
        return detalhesReserva;
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
