package Modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PublicacaoTest {
    private Obra obra;
    private List<String> autores;

    @BeforeEach
    public void setUp() {
        autores = new ArrayList<>();
        autores.add("Afonso");
        autores.add("Duarte");

        obra = new Obra(
                "Boas tarde malta",
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
    }

    @Test
    public void testCreateObra() {
        assertEquals("Boas tarde malta", obra.getTitulo());
        assertEquals(autores, obra.getAutores());
        assertEquals(Genero.ROMANCE, obra.getGenero());
        assertEquals(Subgenero.AVENTURA_EPICA, obra.getSubgenero());
        assertEquals(Editora.HACHETTE_BOOK_GROUP, obra.getEditora());
        assertEquals(1, obra.getNumeroEdicao());
        assertEquals(2023, obra.getAno());
        assertEquals("978-3-16-148410-0", obra.getISBN());
        assertEquals(Estantes.ESTANTE_1A, obra.getEstante());
        assertEquals(Prateleiras.PRATELEIRA_4, obra.getPrateleira());
        assertEquals(Salas.SALA_101, obra.getSala());
        assertEquals(Distribuidor.ALMEDINA, obra.getDistribuidor());
        assertEquals(0, obra.getQuantidade());
    }

    @Test
    public void testAdicionarExemplar() {
        Exemplar exemplar = new Exemplar("123", obra);
        obra.adicionarExemplar(exemplar);
        assertEquals(1, obra.getQuantidade());
        assertTrue(obra.getExemplares().contains(exemplar));
    }

    @Test
    public void testEditarObra() {
        Obra novaObra = new Obra(
                "Agora Adeus",
                autores,
                Genero.FANTASIA,
                Subgenero.CULINARIA_REGIONAL,
                Editora.HARPERCOLLINS,
                2,
                2024,
                "978-3-16-148411-0",
                Estantes.ESTANTE_2B,
                Prateleiras.PRATELEIRA_5,
                Salas.SALA_102,
                Distribuidor.BERTRAND
        );

        obra.editar(novaObra);

        assertEquals("Agora Adeus", obra.getTitulo());
        assertEquals(Genero.FANTASIA, obra.getGenero());
        assertEquals(Subgenero.CULINARIA_REGIONAL, obra.getSubgenero());
        assertEquals(Editora.HARPERCOLLINS, obra.getEditora());
        assertEquals(2, obra.getNumeroEdicao());
        assertEquals(2024, obra.getAno());
        assertEquals("978-3-16-148411-0", obra.getISBN());
        assertEquals(Estantes.ESTANTE_2B, obra.getEstante());
        assertEquals(Prateleiras.PRATELEIRA_5, obra.getPrateleira());
        assertEquals(Salas.SALA_102, obra.getSala());
        assertEquals(Distribuidor.BERTRAND, obra.getDistribuidor());
    }

    @Test
    public void testEliminarExemplar() {
        Exemplar exemplar1 = new Exemplar("123", obra);
        Exemplar exemplar2 = new Exemplar("456", obra);
        obra.adicionarExemplar(exemplar1);
        obra.adicionarExemplar(exemplar2);

        assertEquals(2, obra.getQuantidade());

        int resultado = obra.eliminarExemplar("123");
        assertEquals(0, resultado);
        assertEquals(1, obra.getQuantidade());

        resultado = obra.eliminarExemplar("456");
        assertEquals(0, resultado);
        assertEquals(0, obra.getQuantidade());
    }

    @Test
    public void testHasAutor() {
        assertTrue(obra.hasAutor("Duarte"));
        assertFalse(obra.hasAutor("Messi"));
    }
}
