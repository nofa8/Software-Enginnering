package Modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Obra implements Serializable {
    private static final long serialVersionUID = 1L;
    private String titulo;
    private List<String> autores;
    private Genero genero;
    private Subgenero subgenero;
    private String editora;
    private int numeroEdicao;
    private int ano;
    private String ISBN;
    private Estantes estante;
    private Prateleiras prateleira;
    private Salas sala;
    private List<Exemplar> exemplares;

    public Obra(String titulo, List<String> autores, Genero genero, Subgenero subgenero,
                String editora, int numeroEdicao, int ano, String ISBN,
                Estantes estante, Prateleiras prateleira, Salas sala) {
        this.titulo = titulo;
        this.autores = autores;
        this.genero = genero;
        this.subgenero = subgenero;
        this.editora = editora;
        this.numeroEdicao = numeroEdicao;
        this.ano = ano;
        this.ISBN = ISBN;
        this.sala = sala;
        this.estante = estante;
        this.prateleira = prateleira;
        this.exemplares = new ArrayList<>();
    }

    public void adicionarExemplar(Exemplar exemplar) {
        exemplares.add(exemplar);
    }

    // Getters and setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getAutores() {
        return autores;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }

    // Add other getters and setters as needed

    @Override
    public String toString() {
        return "Obra{" +
                "titulo='" + titulo + '\'' +
                ", autores=" + autores +
                ", genero='" + genero + '\'' +
                ", subgenero='" + subgenero + '\'' +
                ", editora='" + editora + '\'' +
                ", numeroEdicao=" + numeroEdicao +
                ", ano=" + ano +
                ", ISBN='" + ISBN + '\'' +
                ", estante='" + estante + '\'' +
                ", prateleira='" + prateleira + '\'' +
                ", numeroDeExemplares=" + exemplares.size() +
                '}';
    }
}

