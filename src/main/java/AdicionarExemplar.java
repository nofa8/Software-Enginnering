import Modelos.Exemplar;
import Modelos.Obra;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdicionarExemplar extends JFrame{
    private static AdicionarExemplar me;
    private JPanel mainPanel;
    private JButton voltarButton;
    private JButton criarButton;
    private JLabel edicao;
    private JLabel isbn;
    private JLabel editora;
    private JLabel distribuidor;
    private JLabel autor;
    private JLabel genero;
    private JLabel subgenero;
    private JLabel ano;
    private JLabel sala;
    private JLabel estante;
    private JLabel prateleira;
    private JTextField codigoExemplar;
    private JLabel titulo;
    private int width;
    private int height;
    private Obra obra;
    public AdicionarExemplar(Obra obraUtilizacao) {
        super("Adicionar Exemplar");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        obra = obraUtilizacao;
        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        me=this;
        atualizarObra(obra);
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PublicacoesPage.showPubPage();
                me.dispose();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                me = null;
            }
        });

        criarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (codigoExemplar.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "Por favor, preencha o código desejado para o exemplar.",
                            "Código Exemplar",
                            JOptionPane.WARNING_MESSAGE);
                }
                if (obra == null){
                    JOptionPane.showMessageDialog(me,
                            "Não há nenhuma obra a ser referenciada.",
                            "Erro Obra",
                            JOptionPane.WARNING_MESSAGE);
                }
                Exemplar exemplar = new Exemplar(
                        (codigoExemplar.getText()),
                        obra
                );
                AppData.getInstance().guardarExemplar(exemplar);

                JOptionPane.showMessageDialog(me,
                        "Exemplar criado com sucesso!",
                        "Exemplar Criado",
                        JOptionPane.INFORMATION_MESSAGE);
                PublicacoesPage.showPubPage();
                me.dispose();
            }
        });
    }
    private static void atualizarObra(Obra obra) {
        // Atualizar os campos do formulário com os dados da obra
        me.titulo.setText(obra.getTitulo());
        me.edicao.setText(Integer.toString(obra.getNumeroEdicao()));
        me.isbn.setText(obra.getISBN());
        me.editora.setText(obra.getEditora().toString());
        me.distribuidor.setText(obra.getDistribuidor().toString());
        me.autor.setText(obra.getAutores().toString());
        me.genero.setText(obra.getGenero().toString());
        me.subgenero.setText(obra.getSubgenero().toString());
        me.ano.setText(String.valueOf(obra.getAno()));
        me.sala.setText(obra.getSala().toString());
        me.estante.setText(String.valueOf(obra.getEstante().toString()));
        me.prateleira.setText(String.valueOf(obra.getPrateleira().toString()));
    }
    public static void showAddExemplarPage(Obra obra) {
        if (me == null) {
            me = new AdicionarExemplar(obra);
        }
        if (!me.isVisible()) {
            me.obra = obra;
            atualizarObra(obra);
            me.setVisible(true);
        } else {
            me.obra = obra;
            atualizarObra(obra);
            me.toFront();
        }
    }
}
