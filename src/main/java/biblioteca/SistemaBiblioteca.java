package biblioteca;

import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Membro;
import java.io.*;
import java.util.*;

public class SistemaBiblioteca {
    private static Biblioteca biblioteca = new Biblioteca();
    private static Scanner scanner = new Scanner(System.in);
    private static int proximoIdMembro = 1;

    public static void main(String[] args) {
        inicializarExemplos();

        int opcao = 0;
        do {
            exibirMenu();
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        visualizarLivros();
                        break;
                    case 2:
                        adicionarLivro();
                        break;
                    case 3:
                        removerLivro();
                        break;
                    case 4:
                        visualizarMembros();
                        break;
                    case 5:
                        registrarMembro();
                        break;
                    case 6:
                        visualizarEmprestimos();
                        break;
                    case 7:
                        realizarEmprestimo();
                        break;
                    case 8:
                        devolverLivro();
                        break;
                    case 9:
                        salvarDados();
                        break;
                    case 10:
                        carregarDados();
                        break;
                    case 0:
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n===== SISTEMA DE BIBLIOTECA =====");
        System.out.println("1. Visualizar Livros");
        System.out.println("2. Adicionar Livro");
        System.out.println("3. Remover Livro");
        System.out.println("4. Visualizar Membros");
        System.out.println("5. Registrar Novo Membro");
        System.out.println("6. Visualizar Empréstimos");
        System.out.println("7. Realizar Empréstimo");
        System.out.println("8. Devolver Livro");
        System.out.println("9. Salvar Dados");
        System.out.println("10. Carregar Dados");
        System.out.println("0. Sair");
        System.out.println("=================================");
    }

    private static void inicializarExemplos() {
        // Adicionar alguns livros de exemplo
        biblioteca.adicionarLivro(new Livro("Dom Casmurro", "Machado de Assis", "9788574801414"));
        biblioteca.adicionarLivro(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "9788533613379"));
        biblioteca.adicionarLivro(new Livro("Harry Potter e a Pedra Filosofal", "J.K. Rowling", "9788532523051"));

        // Adicionar alguns membros de exemplo
        biblioteca.registrarMembro(new Membro("Ana Silva", proximoIdMembro++, "ana@email.com"));
        biblioteca.registrarMembro(new Membro("Carlos Santos", proximoIdMembro++, "carlos@email.com"));
    }

    private static void visualizarLivros() {
        List<Livro> livros = biblioteca.getLivros();

        if (livros.isEmpty()) {
            System.out.println("Não há livros cadastrados na biblioteca.");
            return;
        }

        System.out.println("\n===== LIVROS DISPONÍVEIS =====");
        for (int i = 0; i < livros.size(); i++) {
            Livro livro = livros.get(i);
            System.out.println((i+1) + ". " + livro.getTitulo() + " - " + livro.getAutor() + " (ISBN: " + livro.getISBN() + ")");
        }
    }

    private static void adicionarLivro() {
        System.out.println("\n===== ADICIONAR NOVO LIVRO =====");

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        Livro novoLivro = new Livro(titulo, autor, isbn);
        biblioteca.adicionarLivro(novoLivro);
        System.out.println("Livro adicionado com sucesso!");
    }

    private static void removerLivro() {
        List<Livro> livros = biblioteca.getLivros();

        if (livros.isEmpty()) {
            System.out.println("Não há livros cadastrados para remover.");
            return;
        }

        visualizarLivros();
        System.out.print("\nDigite o número do livro que deseja remover: ");

        try {
            int numero = Integer.parseInt(scanner.nextLine());

            if (numero > 0 && numero <= livros.size()) {
                Livro livroRemover = livros.get(numero - 1);

                // Verificar se o livro está emprestado
                boolean livroEmprestado = false;
                for (Emprestimo emprestimo : biblioteca.getEmprestimos()) {
                    if (emprestimo.getLivro().equals(livroRemover)) {
                        livroEmprestado = true;
                        break;
                    }
                }

                if (livroEmprestado) {
                    System.out.println("Não é possível remover um livro que está emprestado!");
                    return;
                }

                System.out.print("Confirma a remoção do livro '" + livroRemover.getTitulo() + "'? (S/N): ");
                String confirmacao = scanner.nextLine();

                if (confirmacao.equalsIgnoreCase("S")) {
                    biblioteca.removerLivro(livroRemover);
                    System.out.println("Livro removido com sucesso!");
                } else {
                    System.out.println("Remoção cancelada.");
                }
            } else {
                System.out.println("Número de livro inválido!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido.");
        }
    }

    private static void visualizarMembros() {
        List<Membro> membros = biblioteca.getMembros();

        if (membros.isEmpty()) {
            System.out.println("Não há membros cadastrados na biblioteca.");
            return;
        }

        System.out.println("\n===== MEMBROS CADASTRADOS =====");
        for (int i = 0; i < membros.size(); i++) {
            Membro membro = membros.get(i);
            System.out.println((i+1) + ". " + membro.getNome() + " (ID: " + membro.getId() + ", Email: " + membro.getEmail() + ")");
        }
    }

    private static void registrarMembro() {
        System.out.println("\n===== REGISTRAR NOVO MEMBRO =====");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Membro novoMembro = new Membro(nome, proximoIdMembro++, email);
        biblioteca.registrarMembro(novoMembro);
        System.out.println("Membro registrado com sucesso!");
    }

    private static void visualizarEmprestimos() {
        List<Emprestimo> emprestimos = biblioteca.getEmprestimos();

        if (emprestimos.isEmpty()) {
            System.out.println("Não há empréstimos ativos no momento.");
            return;
        }

        System.out.println("\n===== EMPRÉSTIMOS ATIVOS =====");
        for (int i = 0; i < emprestimos.size(); i++) {
            Emprestimo emprestimo = emprestimos.get(i);
            System.out.println((i+1) + ". Livro: " + emprestimo.getLivro().getTitulo() +
                    " | Membro: " + emprestimo.getMembro().getNome() +
                    " | Data: " + emprestimo.getDataEmprestimo());
        }
    }

    private static void realizarEmprestimo() {
        List<Livro> livros = biblioteca.getLivros();
        List<Membro> membros = biblioteca.getMembros();

        if (livros.isEmpty()) {
            System.out.println("Não há livros disponíveis para empréstimo.");
            return;
        }

        if (membros.isEmpty()) {
            System.out.println("Não há membros cadastrados para realizar empréstimo.");
            return;
        }

        // Verificar quais livros estão disponíveis (não emprestados)
        List<Livro> livrosDisponiveis = new ArrayList<>(livros);
        for (Emprestimo emprestimo : biblioteca.getEmprestimos()) {
            livrosDisponiveis.remove(emprestimo.getLivro());
        }

        if (livrosDisponiveis.isEmpty()) {
            System.out.println("Todos os livros já estão emprestados no momento.");
            return;
        }

        // Exibir livros disponíveis
        System.out.println("\n===== LIVROS DISPONÍVEIS PARA EMPRÉSTIMO =====");
        for (int i = 0; i < livrosDisponiveis.size(); i++) {
            Livro livro = livrosDisponiveis.get(i);
            System.out.println((i+1) + ". " + livro.getTitulo() + " - " + livro.getAutor());
        }

        System.out.print("\nEscolha o número do livro: ");
        int numeroLivro;
        try {
            numeroLivro = Integer.parseInt(scanner.nextLine());
            if (numeroLivro <= 0 || numeroLivro > livrosDisponiveis.size()) {
                System.out.println("Número de livro inválido!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido.");
            return;
        }

        // Exibir membros
        System.out.println("\n===== MEMBROS =====");
        for (int i = 0; i < membros.size(); i++) {
            Membro membro = membros.get(i);
            System.out.println((i+1) + ". " + membro.getNome() + " (ID: " + membro.getId() + ")");
        }

        System.out.print("\nEscolha o número do membro: ");
        int numeroMembro;
        try {
            numeroMembro = Integer.parseInt(scanner.nextLine());
            if (numeroMembro <= 0 || numeroMembro > membros.size()) {
                System.out.println("Número de membro inválido!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido.");
            return;
        }

        Livro livroSelecionado = livrosDisponiveis.get(numeroLivro - 1);
        Membro membroSelecionado = membros.get(numeroMembro - 1);

        biblioteca.registrarEmprestimo(livroSelecionado, membroSelecionado);
        System.out.println("Empréstimo registrado com sucesso!");
    }

    private static void devolverLivro() {
        List<Emprestimo> emprestimos = biblioteca.getEmprestimos();

        if (emprestimos.isEmpty()) {
            System.out.println("Não há empréstimos ativos para devolver.");
            return;
        }

        visualizarEmprestimos();
        System.out.print("\nDigite o número do empréstimo para devolução: ");

        try {
            int numero = Integer.parseInt(scanner.nextLine());

            if (numero > 0 && numero <= emprestimos.size()) {
                Emprestimo emprestimoDevolver = emprestimos.get(numero - 1);

                System.out.print("Confirma a devolução do livro '" + emprestimoDevolver.getLivro().getTitulo() +
                        "' emprestado para '" + emprestimoDevolver.getMembro().getNome() + "'? (S/N): ");
                String confirmacao = scanner.nextLine();

                if (confirmacao.equalsIgnoreCase("S")) {
                    biblioteca.devolverLivro(emprestimoDevolver);
                    System.out.println("Livro devolvido com sucesso!");
                } else {
                    System.out.println("Devolução cancelada.");
                }
            } else {
                System.out.println("Número de empréstimo inválido!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido.");
        }
    }

    private static void salvarDados() {
        System.out.print("Digite o nome do arquivo para salvar os dados: ");
        String nomeArquivo = scanner.nextLine();

        if (nomeArquivo.trim().isEmpty()) {
            nomeArquivo = "biblioteca.txt";
        }

        try {
            biblioteca.salvarDadosEmArquivo(nomeArquivo);
            System.out.println("Dados salvos com sucesso em: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    private static void carregarDados() {
        System.out.print("Digite o nome do arquivo para carregar os dados: ");
        String nomeArquivo = scanner.nextLine();

        if (nomeArquivo.trim().isEmpty()) {
            nomeArquivo = "biblioteca.txt";
        }

        try {
            File arquivo = new File(nomeArquivo);
            if (arquivo.exists()) {
                biblioteca.carregarDadosDeArquivo(nomeArquivo);
                System.out.println("Dados carregados com sucesso de: " + nomeArquivo);

                // Atualizar o próximo ID de membro
                for (Membro membro : biblioteca.getMembros()) {
                    if (membro.getId() >= proximoIdMembro) {
                        proximoIdMembro = membro.getId() + 1;
                    }
                }
            } else {
                System.out.println("Arquivo não encontrado: " + nomeArquivo);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
}