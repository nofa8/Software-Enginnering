import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JTable table1;
    private JTextField autorTxt;
    private JButton pesquisarButton;
    private int width;
    private int height;
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
