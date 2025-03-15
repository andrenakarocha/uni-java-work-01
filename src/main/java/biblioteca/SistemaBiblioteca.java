package biblioteca;

import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Membro;
import java.io.*;
import java.util.*;

public class SistemaBiblioteca {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        Livro livro1 = new Livro("Livro A", "Autor A", "12345");
        Livro livro2 = new Livro("Livro B", "Autor B", "67890");

        Membro membro1 = new Membro("Membro A", 1, "emailA@fiap.com.br");
        Membro membro2 = new Membro("Membro B", 2, "emailB@fiap.com.br");

        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);

        biblioteca.registrarMembro(membro1);
        biblioteca.registrarMembro(membro2);

        biblioteca.registrarEmprestimo(livro1, membro1);

        try {
            biblioteca.salvarDadosEmArquivo("biblioteca.txt");
            biblioteca.carregarDadosDeArquivo("biblioteca.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        biblioteca.devolverLivro(new Emprestimo(livro1, membro1, new Date()));
        biblioteca.removerLivro(livro2);
    }
}