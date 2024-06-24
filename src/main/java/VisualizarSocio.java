import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizarSocio extends JFrame{
    private JPanel mainPanel;
    private JButton requisicoesButton;
    private JButton publicacoesButton;
    private JButton definicoesButton;
    private JButton paginaPrincipalButton;
    private JButton voltarButton;
    private JButton editarButton;
    private JLabel nsocio;
    private JLabel nifCC;
    private JLabel telefone;
    private JLabel email;
    private JLabel distrito;
    private JLabel cidade;
    private JLabel anualidade;
    private JLabel incricao;
    private JLabel codigoPostal;
    private JLabel morada;
    private JLabel estado;
    private JLabel nrequisicoes;
    private JButton eliminarButton;
    private JButton pagamentosButton;
    private int width;
    private int height;
    public VisualizarSocio() {
        super("Visualizar Sócio");
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
    }

}
