package Modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Socio implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String nifOuCC; // NIF or CC (Citizen Card) number
    private String morada;
    private String cidade;
    private String telefone;
    private String email;
    private String codigoPostal;
    private Distrito distrito;
    private LocalDate dataProximoPagamentoAnuidade;
    private LocalDate dataInscricao;
    private boolean anuidadeEmDia;
    private ArrayList<Emprestimo> emprestimosAtuais;
    private float valorEmDivida;
    private int numero;

    private PreferenciaComunicacao preferenciaComunicacao;

    public void alertarDevedorDivida() {
        /*
        * Enviado uma mensagem e/ou email ao devedor com o valor - dividas/multas
        * */
    }
    public void alertarDevedorAnualidade() {
        /*
         * Enviado uma mensagem e/ou email ao devedor com o valor - anualidade
         * */
    }

    public void setDataProximoPagamentoAnuidade(LocalDate dataProximoPagamentoAnuidade) {
        this.dataProximoPagamentoAnuidade = dataProximoPagamentoAnuidade;
    }

    public enum PreferenciaComunicacao {
        SMS, EMAIL
    }

    public Socio(String nome, String nifOuCC, String morada,Distrito distrito, String cidade,String codigoPostal, String telefone, String email, int numero) {
        this.nome = nome;
        this.nifOuCC = nifOuCC;
        this.morada = morada;
        this.distrito = distrito;
        this.cidade = cidade;
        this.codigoPostal = codigoPostal;
        this.telefone = telefone;
        this.email = email;
        this.dataProximoPagamentoAnuidade = LocalDate.now().plusYears(1);
        this.dataInscricao = LocalDate.now();
        this.anuidadeEmDia = true;
        this.emprestimosAtuais = new ArrayList<>();
        this.valorEmDivida = 0.0f;
        this.preferenciaComunicacao = PreferenciaComunicacao.EMAIL; // Default to email
        this.numero = numero;
    }

    public boolean podeRequisitarLivros(int limiteMaximo) {
        return emprestimosAtuais.size() < limiteMaximo && anuidadeEmDia && valorEmDivida == 0;
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        emprestimosAtuais.add(emprestimo);
    }

    public void removerEmprestimo(Emprestimo emprestimo) {
        emprestimosAtuais.remove(emprestimo);
    }

    public void adicionarMulta(float valor) {
        valorEmDivida += valor;
    }

    public void pagarDivida(float valor) {
        valorEmDivida = Math.max(0, valorEmDivida - valor);
    }

    public void pagarAnuidade() {
        if (dataProximoPagamentoAnuidade.isBefore(LocalDate.now())){
            dataProximoPagamentoAnuidade = LocalDate.now().plusYears(1);
        }
        else{
            dataProximoPagamentoAnuidade = dataProximoPagamentoAnuidade.plusYears(1);
        }


        anuidadeEmDia = true;
    }

    public void verificarAnuidade() {
        anuidadeEmDia = LocalDate.now().isBefore(dataProximoPagamentoAnuidade);
    }

    // Getters and setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNifOuCC() {
        return nifOuCC;
    }

    public void setNifOuCC(String nifOuCC) {
        this.nifOuCC = nifOuCC;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataProximoPagamentoAnuidade() {
        return dataProximoPagamentoAnuidade;
    }

    public boolean isAnuidadeEmDia() {
        return anuidadeEmDia;
    }

    public ArrayList<Emprestimo> getEmprestimosAtuais() {
        return new ArrayList<>(emprestimosAtuais);
    }

    public float getValorEmDivida() {
        return valorEmDivida;
    }

    public PreferenciaComunicacao getPreferenciaComunicacao() {
        return preferenciaComunicacao;
    }

    public void setPreferenciaComunicacao(PreferenciaComunicacao preferenciaComunicacao) {
        this.preferenciaComunicacao = preferenciaComunicacao;
    }


    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getNumero() {
        return numero;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    public boolean getAnuidadeEmDia() {
        return anuidadeEmDia;
    }




    @Override
    public String toString() {
        return "Socio{" +
                "nome='" + nome + '\'' +
                ", nifOuCC='" + nifOuCC + '\'' +
                ", morada='" + morada + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", número de sócio= '" + numero + '\''+
                ", anuidadeEmDia=" + anuidadeEmDia +
                ", emprestimosAtuais=" + emprestimosAtuais.size() +
                ", valorEmDivida=" + valorEmDivida +
                ", preferenciaComunicacao=" + preferenciaComunicacao +
                '}';
    }

    public void editar(Socio novo) {
        nifOuCC = novo.nifOuCC != null ? novo.nifOuCC : nifOuCC;
        morada = novo.morada != null ? novo.morada : morada;
        distrito = novo.distrito != null ? novo.distrito : distrito;
        cidade = novo.cidade != null ? novo.cidade : cidade;
        codigoPostal = novo.codigoPostal != null ? novo.codigoPostal : codigoPostal;
        telefone = novo.telefone != null ? novo.telefone : telefone;
        email = novo.email != null ? novo.email : email;
        nome = novo.nome != null ? novo.nome : nome;

    }

}