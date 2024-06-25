import Modelos.Socio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pagamentos extends JFrame{
    private static Pagamentos me;
    private JPanel mainPanel;
    private JTextField multaTotal;
    private JButton pagarMultaButton;
    private JTextField anualidade;
    private JButton pagarAnualidadeButton;
    private JLabel proximaAnualidade;
    private JLabel nomSocio;
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
        me = this;
        socio = novo;
        nomSocio.setText(socio.getNome());
        proximaAnualidade.setText(String.valueOf(socio.getDataProximoPagamentoAnuidade().plusYears(1)));
        anualidade.setText(String.valueOf(AppData.getInstance().getAnualidade()));
        multaTotal.setText(String.valueOf(socio.getValorEmDivida()));

        pagarAnualidadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppData.getInstance().pagarAnuidade(socio);
            }
        });
        pagarMultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppData.getInstance().pagarMulta(socio);
            }
        });
    }

    private static void atualizarSocio(Socio socio) {
        me.socio = socio;
        me.nomSocio.setText(socio.getNome());
        me.proximaAnualidade.setText(String.valueOf(socio.getDataProximoPagamentoAnuidade().plusYears(1)));
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
