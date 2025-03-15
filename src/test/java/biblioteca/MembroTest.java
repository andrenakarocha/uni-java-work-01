package biblioteca;

import org.junit.jupiter.api.Test;
import biblioteca.modelo.Membro;

import static org.junit.jupiter.api.Assertions.*;

public class MembroTest {

    @Test
    public void testConstrutor() {
        Membro membro = new Membro("Ana Silva", 123, "ana@fiap.com.br");

        assertEquals("Ana Silva", membro.getNome());
        assertEquals(123, membro.getId());
        assertEquals("ana@fiap.com.br", membro.getEmail());
    }

    @Test
    public void testToString() {
        Membro membro = new Membro("Ana Silva", 123, "ana@fiap.com.br");
        String expected = "Membro{nome='Ana Silva', id=123, email='ana@fiap.com.br'}";

        assertEquals(expected, membro.toString());
    }
}