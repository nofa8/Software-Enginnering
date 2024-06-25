import Modelos.Exemplar;
import Modelos.Obra;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.LinkedList;

public class CriarEmprestimo extends JFrame {
    private static CriarEmprestimo mainFrame;
    private JPanel mainPanel;
    private JList listObrasEmprestimo;

    private JComboBox comboBoxExemplares;
    private JLabel labelData;
    private JTextField textSocioNumero;
    private JButton requisitarButton1;
    private JButton requisitarButton;
    private JTextField textTituloSearch;
    private JLabel labelObraTitulo;
    private JButton voltarButton;
    private int width;
    private int height;
    private static LinkedList<Obra> obras = null;

    private DefaultListModel<String> listModel;

    public CriarEmprestimo()  {
        super("Empréstimos");

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
        ListSelectionListener listSelectionListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(listObrasEmprestimo.getSelectedIndex() != -1){
                    Obra obra = obras.get(listObrasEmprestimo.getSelectedIndex());
                    labelObraTitulo.setText(obra.getTitulo());
                    for(Exemplar exem : obra.getExemplares()){
                        comboBoxExemplares.addItem(exem);
                    }
                    labelData.setText(LocalDate.now().toString() + " - " + LocalDate.now().plusDays(AppData.getInstance().getDuracaoEmprestimo()));
                }
            }
        };
        listObrasEmprestimo.addListSelectionListener(listSelectionListener);

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
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
        listObrasEmprestimo.setModel(listModel);
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



    public static void showCriarEmpPage() {

        if (mainFrame == null) {
            mainFrame = new CriarEmprestimo();
        }
        if (!mainFrame.isVisible()) {
            mainFrame.setVisible(true);
        } else {
            mainFrame.toFront();
        }
    }


}
