import Modelos.Socio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Pagamentos extends JFrame{
    private static Pagamentos me;
    private JPanel mainPanel;
    private JTextField multaTotal;
    private JButton pagarMultaButton;
    private JTextField anualidade;
    private JButton pagarAnualidadeButton;
    private JLabel proximaAnualidade;
    private JLabel nomSocio;
    private JButton voltarButton;
    private int width;
    private int height;
    private Socio socio;
    public Pagamentos(Socio novo) {
        super("Página de Pagamentos");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        socio = novo;
        nomSocio.setText(socio.getNome());
        proximaAnualidade.setText(String.valueOf(socio.getDataProximoPagamentoAnuidade()));
        anualidade.setText(String.valueOf(AppData.getInstance().getAnualidade()));
        multaTotal.setText(String.valueOf(socio.getValorEmDivida()));

        pagarAnualidadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Socio response = AppData.getInstance().pagarAnuidade(socio);
                if (response == null){
                    JOptionPane.showMessageDialog(me,
                            "Pagamento não efetuado. Sócio não encontrado.",
                            "Anualidade",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(me,
                        "Pagamento efetuado com sucesso.",
                        "Anualidade",
                        JOptionPane.INFORMATION_MESSAGE);
                atualizarSocio(response);

            }
        });
        pagarMultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Socio response = AppData.getInstance().pagarMulta(socio);
                if (response == null){
                    JOptionPane.showMessageDialog(me,
                            "Pagamento não efetuado. Sócio não encontrado.",
                            "Multa",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(me,
                        "Pagamento efetuado com sucesso.",
                        "Multa",
                        JOptionPane.INFORMATION_MESSAGE);
                atualizarSocio(response);

            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisualizarSocio.showVisSocPage(socio);
                me.dispose();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                me = null;
            }
        });

    }

    private static void atualizarSocio(Socio socio) {
        me.socio = socio;
        me.nomSocio.setText(socio.getNome());
        me.proximaAnualidade.setText(String.valueOf(socio.getDataProximoPagamentoAnuidade()));
        me.anualidade.setText(String.valueOf(AppData.getInstance().getAnualidade()));
        me.multaTotal.setText(String.valueOf(socio.getValorEmDivida()));
    }


    public static void showPagamentosPage(Socio socio) {
        if (me == null) {
            me = new Pagamentos(socio);
        }
        if (!me.isVisible()) {
            atualizarSocio(socio);
            me.setVisible(true);
        } else {
            atualizarSocio(socio);
            me.toFront();
        }
    }

}
