package biblioteca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Membro;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class EmprestimoTest {

    private Livro livro;
    private Membro membro;
    private Date dataEmprestimo;
    private Emprestimo emprestimo;

    @BeforeEach
    public void setUp() {
        livro = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "978-85-7657-424-3");
        membro = new Membro("Ana Silva", 123, "ana@fiap.com.br");
        dataEmprestimo = new Date();
        emprestimo = new Emprestimo(livro, membro, dataEmprestimo);
    }

    @Test
    public void testConstrutor() {
        assertEquals(livro, emprestimo.getLivro());
        assertEquals(membro, emprestimo.getMembro());
        assertEquals(dataEmprestimo, emprestimo.getDataEmprestimo());
    }

    @Test
    public void testToString() {
        String expected = "Emprestimo{livro=" + livro + ", membro=" + membro + ", dataEmprestimo=" + dataEmprestimo + '}';
        assertEquals(expected, emprestimo.toString());
    }
}