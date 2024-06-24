package ObjetosProblema;

public class Exemplar {
    private String codigo;
    private Obra obra;
    private boolean disponivel;
    private Emprestimo emprestimoAtual;

    public Exemplar(String codigo, Obra obra) {
        this.codigo = codigo;
        this.obra = obra;
        this.disponivel = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
    public void setEmprestimoAtual(Emprestimo emprestimo) {
        this.emprestimoAtual = emprestimo;
        this.disponivel = (emprestimo == null);
    }

    public Emprestimo getEmprestimoAtual() {
        return emprestimoAtual;
    }

    @Override
    public String toString() {
        return "Exemplar{" +
                "codigo='" + codigo + '\'' +
                ", obra=" + obra.getTitulo() +
                ", disponivel=" + disponivel +
                '}';
    }
}