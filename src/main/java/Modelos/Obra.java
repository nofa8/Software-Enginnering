package Modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Obra implements Serializable {
    private static final long serialVersionUID = 1L;
    private String titulo;
    private List<String> autores;
    private Genero genero;
    private Subgenero subgenero;
    private Editora editora;
    private int numeroEdicao;
    private int ano;
    private Distribuidor distribuidor;
    private String ISBN;
    private Estantes estante;
    private Prateleiras prateleira;
    private Salas sala;
    private List<Exemplar> exemplares;
    public Obra(String titulo, List<String> autores, Genero genero, Subgenero subgenero,
                Editora editora, int numeroEdicao, int ano, String ISBN,
                Estantes estante, Prateleiras prateleira, Salas sala, Distribuidor distribuidor) {
        this.titulo = titulo;
        this.autores = autores;
        this.genero = genero;
        this.subgenero = subgenero;
        this.editora = editora;
        this.numeroEdicao = numeroEdicao;
        this.ano = ano;
        this.ISBN = ISBN;
        this.estante = estante;
        this.prateleira = prateleira;
        this.sala = sala;
        this.exemplares = new ArrayList<>();
        this.distribuidor = distribuidor;
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

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
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

    public Estantes getEstante() {
        return estante;
    }

    public Prateleiras getPrateleira() {
        return prateleira;
    }

    public Salas getSala() {
        return sala;
    }

    public void setEstante(Estantes estante) {
        this.estante = estante;
    }

    public void setPrateleira(Prateleiras prateleira) {
        this.prateleira = prateleira;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public List<Exemplar> getExemplares() {
        return new ArrayList<>(exemplares);
    }

    public void setExemplares(List<Exemplar> exemplares) {
        this.exemplares = exemplares;
    }

    public Distribuidor getDistribuidor() {
        return distribuidor;
    }
    public int getQuantidade(){
        return exemplares.size();

    }

    public void setDistribuidor(Distribuidor distribuidor) {
        this.distribuidor = distribuidor;
    }

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

    public void editar(Obra nova) {
        numeroEdicao = nova.numeroEdicao != 0 ? nova.numeroEdicao: numeroEdicao;
        titulo = nova.titulo != null ? nova.titulo : titulo;
        autores = nova.autores != null ? nova.autores : autores;
        genero = nova.genero  != null ? nova.genero : genero;
        subgenero = nova.subgenero != null ? nova.subgenero: subgenero;
        editora = nova.editora  != null ? nova.editora : editora;
        ano = nova.ano  != 0 ? nova.ano : ano;
        ISBN = nova.ISBN != null ? nova.ISBN: ISBN;
        estante = nova.estante  != null ? nova.estante: estante;
        prateleira = nova.prateleira  != null ? nova.prateleira: prateleira;
        sala = nova.sala   != null ? nova.sala: sala;
        distribuidor = nova.distribuidor != null ? nova.distribuidor: distribuidor;
    }

    public boolean hasAutor(String autor) {
        return autores.contains(autor);
    }
}

