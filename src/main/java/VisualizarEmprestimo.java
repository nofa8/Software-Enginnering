import Modelos.Emprestimo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class VisualizarEmprestimo extends JFrame
{
    private static VisualizarEmprestimo mainFrame;
    private JLabel labelNumSocio;
    private JLabel labelCodExemplar;
    private JLabel labelDataAquisicao;
    private JLabel labelDataFinal;
    private JLabel labelNumDias;
    private JLabel labelMulta;
    private JButton voltarButton;
    private JPanel mainPanel;
    private int width;
    private int height;

    public VisualizarEmprestimo(Emprestimo emprestimo) {
        super("Visualizar Empréstimo");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) (size.getWidth() / 2);
        height = (int) (size.getHeight() / 2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        labelNumSocio.setText(Integer.toString(emprestimo.getSocio().getNumero()));
        labelCodExemplar.setText(emprestimo.getExemplar().getCodigo());
        labelDataAquisicao.setText(emprestimo.getDataEmprestimo().toString());
        labelDataFinal.setText(emprestimo.getDataDevolucaoPrevista().toString());

        long dias = LocalDate.now().toEpochDay() - emprestimo.getDataEmprestimo().toEpochDay();

        labelNumDias.setText(Long.toString(dias));

        labelMulta.setText(Float.toString(emprestimo.getDiasAtraso()*AppData.getInstance().getMultaDiaria()));


        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });
    }
    public static void showReqPage(Emprestimo emprestimo) {
        if (mainFrame == null) {
            mainFrame = new VisualizarEmprestimo(emprestimo);
        }
        if (!mainFrame.isVisible()) {
            mainFrame.setVisible(true);
        } else {
            mainFrame.toFront();
        }
    }
}
