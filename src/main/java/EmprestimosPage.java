import Modelos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EmprestimosPage extends JFrame{
    private static EmprestimosPage mainFrame;
    private JPanel mainPanel;
    private JButton requisicoesButton;
    private JButton sociosButton;
    private JButton publicacoesButton;
    private JButton definicoesButton;
    private JButton paginaPrincipalButton;
    private JRadioButton nºSócioRadioButton;
    private JTextField numSocio;
    private JRadioButton codExemplarRadioButton;
    private JTextField codExemplar;
    private JRadioButton valorEmAtrasoRadioButton;
    private JButton pesquisarButton;
    private JButton reservasButton;
    private JButton devolverButton;
    private JButton criarButton;
    private JButton visualizarButton;
    private JList listEmprestimos;
    private DefaultListModel<String> listModel;
    private int width;
    private int height;
    private static LinkedList<Emprestimo> emprestimos = null;
    public EmprestimosPage() {
        super("Empréstimos");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                atualizarEmprestimos();
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
        definicoesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfiguracoesPage.showConfPage();
            }
        });
        criarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CriarEmprestimo.showCriarEmpPage();
            }
        });
        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(listEmprestimos.getSelectedIndex() == -1){
                    JOptionPane.showMessageDialog(EmprestimosPage.this,
                            "Selecione um empréstimo!\n");
                    return;
                }
                int indice = listEmprestimos.getSelectedIndex();
                LinkedList<Emprestimo> emprestimos = AppData.getInstance().getEmprestimos();

                Emprestimo emprestimo = emprestimos.get(indice);
                if (emprestimo.getDataDevolucaoEfetiva() != null){
                    JOptionPane.showMessageDialog(EmprestimosPage.this,
                            "Empréstimo já terminado\n");
                    return;
                }

                emprestimo.realizarDevolucao(AppData.getInstance().getMultaDiaria());
                JOptionPane.showMessageDialog(EmprestimosPage.this,
                        "Devolução realizada com sucesso!\n");
            }
        });
        reservasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReservasPage.showReqPage();
            }
        });
        visualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(listEmprestimos.getSelectedIndex() == -1){
                    JOptionPane.showMessageDialog(EmprestimosPage.this,
                            "Selecione um empréstimo!\n");
                    return;
                }
                int indice = listEmprestimos.getSelectedIndex();
                LinkedList<Emprestimo> emprestimos = AppData.getInstance().getEmprestimos();
                Emprestimo emprestimo = emprestimos.get(indice);
                VisualizarEmprestimo.showReqPage(emprestimo);
            }
        });
        atualizar();
    }
    private static void atualizarEmprestimos(){
        emprestimos = AppData.getInstance().getEmprestimos();
        SwingUtilities.invokeLater(() -> {
            if (mainFrame != null) {
                mainFrame.atualizar();
            }
        });
    }
    private void atualizar(){
        listModel = new DefaultListModel<>();
        listEmprestimos.setModel(listModel);
        if ( emprestimos == null){
            return;
        }
        for(Emprestimo emprestimo : emprestimos){
            listModel.addElement(formatar(emprestimo));
        }

    }
    private String formatar(Emprestimo emprestimo){
        String detalhesEmprestimo = "";
        int value = Math.min(34,Integer.toString(emprestimo.getSocio().getNumero()).length());
        for (int i = 0; i < 35- value; i++){
            detalhesEmprestimo += " ";
        }
        detalhesEmprestimo += emprestimo.getExemplar().getObra().getTitulo() + " - "+ emprestimo.getExemplar().getCodigo() + "  ";
        value = Math.min(64,(emprestimo.getExemplar().getObra().getTitulo() + " - "+ emprestimo.getExemplar().getCodigo()).length());
        for (int i = 0; i < 65- value; i++){
            detalhesEmprestimo += " ";
        }
        detalhesEmprestimo += emprestimo.getDataEmprestimo()+"   ";
        value = Math.min(64,(emprestimo.getDataEmprestimo().toString()).length());
        for (int i = 0; i < 65- value; i++){
            detalhesEmprestimo += " ";
        }

        return detalhesEmprestimo;
    }
    public static void showReqPage() {
        if (mainFrame == null) {
            mainFrame = new EmprestimosPage();
        }
        if (!mainFrame.isVisible()) {
            mainFrame.setVisible(true);
        } else {
            mainFrame.toFront();
        }
    }

}
