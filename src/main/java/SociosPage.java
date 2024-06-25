import Modelos.Exemplar;
import Modelos.Socio;

import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;

public class SociosPage extends JFrame{
    private static SociosPage mainFrame;
    private JPanel mainPanel;
    private JButton requisicoesButton;
    private JButton sociosButton;
    private JButton publicacoesButton;
    private JButton definicoesButton;
    private JButton paginaPrincipalButton;
    private JButton visualizarButton;
    private JButton alertarDevedoresButton;
    private JButton criarButton;
    private JRadioButton valorEmAtrasoRadioButton;
    private JTextField numeroSocio;
    private JTextField nome;
    private JList list1;
    private JButton pesquisarButton;
    private int width;
    private int height;

    private static HashMap<Integer, Socio> socios = null; //???
    private DefaultListModel<String> listModel;


    public SociosPage() {
        super("Página de Sócios");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                atualizarSocios();
            }
        });

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
        visualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtém o item selecionado na lista
                String selectedValue = list1.getSelectedValue().toString();
                if (selectedValue != null) {
                    // Extrai o número do sócio a partir da string formatada
                    String numeroString = selectedValue.trim().split("\\s+")[0];
                    int numeroSocio = Integer.parseInt(numeroString);

                    // Busca o sócio no HashMap usando o número
                    Socio socioSelecionado = socios.get(numeroSocio);
                    if (socioSelecionado != null) {
                        VisualizarSocio.showVisSocPage(socioSelecionado);
                    }
                }
            }
        });

        criarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CriarSocio.showCriarSocPage();
            }
        });

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = list1.getSelectedIndex();
                    if (index != -1) {
                        visualizarButton.setEnabled(true);
                    } else {
                        visualizarButton.setEnabled(false);
                    }
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mainFrame = null;
            }
        });
        alertarDevedoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resposta = AppData.getInstance().alertarDevedores();
                if (resposta <= 0){
                    JOptionPane.showMessageDialog(mainFrame,
                            "Não existem sócios com dívida.",
                            "Alerta de Devedores",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(mainFrame,
                            "Foram encontrados " + resposta + " empréstimos atrasados.",
                            "Alerta de Devedores",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nSoci = numeroSocio.getText();

                String nomes = nome.getText();
                if (nomes.isEmpty()){
                    nomes = null;
                }

                int soci = 0;
                if (nSoci.isEmpty()){
                    nSoci = null;
                }
                else{
                    try {
                        soci = Integer.parseInt(nSoci);
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(SociosPage.this,
                                "Sócio tem que ser um número inteiro!\n");
                        return;
                    }
                    if (soci < 0) {
                        JOptionPane.showMessageDialog(SociosPage.this,
                                "Sócio tem que ser um inteiro positivo!\n");
                        return;
                    }

                    if (!socios.containsKey(soci)){
                        JOptionPane.showMessageDialog(SociosPage.this,
                                "Número de Sócio inválido",
                                "Número",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }



                socios = AppData.getInstance().filtrarSocios(valorEmAtrasoRadioButton.isSelected(), soci, nomes);
                atualizar();
            }
        });
    }

    private static void atualizarSocios(){
        socios = AppData.getInstance().getSocios();
        SwingUtilities.invokeLater(() -> {
            if (mainFrame != null) {
                mainFrame.atualizar();
            }
        });
    }

    private void atualizar(){
        listModel = new DefaultListModel<>();
        list1.setModel(listModel);
        if (socios.isEmpty()){
            return;
        }
        for(Socio socio : socios.values()){
            listModel.addElement(formatar(socio));
        }

    }


    private String formatar(Socio socio){
        String detalhesSocio = "";
        int value = Math.min(34,Integer.toString(socio.getNumero()).length());
        for (int i = 0; i < 35- value; i++){
            detalhesSocio += " ";
        }
        detalhesSocio += socio.getNumero() + "   ";


        value = Math.min(64,socio.getNome().length());
        for (int i = 0; i < 65- value; i++){
            detalhesSocio += " ";
        }
        detalhesSocio += socio.getNome()+"   ";

        value = Math.min(64,socio.getDataProximoPagamentoAnuidade().toString().length());
        for (int i = 0; i < 65- value; i++){
            detalhesSocio += " ";
        }
        detalhesSocio += socio.getDataProximoPagamentoAnuidade();
        return detalhesSocio;
    }

    public static void showSocPage() {
        if (mainFrame == null) {
            mainFrame = new SociosPage();
        }
        if (!mainFrame.isVisible()) {
            atualizarSocios();
            mainFrame.setVisible(true);
        } else {
            atualizarSocios();
            mainFrame.toFront();
        }
    }
}
