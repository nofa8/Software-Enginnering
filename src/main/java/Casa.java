import Modelos.PieChart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.formdev.flatlaf.FlatLightLaf;

public class Casa extends JFrame {
    private static Casa mainFrame;
    private JPanel mainPanel;
    private JButton requisicoesButton;
    private JButton sociosButton;
    private JButton publicacoesButton;
    private JButton definicoesButton;
    private JButton paginaPrincipalButton;
    private JLabel time;
    private JLabel date;
    private JLabel month;
    private JLabel week;
    private JLabel today;
    private JButton sairButton;
    private JPanel grafico;
    private PieChart pieChart;
    private int width;
    private int height;
    private static int todaI;
    private static int weekI;
    private static int monthI;
    private static LocalDate now;
    private static LocalDateTime now1;

    public Casa() {
        super("Casa");
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
        sociosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SociosPage.showSocPage();
            }
        });
        definicoesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfiguracoesPage.showConfPage();
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            AppData.getInstance().saveToFile();
        }));
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });
    }

    private static void atualizarEstatistica() {
        AppData instance = AppData.getInstance();
        todaI = instance.today();
        weekI = instance.week();
        monthI = instance.month();
        now = LocalDate.now();
        now1 = LocalDateTime.now();
        SwingUtilities.invokeLater(() -> {
            if (mainFrame != null) {
                mainFrame.atualizar();
            }
        });
    }

    private void atualizar() {
        today.setText("Hoje: " + todaI);
        week.setText("Esta Semana: " + weekI);
        month.setText("Este Mês: " + monthI);
        date.setText((now.toString()));
        time.setText(now1.toLocalTime().toString().substring(0,8));
    }

    public static void showCasaPage() {
        if (mainFrame == null) {
            mainFrame = new Casa();
        }
        if (!mainFrame.isVisible()) {
            mainFrame.setVisible(true);
        } else {
            mainFrame.toFront();
        }
        atualizarEstatistica();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        showCasaPage();
    }
}