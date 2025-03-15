package biblioteca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import biblioteca.Biblioteca;
import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Membro;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

public class BibliotecaTest {

    private Biblioteca biblioteca;
    private Livro livro1;
    private Livro livro2;
    private Membro membro1;
    private Membro membro2;
    private Emprestimo emprestimo;

    @BeforeEach
    public void setUp() {
        biblioteca = new Biblioteca();
        livro1 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "978-85-7657-424-3");
        livro2 = new Livro("Harry Potter", "J.K. Rowling", "978-85-325-2765-5");
        membro1 = new Membro("Ana Silva", 123, "ana@fiap.com.br");
        membro2 = new Membro("João Oliveira", 456, "joao@fiap.com.br");
    }

    @Test
    public void testAdicionarLivro() {
        biblioteca.adicionarLivro(livro1);

        List<Livro> livros = biblioteca.getLivros();
        assertEquals(1, livros.size());
        assertEquals(livro1, livros.get(0));
    }

    @Test
    public void testRemoverLivro() {
        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);

        assertEquals(2, biblioteca.getLivros().size());

        biblioteca.removerLivro(livro1);

        assertEquals(1, biblioteca.getLivros().size());
        assertEquals(livro2, biblioteca.getLivros().get(0));
    }

    @Test
    public void testRegistrarMembro() {
        biblioteca.registrarMembro(membro1);

        List<Membro> membros = biblioteca.getMembros();
        assertEquals(1, membros.size());
        assertEquals(membro1, membros.get(0));
    }

    @Test
    public void testRegistrarEmprestimo() {
        biblioteca.adicionarLivro(livro1);
        biblioteca.registrarMembro(membro1);

        biblioteca.registrarEmprestimo(livro1, membro1);

        List<Emprestimo> emprestimos = biblioteca.getEmprestimos();
        assertEquals(1, emprestimos.size());
        assertEquals(livro1, emprestimos.get(0).getLivro());
        assertEquals(membro1, emprestimos.get(0).getMembro());
    }

    @Test
    public void testDevolverLivro() {
        biblioteca.adicionarLivro(livro1);
        biblioteca.registrarMembro(membro1);

        biblioteca.registrarEmprestimo(livro1, membro1);
        assertEquals(1, biblioteca.getEmprestimos().size());

        Emprestimo emprestimo = biblioteca.getEmprestimos().get(0);
        biblioteca.devolverLivro(emprestimo);

        assertEquals(0, biblioteca.getEmprestimos().size());
    }

    @Test
    public void testSalvarECarregarDados(@TempDir Path tempDir) throws IOException {
        // Adicionar livros e membros
        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);
        biblioteca.registrarMembro(membro1);
        biblioteca.registrarMembro(membro2);

        // Registrar empréstimo
        biblioteca.registrarEmprestimo(livro1, membro1);

        // Salvar dados em arquivo temporário
        File tempFile = tempDir.resolve("biblioteca_test.txt").toFile();
        biblioteca.salvarDadosEmArquivo(tempFile.getPath());

        // Verificar se o arquivo foi criado
        assertTrue(tempFile.exists());

        // Testar o carregamento dos dados numa nova instância da biblioteca
        Biblioteca outraBiblioteca = new Biblioteca();
        outraBiblioteca.carregarDadosDeArquivo(tempFile.getPath());

        // Verificar se os livros foram carregados corretamente
        assertEquals(2, outraBiblioteca.getLivros().size(), "Deveria ter carregado 2 livros");
        assertTrue(outraBiblioteca.getLivros().stream()
                        .anyMatch(l -> l.getTitulo().equals(livro1.getTitulo()) &&
                                l.getAutor().equals(livro1.getAutor()) &&
                                l.getISBN().equals(livro1.getISBN())),
                "O livro1 não foi carregado corretamente");
        assertTrue(outraBiblioteca.getLivros().stream()
                        .anyMatch(l -> l.getTitulo().equals(livro2.getTitulo()) &&
                                l.getAutor().equals(livro2.getAutor()) &&
                                l.getISBN().equals(livro2.getISBN())),
                "O livro2 não foi carregado corretamente");

        // Verificar se os membros foram carregados corretamente
        assertEquals(2, outraBiblioteca.getMembros().size(), "Deveria ter carregado 2 membros");
        assertTrue(outraBiblioteca.getMembros().stream()
                        .anyMatch(m -> m.getNome().equals(membro1.getNome()) &&
                                m.getId() == membro1.getId() &&
                                m.getEmail().equals(membro1.getEmail())),
                "O membro1 não foi carregado corretamente");
        assertTrue(outraBiblioteca.getMembros().stream()
                        .anyMatch(m -> m.getNome().equals(membro2.getNome()) &&
                                m.getId() == membro2.getId() &&
                                m.getEmail().equals(membro2.getEmail())),
                "O membro2 não foi carregado corretamente");

        // Verificar se os empréstimos foram carregados corretamente
        assertEquals(1, outraBiblioteca.getEmprestimos().size(), "Deveria ter carregado 1 empréstimo");

        // Como os objetos são diferentes instâncias, precisamos comparar por propriedades relevantes
        Emprestimo emprestimo = outraBiblioteca.getEmprestimos().get(0);
        assertEquals(livro1.getISBN(), emprestimo.getLivro().getISBN(), "O ISBN do livro no empréstimo deve corresponder");
        assertEquals(membro1.getId(), emprestimo.getMembro().getId(), "O ID do membro no empréstimo deve corresponder");

        // Verificar o conteúdo do arquivo
        int linhasLivro = 0;
        int linhasMembro = 0;
        int linhasEmprestimo = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("LIVRO:")) linhasLivro++;
                else if (linha.startsWith("MEMBRO:")) linhasMembro++;
                else if (linha.startsWith("EMPRESTIMO:")) linhasEmprestimo++;
            }
        }

        assertEquals(2, linhasLivro, "Deveria ter 2 linhas de livros");
        assertEquals(2, linhasMembro, "Deveria ter 2 linhas de membros");
        assertEquals(1, linhasEmprestimo, "Deveria ter 1 linha de empréstimo");
    }
}