import Modelos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;

public class CriarPublicacao extends JFrame{
    private static CriarPublicacao me;
    private JPanel mainPanel;
    private JButton voltarButton;
    private JButton criarButton;
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
    public CriarPublicacao() {
        super("Criar Publicação");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(size.getWidth()/2);
        height = (int)(size.getHeight()/2);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        me = this;

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
//        distribuidor = new JComboBox(Distribuidor.values());
//        sala = new JComboBox(Salas.values());
//        estante = new JComboBox(Estantes.values());
//        prateleira = new JComboBox(Prateleiras.values());
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                me.show(false);
                me.dispose();
            }
        });
        criarButton.addActionListener(new ActionListener() {
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
                            "O numero de edição tem que ser um número inteiro positivo!\n");
                    return;
                }

                int anoo;
                try{
                    anoo = Integer.parseInt(an);
                }catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(me,
                            "O numero de edição tem que ser um número inteiro positivo!\n");
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
                AppData.getInstance().adicionarObra(obra);
                JOptionPane.showMessageDialog(null,
                        "Obra criada com sucesso!\n");
                PublicacoesPage.showPubPage();
                me.dispose();
            }
        });
    }

    public static void showCriarPubPage() {
        if (me == null) {
            me = new CriarPublicacao();
        }
        if (!me.isVisible()) {
            me.setVisible(true);
        } else {
            me.toFront();
        }
    }
}
