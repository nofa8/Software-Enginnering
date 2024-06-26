import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.formdev.flatlaf.FlatLightLaf;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;


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
    private JLabel imagemBola;
    private int width;
    private int height;
    private static int todaI;
    private static int weekI;
    private static int monthI;
    private static LocalDate now;
    private static LocalDateTime now1;
    private int valueFirst;
    private int valueSecond;
    private int icone;
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
        icone = 0;
        createPieChart();
        setChartImage();
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
            if (icone != 0){
                try {
                    Files.deleteIfExists(Paths.get("src/main/resources/images/pie_chart"+icone+".png"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }));
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void createPieChart() {
        try {
            Files.deleteIfExists(Paths.get("src/main/resources/images/pie_chart"+icone+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int valor1 = AppData.getInstance().getDuracaoEmprestimo();
        int valor2 = 100-valor1;
        DefaultPieDataset dataset = new DefaultPieDataset();
        valueFirst = AppData.getInstance().totalRequisicoes();
        valueSecond = AppData.getInstance().totalDisponiveis();
        dataset.setValue("Exemplares Requisitados: "+valueFirst,valueFirst );
        dataset.setValue("Exemplares Disponiveis: "+valueSecond, valueSecond );

        JFreeChart pieChart = ChartFactory.createPieChart(
                "", //porque fica mais minimalista
                dataset,
                true,
                true,
                false
        );
        icone ++;
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSectionPaint("Exemplares Requisitados: "+valueFirst, new Color(255, 100, 100));
        plot.setSectionPaint("Exemplares Disponiveis: "+valueSecond, new Color(100, 100, 255));
        try {
            File outputfile = new File("src/main/resources/images/pie_chart"+icone+".png");
            ChartUtils.saveChartAsPNG(outputfile, pieChart, width/2, width/2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setChartImage() {
        // Define the path to the saved image
        String path = "src/main/resources/images/pie_chart"+icone+".png";
        ImageIcon icon = new ImageIcon(path);
        // Set the icon to the JLabel
        imagemBola.setIcon(icon);

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
        AppData instance = AppData.getInstance();
        if (instance.totalRequisicoes() != valueFirst || instance.totalDisponiveis() != valueSecond) {
            imagemBola.setIcon(null);
            createPieChart();
            setChartImage();
        }

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