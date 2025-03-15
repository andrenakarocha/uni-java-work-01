package restaurante;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurante.modelo.Pedido;
import restaurante.modelo.Prato;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class PedidoTest {
    
    private Pedido pedido;
    private Prato prato1;
    private Prato prato2;
    
    @BeforeEach
    public void setUp() {
        pedido = new Pedido(1, "José Silva");
        prato1 = new Prato("Lasanha", 32.90, "Lasanha de carne com molho branco");
        prato2 = new Prato("Suco de Laranja", 8.50, "Suco natural de laranja 500ml");
    }
    
    @Test
    public void testConstrutor() {
        assertEquals(1, pedido.getNumeroPedido());
        assertEquals("José Silva", pedido.getCliente());
        assertEquals(0.0, pedido.getTotal(), 0.001);
        assertTrue(pedido.getListaDePratos().isEmpty());
    }
    
    @Test
    public void testAdicionarPrato() {
        pedido.adicionarPrato(prato1);
        
        List<Prato> pratos = pedido.getListaDePratos();
        assertEquals(1, pratos.size());
        assertEquals(prato1, pratos.get(0));
        assertEquals(32.90, pedido.getTotal(), 0.001);
    }
    
    @Test
    public void testRemoverPrato() {
        pedido.adicionarPrato(prato1);
        pedido.adicionarPrato(prato2);
        
        assertEquals(2, pedido.getListaDePratos().size());
        assertEquals(41.40, pedido.getTotal(), 0.001);
        
        pedido.removerPrato(prato1);
        
        assertEquals(1, pedido.getListaDePratos().size());
        assertEquals(prato2, pedido.getListaDePratos().get(0));
        assertEquals(8.50, pedido.getTotal(), 0.001);
    }
    
    @Test
    public void testCalcularTotal() {
        assertEquals(0.0, pedido.getTotal(), 0.001);
        
        pedido.adicionarPrato(prato1);
        assertEquals(32.90, pedido.getTotal(), 0.001);
        
        pedido.adicionarPrato(prato2);
        assertEquals(41.40, pedido.getTotal(), 0.001);
        
        pedido.removerPrato(prato1);
        assertEquals(8.50, pedido.getTotal(), 0.001);
    }
}
