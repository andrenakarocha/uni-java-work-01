package biblioteca;

import org.junit.jupiter.api.Test;
import biblioteca.modelo.Livro;

import static org.junit.jupiter.api.Assertions.*;

public class LivroTest {

    @Test
    public void testConstrutor() {
        Livro livro = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "978-85-7657-424-3");

        assertEquals("O Senhor dos Anéis", livro.getTitulo());
        assertEquals("J.R.R. Tolkien", livro.getAutor());
        assertEquals("978-85-7657-424-3", livro.getISBN());
    }

    @Test
    public void testToString() {
        Livro livro = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "978-85-7657-424-3");
        String expected = "Livro{titulo='O Senhor dos Anéis', autor='J.R.R. Tolkien', ISBN='978-85-7657-424-3'}";

        assertEquals(expected, livro.toString());
    }
}