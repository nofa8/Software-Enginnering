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
        super("Página de Publicações");
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

                    if (exemplares.getSelectedItem() == null){
                        JOptionPane.showMessageDialog(PublicacoesPage.this,
                                "Por favor, selecione um exemplar para eliminar .",
                                "Nenhum Exemplar Selecionado",
                                JOptionPane.WARNING_MESSAGE);
                    }

                    int response = AppData.getInstance().eliminarExemplar(obra, (String) exemplares.getSelectedItem());
                    if (response == -1){
                        JOptionPane.showMessageDialog(PublicacoesPage.this,
                                "Por favor, selecione um exemplar para eliminar .",
                                "Nenhum Exemplar Selecionado",
                                JOptionPane.WARNING_MESSAGE);
                    }else if(response == 1){
                        JOptionPane.showMessageDialog(PublicacoesPage.this,
                                "O Exemplar selecionado ainda se encontra requesitado .",
                                "Nenhum Exemplar Selecionado",
                                JOptionPane.WARNING_MESSAGE);
                    }else if(response == 2){
                        JOptionPane.showMessageDialog(PublicacoesPage.this,
                                "O exemplar da obra não foi encontrado.",
                                "Nenhum Exemplar Selecionado",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(PublicacoesPage.this,
                                "O exemplar da obra foi eliminado com sucesso.",
                                "Exemplar Eliminado",
                                JOptionPane.INFORMATION_MESSAGE);
                        if (obra.getNumeroExemplares() == 0){

                            int confirm = JOptionPane.showConfirmDialog(mainFrame, "A obra já não tem nenhum exemplar.\nDeseja eliminar a obra " + obra.getTitulo() + "?", "Confirmar Eliminação", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                AppData.getInstance().eliminarObra(obra);
                                atualizarObras();
                            }
                        }
                    }


                } else {
                    // If no item is selected, show an error message
                    JOptionPane.showMessageDialog(PublicacoesPage.this,
                            "Por favor, selecione uma obra para eliminar um exemplar .",
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
        int valur = width/30;
        int value = Math.min(valur-1,Integer.toString(obra.getNumeroEdicao()).length());
        for (int i = 0; i < valur- value; i++){
            detalhesObra += " ";
        }
        detalhesObra += obra.getNumeroEdicao() + "   ";
        value = Math.min(2*valur-1,obra.getTitulo().length());
        int sub = 0;
        if (obra.getTitulo().length() > 2*valur-1){
            sub = 2*valur-1 - obra.getTitulo().length();
        }
        for (int i = 0; i < 2*valur- value; i++){
            detalhesObra += " ";
        }
        detalhesObra += obra.getTitulo()+"   ";
        value = Math.min(2*valur-1,Integer.toString(obra.getAno()).length());
        for (int i = 0; i < 2*valur - value-sub; i++){
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
