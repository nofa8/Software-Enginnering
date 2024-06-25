import Modelos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class CriarSocio extends JFrame{
    private static CriarSocio me;
    private JPanel mainPanel;
    private JTextField nomeTextField;
    private JButton criarButton;
    private JButton voltarButton;
    private JPanel nSocioLabel;
    private JTextField textFieldNsocio;
    private JTextField textFieldNif;
    private JTextField textFieldTel;
    private JTextField textFieldEmail;
    private JTextField textFieldCidade;
    private JTextField textFieldCodigoPostal;
    private JTextField textFieldMorada;
    private JComboBox comboBoxdistrito;
    private int width;
    private int height;

    public CriarSocio() {
        super("Criar Sócio");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        EnumSet.allOf(Distrito.class)
                .forEach(dist -> comboBoxdistrito.addItem(dist));

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                me.show(false);
                me.dispose();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                me = null;
            }
        });
        criarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (textFieldNsocio.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "Número de sócio tem que ser um preenchido!\n");
                    return;
                }
                if (textFieldCidade.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "Cidade tem que ser um preenchido!\n");
                    return;
                }
                if (textFieldEmail.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "Email tem que ser um preenchido!\n");
                    return;
                }
                if (textFieldCodigoPostal.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "Codigo Postal tem que ser um preenchido!\n");

                    return;
                }
                if (nomeTextField.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "Título tem que ser um preenchido!\n");
                    return;
                }
                if (textFieldNif.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "Nif tem que ser um preenchido!\n");
                    return;
                }
                if (textFieldTel.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "O numero de telefone tem que ser um preenchido!\n");
                    return;
                }
                if (textFieldMorada.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "Morada tem que ser um preenchido!\n");
                    return;
                }


                String nSocio = textFieldNsocio.getText();
                String cidade = textFieldCidade.getText();
                String email = textFieldEmail.getText();
                String codPost = textFieldCodigoPostal.getText();
                String nom = nomeTextField.getText();
                String nif = textFieldNif.getText();
                String tele = textFieldTel.getText();
                String morada = textFieldMorada.getText();

                String dis ;

                try{
                    dis = Objects.requireNonNull(comboBoxdistrito.getSelectedItem()).toString();
                }catch (NullPointerException exception){
                    JOptionPane.showMessageDialog(me,
                            "Os campos têm de ser todos preenchidos!\n");
                    return;
                }
                int numSocio;
                try{
                    numSocio = Integer.parseInt(nSocio);
                }catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(me,
                            "O numero de socio tem que ser um número inteiro positivo\n");
                    return;
                }
                Distrito dist = Distrito.fromFormattedString(dis);

                Socio novoSocio = new Socio(nom,nif,morada,dist,cidade,codPost,tele, email,numSocio);
                AppData.getInstance().adicionarSocio(novoSocio);
                JOptionPane.showMessageDialog(null,
                        "Socio criado com sucesso!\n");
                me.dispose();
                SociosPage.showSocPage();
            }
        });
    }

    private static void cleanPlaces() {
        SwingUtilities.invokeLater(() -> {
            if (me != null) {
                me.clean();
            }
        });
    }

    private void clean() {
        // Atualizar os campos do formulário com os dados da obra
        textFieldNsocio.setText("");
        textFieldNif.setText("");
        textFieldCidade.setText("");
        textFieldCodigoPostal.setText("");
        textFieldMorada.setText("");
        textFieldEmail.setText("");
        textFieldTel.setText("");
        // Set initial values for combo boxes
        comboBoxdistrito.setSelectedItem(comboBoxdistrito.getItemAt(0));
        nomeTextField.setText("Nome");
    }

    public static void showCriarSocPage() {
        if (me == null) {
            me = new CriarSocio();
        }
        if (!me.isVisible()) {
            cleanPlaces();
            me.setVisible(true);
        } else {
            cleanPlaces();
            me.toFront();
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

