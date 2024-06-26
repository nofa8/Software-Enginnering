import Modelos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.regex.Pattern;

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

                if (isNullOrEmpty(textFieldNsocio.getText())) {
                    showError(me, "Número de sócio tem que ser preenchido!");
                    return;
                }

                if (!isValidNumber(textFieldNsocio.getText(), 0)) {
                    showError(me, "O número de sócio apenas pode conter números!");
                    return;
                }
                if (isNullOrEmpty(textFieldCidade.getText())) {
                    showError(me, "Cidade tem que ser preenchida!");
                    return;
                }
                if (isNullOrEmpty(textFieldEmail.getText())) {
                    showError(me, "Email tem que ser preenchido!");
                    return;
                }
                if (!isValidEmail(textFieldEmail.getText())) {
                    showError(me, "Email tem que ter um formato válido!");
                    return;
                }
                if (isNullOrEmpty(textFieldCodigoPostal.getText())) {
                    showError(me, "Código Postal tem que ser preenchido!");
                    return;
                }
                if (isNullOrEmpty(nomeTextField.getText())) {
                    showError(me, "Título tem que ser preenchido!");
                    return;
                }
                if (isNullOrEmpty(textFieldNif.getText())) {
                    showError(me, "NIF tem que ser preenchido!");
                    return;
                }
                if (!isValidNifCC(textFieldNif.getText())) {
                    showError(me, "NIF/CC tem que conter mais que 9 caracteres  alfanumérico !");
                    return;
                }
                if (isNullOrEmpty(textFieldTel.getText())) {
                    showError(me, "O número de telefone tem que ser preenchido!");
                    return;
                }
                if (!isValidNumber(textFieldTel.getText(), 9)) {
                    showError(me, "O número de telefone tem que conter exatamente 9 dígitos!");
                    return;
                }
                if (isNullOrEmpty(textFieldMorada.getText())) {
                    showError(me, "Morada tem que ser preenchida!");
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
    private static void showError(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Error" ,JOptionPane.ERROR_MESSAGE);
    }
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private static boolean isValidNumber(String str, int length) {
        if (str == null || (str.length() != length && length != 0)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    private static boolean isValidNifCC(String nif) {
        return nif.matches("[a-zA-Z0-9]+") && nif.length() >= 9;
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
}

