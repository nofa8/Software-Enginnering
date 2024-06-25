package Modelos;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SocioTest {

    private Socio socio;

    @BeforeEach
    public void setUp() {
        socio = new Socio("Joaozinho Laranja", "123456789", "Rua IPL", Distrito.LISBOA, "Lisboa", "1234-567", "123456789", "cr7@gmail.com", 1);
    }

    @AfterEach
    public void tearDown() {
        socio = null;
    }

    @Test
    public void testCreateSocio() {
        assertEquals("Joaozinho Laranja", socio.getNome());
        assertEquals("123456789", socio.getNifOuCC());
        assertEquals("Rua IPL", socio.getMorada());
        assertEquals("Lisboa", socio.getCidade());
        assertEquals("1234-567", socio.getCodigoPostal());
        assertEquals("123456789", socio.getTelefone());
        assertEquals("cr7@gmail.com", socio.getEmail());
        assertEquals(Distrito.LISBOA, socio.getDistrito());
        assertEquals(1, socio.getNumero());
        assertTrue(socio.getAnuidadeEmDia());
        assertEquals(0.0f, socio.getValorEmDivida());
        assertEquals(LocalDate.now().plusYears(1), socio.getDataProximoPagamentoAnuidade());
        assertEquals(0, socio.getEmprestimosAtuais().size());
        assertEquals(Socio.PreferenciaComunicacao.EMAIL, socio.getPreferenciaComunicacao());
    }

    @Test
    public void testAdicionarRemoverEmprestimo() {
        List<String> autores = new ArrayList<>();
        autores.add("Luis");
        autores.add("Afonso");
        Obra obra = new Obra(
                "Título da Obra",
                autores,
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
        Exemplar exemplar = new Exemplar("123", obra);
        Emprestimo emprestimo = new Emprestimo(exemplar, socio, 14);

        socio.adicionarEmprestimo(emprestimo);
        assertEquals(1, socio.getEmprestimosAtuais().size());
        assertTrue(socio.getEmprestimosAtuais().contains(emprestimo));

        socio.removerEmprestimo(emprestimo);
        assertEquals(0, socio.getEmprestimosAtuais().size());
        assertFalse(socio.getEmprestimosAtuais().contains(emprestimo));
    }

    @Test
    public void testVerificarAnuidade() {
        socio.verificarAnuidade();
        assertTrue(socio.getAnuidadeEmDia());

        socio.pagarAnuidade();
        assertTrue(socio.getAnuidadeEmDia());

        socio.setDataProximoPagamentoAnuidade(LocalDate.now().minusDays(1));
        socio.verificarAnuidade();
        assertFalse(socio.getAnuidadeEmDia());
    }

    @Test
    public void testPagarDivida() {
        socio.adicionarMulta(50.0f);
        assertEquals(50.0f, socio.getValorEmDivida());

        socio.pagarDivida(20.0f);
        assertEquals(30.0f, socio.getValorEmDivida());

        socio.pagarDivida(40.0f);
        assertEquals(0.0f, socio.getValorEmDivida());
    }

    @Test
    public void testEditarSocio() {
        Socio novoSocio = new Socio("Jane Doe", "987654321", "456 Elm St", Distrito.PORTO, "Porto", "7654-321", "987654321", "jane@example.com", 2);

        socio.editar(novoSocio);

        assertEquals("Jane Doe", socio.getNome());
        assertEquals("987654321", socio.getNifOuCC());
        assertEquals("456 Elm St", socio.getMorada());
        assertEquals(Distrito.PORTO, socio.getDistrito());
        assertEquals("Porto", socio.getCidade());
        assertEquals("7654-321", socio.getCodigoPostal());
        assertEquals("987654321", socio.getTelefone());
        assertEquals("jane@example.com", socio.getEmail());
    }
}