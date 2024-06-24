package Modelos;

import java.io.Serializable;
import java.time.LocalDate;

public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;

    private Obra obra;
    private Socio socio;

    private LocalDate dataInicio;

    public Reserva(Obra obra, Socio socio) {
        this.obra = obra;
        this.socio = socio;
    }

    public Obra getObra() {
        return obra;
    }

    public Socio getSocio() {
        return socio;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "obra=" + obra.getTitulo() +
                ", socio=" + socio.getNumero() +
                '}';
    }
}
