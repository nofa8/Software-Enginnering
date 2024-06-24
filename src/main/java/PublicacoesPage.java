import Modelos.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class PublicacoesPage extends JFrame{
    private static PublicacoesPage mainFrame;
    private JPanel mainPanel;
    private JButton requisicoesButton;
    private JButton sociosButton;
    private JButton publicacoesButton;
    private JButton definicoesButton;
    private JButton paginaPrincipalButton;
    private JButton adicionarExButton;
    private JButton eliminarButton;
    private JButton visualizarButton;
    private JButton criarButton;
    private JRadioButton topRequisitadosRadioButton;
    private JComboBox generoDrop;
    private JComboBox subgeneroDrop;
    private JTextField autorTxt;
    private JButton pesquisarButton;
    private JList list1;
    private int width;
    private int height;
    private static LinkedList<Obra> obras = null;

    private DefaultListModel<String> listModel;//apagar


    public PublicacoesPage() {
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
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                atualizarObras();
            }
        });

        requisicoesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmprestimosPage.showReqPage();
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
                CriarPublicacao.showCriarPubPage();
            }
        });

        visualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected index from the JList
                int selectedIndex = list1.getSelectedIndex();

                if (selectedIndex != -1) { // Check if any item is selected
                    // Get the selected item (details of obra) from the DefaultListModel
                    Obra obra = obras.get(selectedIndex);
                    if (obra == null){


                        return;
                    }
                    new VisualizarPublicacao(obra);

                } else {
                    // If no item is selected, show an error message
                    JOptionPane.showMessageDialog(PublicacoesPage.this,
                            "Por favor, selecione uma obra para visualizar a estante.",
                            "Nenhuma Obra Selecionada",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (topRequisitadosRadioButton.isSelected()){

                }
                if (generoDrop.getSelectedItem() != null){

                }
                if (subgeneroDrop.getSelectedItem() != null){

                }
                if (autorTxt.getText() != null){

                }
            }
        });
        atualizar();
    }
    private static void atualizarObras(){
        obras = AppData.getInstance().getObras();
        SwingUtilities.invokeLater(() -> {
            if (mainFrame != null) {
                mainFrame.atualizar();
            }
        });
    }
    private void atualizar(){
        listModel = new DefaultListModel<>();
        list1.setModel(listModel);
//        listModel.addElement("\tNºEdição\t\tObra\t\tAno");
        if (obras == null){
            return;
        }
        for(Obra obra : obras){
            listModel.addElement(formatar(obra));
        }

    }

    private String formatar(Obra obra){
        String detalhesObra = "";
        int value = Math.min(34,Integer.toString(obra.getNumeroEdicao()).length());
        for (int i = 0; i < 35- value; i++){
            detalhesObra += " ";
        }
        detalhesObra += obra.getNumeroEdicao() + "   ";
        value = Math.min(64,obra.getTitulo().length());
        for (int i = 0; i < 65- value; i++){
            detalhesObra += " ";
        }
        detalhesObra += obra.getTitulo()+"   ";
        value = Math.min(64,Integer.toString(obra.getAno()).length());
        for (int i = 0; i < 65- value; i++){
            detalhesObra += " ";
        }
        detalhesObra += obra.getAno();
        return detalhesObra;
    }

    public static void showPubPage() {
        if (mainFrame == null) {
            mainFrame = new PublicacoesPage();
        }
        if (!mainFrame.isVisible()) {
            atualizarObras();
            mainFrame.setVisible(true);
        } else {
            atualizarObras();
            mainFrame.toFront();
        }
    }
}
