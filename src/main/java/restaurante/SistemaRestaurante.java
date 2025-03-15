package restaurante;

import restaurante.Cardapio;
import restaurante.modelo.Pedido;
import restaurante.modelo.Prato;
import java.io.*;
import java.util.*;

public class SistemaRestaurante {
    private static Cardapio cardapio = new Cardapio();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int proximoNumeroPedido = 1;

    public static void main(String[] args) {
        inicializarCardapio();
        
        int opcao = 0;
        do {
            exibirMenu();
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        cardapio.visualizarCardapio();
                        break;
                    case 2:
                        criarNovoPedido();
                        break;
                    case 3:
                        visualizarPedidos();
                        break;
                    case 4:
                        adicionarPratoAoCardapio();
                        break;
                    case 5:
                        removerPratoDoCardapio();
                        break;
                    case 6:
                        salvarDados();
                        break;
                    case 7:
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
        System.out.println("\n===== SISTEMA DE RESTAURANTE =====");
        System.out.println("1. Visualizar Cardápio");
        System.out.println("2. Criar Novo Pedido");
        System.out.println("3. Visualizar Pedidos");
        System.out.println("4. Adicionar Prato ao Cardápio");
        System.out.println("5. Remover Prato do Cardápio");
        System.out.println("6. Salvar Dados");
        System.out.println("7. Carregar Dados");
        System.out.println("0. Sair");
        System.out.println("=================================");
    }

    private static void inicializarCardapio() {
        // Adicionar alguns pratos de exemplo
        cardapio.adicionarPrato(new Prato("Macarrão à Bolonhesa", 28.90, "Macarrão com molho de tomate e carne moída"));
        cardapio.adicionarPrato(new Prato("Filé Mignon", 45.50, "Filé mignon grelhado com batatas assadas e legumes"));
        cardapio.adicionarPrato(new Prato("Pizza Margherita", 35.00, "Pizza com molho de tomate, muçarela e manjericão"));
        cardapio.adicionarPrato(new Prato("Salada Caesar", 22.50, "Alface americana, croutons, parmesão e molho caesar"));
        cardapio.adicionarPrato(new Prato("Risoto de Funghi", 38.90, "Arroz arbóreo com cogumelos, creme de leite e parmesão"));
    }

    private static void criarNovoPedido() {
        System.out.println("\n===== NOVO PEDIDO =====");
        System.out.print("Nome do cliente: ");
        String nomeCliente = scanner.nextLine();
        
        Pedido novoPedido = new Pedido(proximoNumeroPedido++, nomeCliente);
        boolean adicionandoPratos = true;
        
        while (adicionandoPratos) {
            cardapio.visualizarCardapio();
            System.out.print("Digite o número do prato (0 para finalizar): ");
            
            try {
                int numeroPrato = Integer.parseInt(scanner.nextLine());
                
                if (numeroPrato == 0) {
                    adicionandoPratos = false;
                } else if (numeroPrato > 0 && numeroPrato <= cardapio.getPratos().size()) {
                    Prato pratoSelecionado = cardapio.getPratos().get(numeroPrato - 1);
                    novoPedido.adicionarPrato(pratoSelecionado);
                } else {
                    System.out.println("Número de prato inválido!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
        
        if (!novoPedido.getListaDePratos().isEmpty()) {
            pedidos.add(novoPedido);
            System.out.println("\nPedido criado com sucesso!");
            novoPedido.visualizarPedido();
        } else {
            System.out.println("\nPedido cancelado: nenhum prato selecionado.");
        }
    }

    private static void visualizarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("Não há pedidos registrados.");
            return;
        }
        
        System.out.println("\n===== PEDIDOS REGISTRADOS =====");
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedido = pedidos.get(i);
            System.out.println((i+1) + ". Pedido #" + pedido.getNumeroPedido() + 
                               " - Cliente: " + pedido.getCliente() + 
                               " - Total: R$" + String.format("%.2f", pedido.getTotal()));
        }
        
        System.out.print("\nDigite o número do pedido para ver detalhes (0 para voltar): ");
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            
            if (opcao > 0 && opcao <= pedidos.size()) {
                pedidos.get(opcao - 1).visualizarPedido();
            } else if (opcao != 0) {
                System.out.println("Número de pedido inválido!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido.");
        }
    }

    private static void adicionarPratoAoCardapio() {
        System.out.println("\n===== ADICIONAR PRATO AO CARDÁPIO =====");
        
        System.out.print("Nome do prato: ");
        String nome = scanner.nextLine();
        
        double preco = 0;
        boolean precoValido = false;
        while (!precoValido) {
            System.out.print("Preço (R$): ");
            try {
                preco = Double.parseDouble(scanner.nextLine());
                precoValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um valor válido.");
            }
        }
        
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        
        Prato novoPrato = new Prato(nome, preco, descricao);
        cardapio.adicionarPrato(novoPrato);
    }

    private static void removerPratoDoCardapio() {
        if (cardapio.getPratos().isEmpty()) {
            System.out.println("O cardápio está vazio.");
            return;
        }
        
        cardapio.visualizarCardapio();
        System.out.print("Digite o número do prato a ser removido: ");
        
        try {
            int numeroPrato = Integer.parseInt(scanner.nextLine());
            
            if (numeroPrato > 0 && numeroPrato <= cardapio.getPratos().size()) {
                Prato pratoRemover = cardapio.getPratos().get(numeroPrato - 1);
                
                System.out.print("Confirma a remoção do prato '" + pratoRemover.getNome() + "'? (S/N): ");
                String confirmacao = scanner.nextLine();
                
                if (confirmacao.equalsIgnoreCase("S")) {
                    cardapio.removerPrato(pratoRemover);
                } else {
                    System.out.println("Remoção cancelada.");
                }
            } else {
                System.out.println("Número de prato inválido!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido.");
        }
    }

    private static void salvarDados() {
        try {
            cardapio.salvarCardapioEmArquivo("cardapio.txt");
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("pedidos.txt"))) {
                for (Pedido pedido : pedidos) {
                    writer.write(pedido.getNumeroPedido() + ";" + pedido.getCliente() + "\n");
                    
                    for (Prato prato : pedido.getListaDePratos()) {
                        writer.write("PRATO:" + prato.getNome() + ";" + 
                                     prato.getPreco() + ";" + 
                                     prato.getDescricao() + "\n");
                    }
                    
                    writer.write("FIM_PEDIDO\n");
                }
            }
            System.out.println("Pedidos salvos em: pedidos.txt");
            
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    private static void carregarDados() {
        try {
            // Carregar cardápio
            File cardapioFile = new File("cardapio.txt");
            if (cardapioFile.exists()) {
                cardapio.carregarCardapioDeArquivo("cardapio.txt");
            } else {
                System.out.println("Arquivo de cardápio não encontrado.");
            }
            
            // Carregar pedidos
            File pedidosFile = new File("pedidos.txt");
            if (pedidosFile.exists()) {
                pedidos.clear();
                proximoNumeroPedido = 1;
                
                try (BufferedReader reader = new BufferedReader(new FileReader("pedidos.txt"))) {
                    String linha;
                    Pedido pedidoAtual = null;
                    
                    while ((linha = reader.readLine()) != null) {
                        if (linha.startsWith("PRATO:")) {
                            String[] dadosPrato = linha.substring(6).split(";");
                            if (dadosPrato.length == 3 && pedidoAtual != null) {
                                String nome = dadosPrato[0];
                                double preco = Double.parseDouble(dadosPrato[1]);
                                String descricao = dadosPrato[2];
                                
                                Prato prato = new Prato(nome, preco, descricao);
                                pedidoAtual.adicionarPrato(prato);
                            }
                        } else if (linha.equals("FIM_PEDIDO")) {
                            if (pedidoAtual != null) {
                                pedidoAtual.calcularTotal();
                                proximoNumeroPedido = Math.max(proximoNumeroPedido, pedidoAtual.getNumeroPedido() + 1);
                            }
                        } else {
                            String[] dadosPedido = linha.split(";");
                            if (dadosPedido.length == 2) {
                                int numeroPedido = Integer.parseInt(dadosPedido[0]);
                                String cliente = dadosPedido[1];
                                
                                pedidoAtual = new Pedido(numeroPedido, cliente);
                                pedidos.add(pedidoAtual);
                            }
                        }
                    }
                }
                System.out.println("Pedidos carregados de: pedidos.txt");
            } else {
                System.out.println("Arquivo de pedidos não encontrado.");
            }
            
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
    }
