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
    private JButton requisitarButton;
    private JButton reservarButton;
    private JTextField textTituloSearch;
    private JLabel labelObraTitulo;
    private JButton voltarButton;
    private JButton pesquisarTituloButton;
    private int width;
    private int height;
    private static LinkedList<Obra> obras = null;

    private DefaultListModel<String> listModel;

    public CriarEmprestimo()  {
        super("Criar Empréstimo");

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

        ListSelectionListener listSelectionListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int disponiveis = 0;
                if(listObrasEmprestimo.getSelectedIndex() != -1){
                    Obra obra = obras.get(listObrasEmprestimo.getSelectedIndex());
                    labelObraTitulo.setText(obra.getTitulo());
                    comboBoxExemplares.removeAllItems();
                    for(Exemplar exem : obra.getExemplares()){
                        if(exem.isDisponivel()){
                            comboBoxExemplares.addItem(exem.getCodigo());
                            disponiveis++;
                        }

                    }
                    if(disponiveis == 0){
                        requisitarButton.setEnabled(false);
                        reservarButton.setEnabled(true);
                    }
                    else{
                        requisitarButton.setEnabled(true);
                        reservarButton.setEnabled(false);
                    }
                    labelData.setText(LocalDate.now().toString() + " - " + LocalDate.now().plusDays(AppData.getInstance().getDuracaoEmprestimo()));

                }else{
                    comboBoxExemplares.removeAllItems();
                }

            }
        };
        listObrasEmprestimo.addListSelectionListener(listSelectionListener);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mainFrame = null;
            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });
        requisitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Obra obra = obras.get(listObrasEmprestimo.getSelectedIndex());
                String codeExem = comboBoxExemplares.getSelectedItem().toString();
                Exemplar exemplarRequisitar = null;
                for(Exemplar exemplar: obra.getExemplares()){
                    if(exemplar.getCodigo() == codeExem){
                        exemplarRequisitar = exemplar;
                    }
                }
                String numSocio = textSocioNumero.getText();
                if(exemplarRequisitar == null || numSocio.isEmpty()){
                    JOptionPane.showMessageDialog(CriarEmprestimo.this,
                            "Valores inválidos para criar Empréstimo","Empréstimo não criado",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int i = AppData.getInstance().adicionarEmprestimo(exemplarRequisitar, numSocio);
                if (i == -1){
                    JOptionPane.showMessageDialog(CriarEmprestimo.this,
                            "Sócio não existe","Empréstimo não criado",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (i == -2){
                    JOptionPane.showMessageDialog(CriarEmprestimo.this,
                            "Sócio não tem permissão ou possibilidade de requisitar","Empréstimo não criado",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(CriarEmprestimo.this,
                        "Empréstimo criado com sucesso","Empréstimo criado",
                        JOptionPane.INFORMATION_MESSAGE);
                EmprestimosPage.showReqPage();
                mainFrame.dispose();
            }
        });
        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Obra obra = obras.get(listObrasEmprestimo.getSelectedIndex());
                String numSocio = textSocioNumero.getText();

                if(obra == null || numSocio.isEmpty()){
                    JOptionPane.showMessageDialog(CriarEmprestimo.this,
                            "Valores inválidos para criar Reserva","Reserva não criada",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                AppData.getInstance().adicionarReserva(obra, numSocio);
                JOptionPane.showMessageDialog(CriarEmprestimo.this,
                        "Reserva criada com sucesso","Reserva criado",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        pesquisarTituloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = textTituloSearch.getText();
                if (titulo.isEmpty()){
                    JOptionPane.showMessageDialog(CriarEmprestimo.this,
                            "Título tem que ser preenchido para filtrar!","Falta de Título",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                obras= AppData.getInstance().filtrarObrasEmprestimo(titulo);
                atualizar();
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
            atualizarObras();
            mainFrame.setVisible(true);
        } else {
            atualizarObras();
            mainFrame.toFront();
        }
    }
}
