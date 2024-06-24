import Modelos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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




                ArrayList<String> listaut = new ArrayList<>();
                Genero gener = Genero.valueOf(gen);
                Subgenero subgener = Subgenero.valueOf(subgen);
                int numEdicao = Integer.parseInt(ned);
                Estantes estan = Estantes.valueOf(est);
                Editora edit = Editora.valueOf(edi);
                Prateleiras prat = Prateleiras.valueOf(pra);
                Salas salars = Salas.valueOf(sal);
                int anoo = Integer.parseInt(an);
                Distribuidor distribuidor = Distribuidor.valueOf(dis);
                Obra obra = new Obra(tit,listaut, gener,subgener, edit,numEdicao,anoo,is,estan,prat,salars,distribuidor);


//                String titulo,
//                List<String> autores,
//                Genero genero,
//                Subgenero subgenero,
//                String editora,
//                int numeroEdicao,
//                int ano,
//                String ISBN,
//                Estantes estante,
//                Prateleiras prateleira,
//                Salas sala
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
