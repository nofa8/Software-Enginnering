import Modelos.Obra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VisualizarPublicacao extends JFrame{
    private static VisualizarPublicacao me;
    private JPanel mainPanel;
    private JButton voltarButton;
    private JButton editarButton;
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
    private JLabel quantidade;
    private JLabel titulo;
    private int width;
    private int height;
    public VisualizarPublicacao(Obra obra) {
        super("Visualizar Obra");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        atualizarObra(obra);
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                me.show(false);
                PublicacoesPage.showPubPage();
                me.dispose();
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                me.show(false);
                EditarPublicacao.showCriarPubPage(obra);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                me = null;
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
        me.quantidade.setText(String.valueOf(obra.getQuantidade()));
    }
    public static void showVisPubPage(Obra obra) {
        if (me == null) {
            me = new VisualizarPublicacao(obra);
        }
        if (!me.isVisible()) {
            atualizarObra(obra);
            me.setVisible(true);
        } else {
            atualizarObra(obra);
            me.toFront();
        }
    }
}
