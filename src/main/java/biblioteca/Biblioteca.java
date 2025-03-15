package biblioteca;

import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Membro;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Biblioteca {
    private List<Livro> livros = new ArrayList<>();
    private List<Membro> membros = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

    public List<Livro> getLivros() {
        return livros;
    }

    public List<Membro> getMembros() {
        return membros;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
        System.out.println("Livro adicionado: " + livro);
    }

    public void removerLivro(Livro livro) {
        livros.remove(livro);
        System.out.println("Livro removido: " + livro);
    }

    public void registrarMembro(Membro membro) {
        membros.add(membro);
        System.out.println("Membro registrado: " + membro);
    }

    public void registrarEmprestimo(Livro livro, Membro membro) {
        Emprestimo emprestimo = new Emprestimo(livro, membro, new Date());
        emprestimos.add(emprestimo);
        System.out.println("Emprestimo registrado: " + emprestimo);
    }

    public void devolverLivro(Emprestimo emprestimo) {
        emprestimos.remove(emprestimo);
        System.out.println("Livro devolvido: " + emprestimo);
    }

    public void salvarDadosEmArquivo(String nomeArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            // Salvar livros
            for (Livro livro : livros) {
                writer.write("LIVRO:" + livro.getTitulo() + ";" + livro.getAutor() + ";" + livro.getISBN() + "\n");
            }

            // Salvar membros
            for (Membro membro : membros) {
                writer.write("MEMBRO:" + membro.getNome() + ";" + membro.getId() + ";" + membro.getEmail() + "\n");
            }

            // Salvar empréstimos
            for (Emprestimo emprestimo : emprestimos) {
                writer.write("EMPRESTIMO:" +
                        emprestimo.getLivro().getISBN() + ";" +
                        emprestimo.getMembro().getId() + ";" +
                        emprestimo.getDataEmprestimo() + "\n");
            }
        }
    }

    public void carregarDadosDeArquivo(String nomeArquivo) throws IOException {
        // Limpar as listas atuais
        livros.clear();
        membros.clear();
        emprestimos.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("LIVRO:")) {
                    processarLinhaLivro(linha.substring(6));
                } else if (linha.startsWith("MEMBRO:")) {
                    processarLinhaMembro(linha.substring(7));
                } else if (linha.startsWith("EMPRESTIMO:")) {
                    processarLinhaEmprestimo(linha.substring(11));
                }
            }
        }

        System.out.println("Dados carregados: " + livros.size() + " livros, " +
                membros.size() + " membros, " + emprestimos.size() + " empréstimos");
    }

    private void processarLinhaLivro(String linha) {
        String[] partes = linha.split(";");
        if (partes.length >= 3) {
            String titulo = partes[0];
            String autor = partes[1];
            String isbn = partes[2];

            livros.add(new Livro(titulo, autor, isbn));
        }
    }

    private void processarLinhaMembro(String linha) {
        String[] partes = linha.split(";");
        if (partes.length >= 3) {
            String nome = partes[0];
            int id = Integer.parseInt(partes[1]);
            String email = partes[2];

            membros.add(new Membro(nome, id, email));
        }
    }

    private void processarLinhaEmprestimo(String linha) {
        String[] partes = linha.split(";");
        if (partes.length >= 3) {
            String isbn = partes[0];
            int idMembro = Integer.parseInt(partes[1]);
            Date dataEmprestimo;

            try {
                dataEmprestimo = dateFormat.parse(partes[2]);
            } catch (ParseException e) {
                dataEmprestimo = new Date(); // Em caso de erro, usa a data atual
            }

            // Encontrar livro e membro pelos IDs
            Livro livro = null;
            for (Livro l : livros) {
                if (l.getISBN().equals(isbn)) {
                    livro = l;
                    break;
                }
            }

            Membro membro = null;
            for (Membro m : membros) {
                if (m.getId() == idMembro) {
                    membro = m;
                    break;
                }
            }

            if (livro != null && membro != null) {
                emprestimos.add(new Emprestimo(livro, membro, dataEmprestimo));
            }
        }
    }
}