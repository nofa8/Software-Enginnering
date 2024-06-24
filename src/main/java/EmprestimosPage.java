import Modelos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    public EmprestimosPage() {
        super("Bought Page");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        listModel = new DefaultListModel<>();
        listEmprestimos.setModel(listModel);


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
                Emprestimo emprestimo = new Emprestimo(new Exemplar("123456", new Obra("Queixo no Soco", List.of("ola"),Genero.AVENTURA, Subgenero.DISTOPIA,
                        Editora.HACHETTE_BOOK_GROUP, 1234,2015,"dfegr", Estantes.ESTANTE_1A,Prateleiras.PRATELEIRA_1,Salas.SALA_101,Distribuidor.ALMEDINA)),
                        new Socio("Rei","123456778","dfeghrtj", "91972345", "arroz@mail.com",12),12);
                String detalhesEmprestimo = emprestimo.getSocio().getNumero() +
                        " | " + emprestimo.getExemplar().getObra().getTitulo() + " - " + emprestimo.getExemplar().getCodigo() +
                        " | " + emprestimo.getDataEmprestimo();


                listModel.addElement(detalhesEmprestimo);

                JOptionPane.showMessageDialog(EmprestimosPage.this,
                        "Emprestimos criados com sucesso!\n");
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
