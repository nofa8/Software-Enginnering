package Modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

////para testes
//public void setDataDevolucaoPrevista(LocalDate localDate) {
//    dataDevolucaoPrevista = localDate;
//}
public class EmprestimoTest {

    private Socio socio;
    private Obra obra;
    private Exemplar exemplar;
    private Emprestimo emprestimo;

    @BeforeEach
    public void setUp() {
        socio = new Socio("Luis Oliveirao", "123456789", "Olha o buraco", Distrito.LISBOA, "Lisboa", "1234-567", "123456789", "messi@perde.com", 1);
        obra = new Obra(
                "Os viajantes de costas",
                List.of("Luis", "Afonso"),
                Genero.ROMANCE,
                Subgenero.AVENTURA_EPICA,
                Editora.HACHETTE_BOOK_GROUP,
                1,
                2023,
                "978-3-16-148410-0",
                Estantes.ESTANTE_1A,
                Prateleiras.PRATELEIRA_4,
                Salas.SALA_101,
                Distribuidor.ALMEDINA
        );
        exemplar = new Exemplar("123", obra);
        emprestimo = new Emprestimo(exemplar, socio, 14);
        exemplar.setEmprestimoAtual(emprestimo);
    }

    @Test
    public void testSetEmprestimoAtual() {
        assertFalse(exemplar.isDisponivel());
        assertEquals(emprestimo, exemplar.getEmprestimoAtual());

        exemplar.setEmprestimoAtual(null);

        assertTrue(exemplar.isDisponivel());
        assertNull(exemplar.getEmprestimoAtual());
    }

    @Test
    public void testEstaAtrasado() {
        assertFalse(emprestimo.estaAtrasado());

        emprestimo.setDataDevolucaoPrevista(LocalDate.now().minusDays(1));
        assertTrue(emprestimo.estaAtrasado());

        emprestimo.realizarDevolucao(0.5f);
        assertFalse(emprestimo.estaAtrasado());
    }

    @Test
    public void testGetDiasAtraso() {
        assertEquals(0, emprestimo.getDiasAtraso());

        emprestimo.setDataDevolucaoPrevista(LocalDate.now().minusDays(1));
        assertEquals(1, emprestimo.getDiasAtraso());

        emprestimo.realizarDevolucao(0.5f);
        assertEquals(0, emprestimo.getDiasAtraso());
    }



    @Test
    public void testRealizarDevolucao() {
        emprestimo.setDataDevolucaoPrevista(LocalDate.now().minusDays(1));

        assertTrue(emprestimo.estaAtrasado());
        emprestimo.realizarDevolucao(0.5f);

        assertNotNull(emprestimo.getDataDevolucaoEfetiva());
        assertFalse(emprestimo.estaAtrasado());

        assertNotEquals(0.0f, emprestimo.getMulta());
        assertTrue(exemplar.isDisponivel());
        assertEquals(0, socio.getEmprestimosAtuais().size());
    }



    @Test
    public void testToString() {
        String expectedString = "Emprestimo{" +
                "exemplar=" + exemplar.getCodigo() +
                ", socio=" + socio.getNome() +
                ", dataEmprestimo=" + emprestimo.getDataEmprestimo() +
                ", dataDevolucaoPrevista=" + emprestimo.getDataDevolucaoPrevista() +
                ", dataDevolucaoEfetiva=null" +
                ", multa=0.0" +
                '}';
        assertEquals(expectedString, emprestimo.toString());
    }
}
