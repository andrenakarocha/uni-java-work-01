package restaurante;

import restaurante.modelo.Prato;
import java.io.*;
import java.util.*;

public class Cardapio {
    private List<Prato> pratos = new ArrayList<>();

    public List<Prato> getPratos() {
        return pratos;
    }

    public void adicionarPrato(Prato prato) {
        pratos.add(prato);
        System.out.println("Prato adicionado ao cardápio: " + prato);
    }

    public void removerPrato(Prato prato) {
        pratos.remove(prato);
        System.out.println("Prato removido do cardápio: " + prato);
    }

    public void visualizarCardapio() {
        System.out.println("===== CARDÁPIO =====");
        for (int i = 0; i < pratos.size(); i++) {
            Prato prato = pratos.get(i);
            System.out.println((i+1) + ". " + prato.getNome() + " - R$" + 
                String.format("%.2f", prato.getPreco()));
            System.out.println("   " + prato.getDescricao());
        }
        System.out.println("====================");
    }

    public Prato buscarPratoPorNome(String nome) {
        for (Prato prato : pratos) {
            if (prato.getNome().equalsIgnoreCase(nome)) {
                return prato;
            }
        }
        return null;
    }

    public void salvarCardapioEmArquivo(String nomeArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Prato prato : pratos) {
                writer.write(prato.getNome() + ";" + 
                             prato.getPreco() + ";" + 
                             prato.getDescricao() + "\n");
            }
        }
        System.out.println("Cardápio salvo em: " + nomeArquivo);
    }

    public void carregarCardapioDeArquivo(String nomeArquivo) throws IOException {
        pratos.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    String nome = dados[0];
                    double preco = Double.parseDouble(dados[1]);
                    String descricao = dados[2];
                    
                    Prato prato = new Prato(nome, preco, descricao);
                    pratos.add(prato);
                }
            }
        }
        System.out.println("Cardápio carregado de: " + nomeArquivo);
    }
}
