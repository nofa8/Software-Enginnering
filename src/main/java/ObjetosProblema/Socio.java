package ObjetosProblema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Socio  {

    private String nome;
    private String nifOuCC; // NIF or CC (Citizen Card) number
    private String morada;
    private String telefone;
    private String email;
    private LocalDate dataUltimaPagamentoAnuidade;
    private boolean anuidadeEmDia;
    private ArrayList<Emprestimo> emprestimosAtuais;
    private float valorEmDivida;

    private PreferenciaComunicacao preferenciaComunicacao;

    public enum PreferenciaComunicacao {
        SMS, EMAIL
    }

    public Socio(String nome, String nifOuCC, String morada, String telefone, String email) {
        this.nome = nome;
        this.nifOuCC = nifOuCC;
        this.morada = morada;
        this.telefone = telefone;
        this.email = email;
        this.dataUltimaPagamentoAnuidade = LocalDate.now();
        this.anuidadeEmDia = true;
        this.emprestimosAtuais = new ArrayList<>();
        this.valorEmDivida = 0.0f;
        this.preferenciaComunicacao = PreferenciaComunicacao.EMAIL; // Default to email
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
        dataUltimaPagamentoAnuidade = LocalDate.now();
        anuidadeEmDia = true;
    }

    public void verificarAnuidade() {
        anuidadeEmDia = LocalDate.now().isBefore(dataUltimaPagamentoAnuidade.plusYears(1));
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

    public LocalDate getDataUltimaPagamentoAnuidade() {
        return dataUltimaPagamentoAnuidade;
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

    @Override
    public String toString() {
        return "Socio{" +
                "nome='" + nome + '\'' +
                ", nifOuCC='" + nifOuCC + '\'' +
                ", morada='" + morada + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", anuidadeEmDia=" + anuidadeEmDia +
                ", emprestimosAtuais=" + emprestimosAtuais.size() +
                ", valorEmDivida=" + valorEmDivida +
                ", preferenciaComunicacao=" + preferenciaComunicacao +
                '}';
    }
}