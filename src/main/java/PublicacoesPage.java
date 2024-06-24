import Modelos.Obra;
import Modelos.Genero;
import Modelos.Subgenero;
import Modelos.Salas;
import Modelos.Prateleiras;
import Modelos.Estantes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private List<Obra> obrasList; // List to store Obra instances


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

        obrasList = new ArrayList<>();

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
                List<String> autores = Arrays.asList("Joel", "Ellie");
                Obra obra = new Obra(
                        "Título da Obra",
                        autores,
                        Genero.ROMANCE,
                        Subgenero.ARTE_MODERNA,
                        "Editora Exemplo",
                        45641,
                        2023,
                        "123-4567890123",
                        Estantes.ESTANTE_1A,
                        Prateleiras.PRATELEIRA_1,
                        Salas.SALA_101
                );
                Obra obra2 = new Obra(
                        "Fortnite",
                        autores,
                        Genero.ROMANCE,
                        Subgenero.ARTE_MODERNA,
                        "Editora Exemplo",
                        2344531,
                        2022,
                        "123-4567890123",
                        Estantes.ESTANTE_1A,
                        Prateleiras.PRATELEIRA_1,
                        Salas.SALA_101
                );

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dataFormatada = dateFormat.format(new Date());

                // Adicionando os detalhes da obra à JList
                String detalhesObra = obra.getNumeroEdicao() +
                        " | " + obra.getTitulo() +
                        " | " + dataFormatada;
                String detalhesObra2 = obra2.getNumeroEdicao() +
                        " | " + obra2.getTitulo() +
                        " | " + dataFormatada;

                listModel = new DefaultListModel<>();
                list1.setModel(listModel);


                String msg;
                switch (AppData.getInstance().adicionarObra(obra)) {
                    case 0:
                        msg = "Obra adicionada com sucesso.";
                        obrasList.add(obra);
                        listModel.addElement(detalhesObra);
                        break;
                    case -1:
                        msg = "Erro: Obra está vazia.";
                        break;
                    case -2:
                        msg = "Erro: Número de edição já existe.";
                        break;
                    default:
                        msg = "Erro desconhecido.";
                }
                JOptionPane.showMessageDialog(PublicacoesPage.this, msg );

                AppData.getInstance().adicionarObra(obra2);


                obrasList.add(obra2);
                listModel.addElement(detalhesObra2);



            }
        });

        visualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected index from the JList
                int selectedIndex = list1.getSelectedIndex();

                if (selectedIndex != -1) { // Check if any item is selected
                    // Get the selected item (details of obra) from the DefaultListModel
                    String selectedObraDetails = listModel.getElementAt(selectedIndex);

                    // Extract the "estante" information from the selectedObraDetails
                    String[] parts = selectedObraDetails.split("\\|"); // Split by '|'
                    String estante = parts[0].trim(); // Assuming "estante" is the third part

                    // Show a message dialog with the "estante" information
                    JOptionPane.showMessageDialog(PublicacoesPage.this,
                            "Estante da Obra Selecionada: " + estante.trim(),
                            "Detalhes da Estante",
                            JOptionPane.INFORMATION_MESSAGE);
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
    }
    public static void showPubPage() {
        if (mainFrame == null) {
            mainFrame = new PublicacoesPage();
        }
        if (!mainFrame.isVisible()) {
            mainFrame.setVisible(true);
        } else {
            mainFrame.toFront();
        }
    }
}
