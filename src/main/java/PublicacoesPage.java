import Modelos.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

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
    private JComboBox exemplares;
    private int width;
    private int height;
    private static LinkedList<Obra> obras = null;

    private DefaultListModel<String> listModel;


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
        generoDrop.addItem(null);
        EnumSet.allOf(Genero.class)
                .forEach(gene -> generoDrop.addItem(gene));
        subgeneroDrop.addItem(null);
        EnumSet.allOf(Subgenero.class)
                .forEach(gene -> subgeneroDrop.addItem(gene));
        EnumSet.allOf(Editora.class);


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
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = list1.getSelectedIndex();
                    if (index != -1) {
                        adicionarExButton.setEnabled(true);
                        visualizarButton.setEnabled(true);
                        eliminarButton.setEnabled(true);

                        List<Exemplar> exemplares1 = AppData.getInstance().getExemplares(obras.get(index));
                        if (!exemplares1.isEmpty()) {
                            exemplares.removeAllItems();
                            for (Exemplar ex : exemplares1) {
                                exemplares.addItem(ex.getCodigo());
                            }
                        }else{
                            exemplares.removeAllItems();
                        }
                    } else {
                        adicionarExButton.setEnabled(false);
                        visualizarButton.setEnabled(false);
                        eliminarButton.setEnabled(false);
                        exemplares.removeAllItems();
                    }
                }
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
                String autor = null;
                if (!autorTxt.getText().isEmpty()){
                    autor = autorTxt.getText();
                }
                obras = AppData.getInstance().
                        filtrarObras(autor,
                                (Subgenero) subgeneroDrop.getSelectedItem(),
                                (Genero) generoDrop.getSelectedItem(),
                                topRequisitadosRadioButton.isSelected());
                atualizar();
            }
        });
        atualizar();

        adicionarExButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list1.getSelectedIndex();

                if (selectedIndex != -1) { // Check if any item is selected
                    // Get the selected item (details of obra) from the DefaultListModel
                    Obra obra = obras.get(selectedIndex);
                    if (obra == null){


                        return;
                    }

                    new AdicionarExemplar(obra);

                } else {
                    // If no item is selected, show an error message
                    JOptionPane.showMessageDialog(PublicacoesPage.this,
                            "Por favor, selecione uma obra para criar um exemplar.",
                            "Nenhuma Obra Selecionada",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list1.getSelectedIndex();

                if (selectedIndex != -1) { // Check if any item is selected
                    // Get the selected item (details of obra) from the DefaultListModel
                    Obra obra = obras.get(selectedIndex);
                    if (obra == null){
                        return;
                    }



                } else {
                    // If no item is selected, show an error message
                    JOptionPane.showMessageDialog(PublicacoesPage.this,
                            "Por favor, selecione uma exemplar para eliminar .",
                            "Nenhuma Obra Selecionada",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
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
