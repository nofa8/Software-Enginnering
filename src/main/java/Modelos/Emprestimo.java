package Modelos;
import java.io.Serializable;
import java.time.LocalDate;


public class Emprestimo  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Exemplar exemplar;
    private Socio socio;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoEfetiva;
    private float multa;

    public Emprestimo(Exemplar exemplar, Socio socio, int duracaoEmprestimoDias) {
        this.exemplar = exemplar;
        this.socio = socio;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(duracaoEmprestimoDias);
        this.dataDevolucaoEfetiva = null;
        this.multa = 0.0f;
    }

    public void realizarDevolucao(float multaDiaria) {
        this.dataDevolucaoEfetiva = LocalDate.now();
        if (dataDevolucaoEfetiva.isAfter(dataDevolucaoPrevista)) {
            long diasAtraso = dataDevolucaoEfetiva.toEpochDay() - dataDevolucaoPrevista.toEpochDay();
            this.multa = diasAtraso * multaDiaria;
            socio.adicionarMulta(this.multa);
        }
        exemplar.setDisponivel(true);
        socio.removerEmprestimo(this);
    }

    public boolean estaAtrasado() {
        return LocalDate.now().isAfter(dataDevolucaoPrevista) && dataDevolucaoEfetiva == null;
    }

    public long getDiasAtraso() {
        if (!estaAtrasado()) {
            return 0;
        }
        return LocalDate.now().toEpochDay() - dataDevolucaoPrevista.toEpochDay();
    }

    // Getters and setters

    public Exemplar getExemplar() {
        return exemplar;
    }

    public Socio getSocio() {
        return socio;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoEfetiva() {
        return dataDevolucaoEfetiva;
    }

    public float getMulta() {
        return multa;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "exemplar=" + exemplar.getCodigo() +
                ", socio=" + socio.getNome() +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucaoPrevista=" + dataDevolucaoPrevista +
                ", dataDevolucaoEfetiva=" + dataDevolucaoEfetiva +
                ", multa=" + multa +
                '}';
    }

    public void setDataDevolucaoPrevista(LocalDate localDate) {
        dataDevolucaoPrevista = localDate;
    }
}