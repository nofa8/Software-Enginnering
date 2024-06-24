import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfiguracoesPage extends JFrame{
    private static ConfiguracoesPage mainFrame;
    private JPanel mainPanel;
    private JButton requisicoesButton;
    private JButton sociosButton;
    private JButton publicacoesButton;
    private JButton definicoesButton;
    private JButton paginaPrincipalButton;
    private JButton duracaoEmpButton;
    private JTextField duracaoEmp;
    private JTextField emprestimosSim;
    private JButton emprestimosSimButton;
    private JTextField anualidade;
    private JButton anualidadeButton;
    private JTextField multaDiaria;
    private JButton multaDiariaButton;
    private int width;
    private int height;
    public ConfiguracoesPage() {
        super("Página de Configurações");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        AppData instance = AppData.getInstance();
        duracaoEmp.setText(String.valueOf(instance.getDuracaoEmprestimo()));
        emprestimosSim.setText(String.valueOf(instance.getLimiteEmprestimosSim()));
        anualidade.setText(String.valueOf(instance.getAnualidade()));
        multaDiaria.setText(String.valueOf(instance.getMultaDiaria()));

        requisicoesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmprestimosPage.showReqPage();

            }
        });
        publicacoesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PublicacoesPage.showPubPage();
            }
        });
        sociosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SociosPage.showSocPage();
            }
        });
        paginaPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Casa.showCasaPage();
            }
        });
        anualidadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String anualidadeText = anualidade.getText();
                try {
                    float anualidade = Float.parseFloat(anualidadeText);
                    if (anualidade <= 0) {
                        JOptionPane.showMessageDialog(null, "A Duração da Anualidade tem que ser um número superior a 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    AppData.getInstance().setAnualidade(anualidade);
                    JOptionPane.showMessageDialog(null, "Duração da anualidade atualizada com sucesso.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "A Duração da Anualidade tem que ser um número superior a 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        duracaoEmpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentDuration = duracaoEmp.getText();
                try {
                    int duration = Integer.parseInt(currentDuration);
                    if (duration <= 0) {
                        JOptionPane.showMessageDialog(null, "A Duração do Empréstimo tem que ser um número superior a 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Update the duration in the AppData instance
                    AppData.getInstance().setDuracaoEmprestimo(duration);
                    // Update the text field with the new duration
                    JOptionPane.showMessageDialog(null, "Duração do empréstimo atualizado com sucesso.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    // Handle the case where the current text is not a valid integer
                    JOptionPane.showMessageDialog(null, "A Duração do Empréstimo tem que ser um número superior a 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        emprestimosSimButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentDuration = emprestimosSim.getText();
                try {
                    int simultaneo = Integer.parseInt(currentDuration);
                    if (simultaneo <= 0) {
                        JOptionPane.showMessageDialog(null, "O Número de Empréstimos em Simultâneo tem que ser um número superior a 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    AppData.getInstance().setLimiteEmprestimosSim(simultaneo);
                    JOptionPane.showMessageDialog(null, "Número de empréstimos em simultâneo atualizado com sucesso.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    // Handle the case where the current text is not a valid integer
                    JOptionPane.showMessageDialog(null, "O Número de Empréstimos em Simultâneo tem que ser um número superior a 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        multaDiariaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String multaDiariaText = multaDiaria.getText();
                try {
                    float multaDiaria = Float.parseFloat(multaDiariaText);
                    if (multaDiaria <= 0.0) {
                        JOptionPane.showMessageDialog(null, "O Valor da Multa Diária tem que ser um número superior a 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    AppData.getInstance().setMultaDiaria(multaDiaria);
                    JOptionPane.showMessageDialog(null, "Valor da multa diária atualizado com sucesso.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    // Handle the case where the current text is not a valid integer
                    JOptionPane.showMessageDialog(null, "O Valor da Multa Diária tem que ser um número superior a 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    public static void showConfPage() {
        if (mainFrame == null) {
            mainFrame = new ConfiguracoesPage();
        }
        if (!mainFrame.isVisible()) {
            mainFrame.setVisible(true);
        } else {
            mainFrame.toFront();
        }
    }
}
