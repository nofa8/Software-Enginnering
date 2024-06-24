package Modelos;

import java.time.LocalDate;

public class Reserva {
    private Obra obra;
    private Socio socio;

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


}
