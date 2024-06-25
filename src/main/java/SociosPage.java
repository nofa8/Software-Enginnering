import Modelos.Socio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

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
    private int width;
    private int height;

    private static HashMap<Integer, Socio> socios = null; //???
    private DefaultListModel<String> listModel;


    public SociosPage() {
        super("Página de Sócios");
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
        if (socios == null){
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
