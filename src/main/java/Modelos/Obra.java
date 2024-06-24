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

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Subgenero getSubgenero() {
        return subgenero;
    }

    public void setSubgenero(Subgenero subgenero) {
        this.subgenero = subgenero;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getNumeroEdicao() {
        return numeroEdicao;
    }

    public void setNumeroEdicao(int numeroEdicao) {
        this.numeroEdicao = numeroEdicao;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public String getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(String prateleira) {
        this.prateleira = prateleira;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    public void setExemplares(List<Exemplar> exemplares) {
        this.exemplares = exemplares;
    }

    // Add other getters and setters as needed




    /*
    public void getQuantExemplares(){
        return
    }*/

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

