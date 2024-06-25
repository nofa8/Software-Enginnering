import Modelos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class EditarPublicacao extends JFrame{
    private static EditarPublicacao me;
    private JPanel mainPanel;
    private JButton voltarButton;
    private JButton editarButton;
    private JLabel quantidade;
    private JTextField nedicao;
    private JTextField isbn;
    private JTextField autor;
    private JComboBox genero;
    private JComboBox subgenero;
    private JTextField ano;
    private JComboBox editora;
    private JComboBox distribuidor;
    private JComboBox sala;
    private JComboBox estante;
    private JComboBox prateleira;
    private JTextField tituloTextField;
    private int width;
    private int height;
    private Obra antiga;
    public EditarPublicacao(Obra obra) {
        super("Editar Publicação");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        EnumSet.allOf(Genero.class)
                .forEach(gene -> genero.addItem(gene));

        EnumSet.allOf(Subgenero.class)
                .forEach(gene -> subgenero.addItem(gene));
        EnumSet.allOf(Editora.class)
                .forEach(gene -> editora.addItem(gene));
        EnumSet.allOf(Distribuidor.class)
                .forEach(gene -> distribuidor.addItem(gene));
        EnumSet.allOf(Salas.class)
                .forEach(gene -> sala.addItem(gene));
        EnumSet.allOf(Estantes.class)
                .forEach(gene -> estante.addItem(gene));
        EnumSet.allOf(Prateleiras.class)
                .forEach(gene -> prateleira.addItem(gene));

        antiga = obra;

        atualizarObra(obra);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                me = null;
            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                me.show(false);
                me.dispose();
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (nedicao.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "Número de edição tem que ser um preenchido!\n");
                    return;
                }
                if (isbn.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "ISBN tem que ser um preenchido!\n");
                    return;
                }
                if (autor.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "Autor tem que ser um preenchido!\n");
                    return;
                }
                if (ano.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "Ano tem que ser um preenchido!\n");

                    return;
                }
                if (tituloTextField.getText() == null){
                    JOptionPane.showMessageDialog(me,
                            "Título tem que ser um preenchido!\n");
                    return;
                }


                String ned = nedicao.getText();
                String is = isbn.getText();
                String aut = autor.getText();
                String an = ano.getText();
                String tit = tituloTextField.getText();

                String gen ;
                String subgen;
                String edi ;
                String dis;
                String sal;
                String est;
                String pra ;
                try{
                    gen = Objects.requireNonNull(genero.getSelectedItem()).toString();
                    subgen = Objects.requireNonNull(subgenero.getSelectedItem()).toString();
                    edi = Objects.requireNonNull(editora.getSelectedItem()).toString();
                    dis = Objects.requireNonNull(distribuidor.getSelectedItem()).toString();
                    sal = Objects.requireNonNull(sala.getSelectedItem()).toString();
                    est = Objects.requireNonNull(estante.getSelectedItem()).toString();
                    pra = Objects.requireNonNull(prateleira.getSelectedItem()).toString();

                }catch (NullPointerException exception){
                    JOptionPane.showMessageDialog(me,
                            "Os campos têm de ser todos preenchidos!\n");
                    return;
                }


                String[] autSplit = aut.split(",");
                ArrayList<String> listaut = new ArrayList<String>(Arrays.asList(autSplit));

                int numEdicao;
                try{
                    numEdicao = Integer.parseInt(ned);
                }catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(me,
                            "O numero de edição tem que ser um número inteiro positivo e não superior a 2147483647!\n");
                    return;
                }

                int anoo;
                try{
                    anoo = Integer.parseInt(an);
                }catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(me,
                            "O ano tem que ser um número inteiro positivo!\n");
                    return;
                }

                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                int hoje = Integer.parseInt(yearFormat.format(new Date()));
                if (anoo < 1960 || anoo > hoje){
                    JOptionPane.showMessageDialog(me,
                            "Ano inválido "+1960+"-"+hoje+"!\n");
                    return;
                }

                Genero gener;
                Subgenero subgener;
                Estantes estan;
                Editora edit;
                Prateleiras prat ;
                Salas salars ;
                Distribuidor distribuidor;
                try{
                    gener = Genero.valueOf(gen);
                    salars = Salas.valueOf(sal);
                    prat = Prateleiras.valueOf(pra);
                    edit = Editora.valueOf(edi);
                    estan = Estantes.valueOf(est);
                    subgener = Subgenero.valueOf(subgen);
                    distribuidor = Distribuidor.valueOf(dis);
                }catch (IllegalArgumentException exception){
                    JOptionPane.showMessageDialog(me,
                            "Ocurreu um erro com os dropdowns!\n");
                    return;
                }

                Obra obra = new Obra(tit,listaut, gener,subgener, edit,numEdicao,anoo,is,estan,prat,salars,distribuidor);
                AppData.getInstance().editarObra(obra,antiga);
                JOptionPane.showMessageDialog(null,
                        "Obra editada com sucesso!\n");
                VisualizarPublicacao.showVisPubPage(obra);
                me.dispose();
            }
        });
    }

    private void atualizarObra(Obra obra) {
        // Atualizar os campos do formulário com os dados da obra
        nedicao.setText(Integer.toString(obra.getNumeroEdicao()));
        tituloTextField.setText(obra.getTitulo());
        isbn.setText(obra.getISBN());
        autor.setText(obra.getAutores().toString());
        ano.setText(String.valueOf(obra.getAno()));
        quantidade.setText(String.valueOf(obra.getQuantidade()));
        // Set initial values for combo boxes
        editora.setSelectedItem(obra.getEditora());
        distribuidor.setSelectedItem(obra.getDistribuidor());
        genero.setSelectedItem(obra.getGenero());
        subgenero.setSelectedItem(obra.getSubgenero());
        sala.setSelectedItem(obra.getSala());
        estante.setSelectedItem(obra.getEstante());
        prateleira.setSelectedItem(obra.getPrateleira());
    }

    public static void showCriarPubPage(Obra obra) {
        if (me == null) {
            me = new EditarPublicacao(obra);
        }
        if (!me.isVisible()) {
            me.setVisible(true);
        } else {
            me.toFront();
        }
    }
}
