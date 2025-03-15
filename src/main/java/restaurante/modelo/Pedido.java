package restaurante.modelo;

import java.util.*;

public class Pedido {
    private int numeroPedido;
    private String cliente;
    private List<Prato> listaDePratos = new ArrayList<>();
    private double total;

    public Pedido(int numeroPedido, String cliente) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.total = 0.0;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public List<Prato> getListaDePratos() {
        return listaDePratos;
    }

    public double getTotal() {
        return total;
    }

    public void adicionarPrato(Prato prato) {
        listaDePratos.add(prato);
        calcularTotal();
        System.out.println("Prato adicionado: " + prato);
    }

    public void removerPrato(Prato prato) {
        listaDePratos.remove(prato);
        calcularTotal();
        System.out.println("Prato removido: " + prato);
    }

    public void calcularTotal() {
        total = 0.0;
        for (Prato prato : listaDePratos) {
            total += prato.getPreco();
        }
    }

    public void visualizarPedido() {
        System.out.println("Detalhes do Pedido #" + numeroPedido);
        System.out.println("Cliente: " + cliente);
        System.out.println("Pratos:");
        
        for (int i = 0; i < listaDePratos.size(); i++) {
            System.out.println((i+1) + ". " + listaDePratos.get(i));
        }
        
        System.out.println("Total: R$" + String.format("%.2f", total));
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido=" + numeroPedido +
                ", cliente='" + cliente + '\'' +
                ", listaDePratos=" + listaDePratos +
                ", total=" + total +
                '}';
    }
}
