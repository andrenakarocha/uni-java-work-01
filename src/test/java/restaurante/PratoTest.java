package restaurante;

import org.junit.jupiter.api.Test;
import restaurante.modelo.Prato;

import static org.junit.jupiter.api.Assertions.*;

public class PratoTest {
    
    @Test
    public void testConstrutor() {
        Prato prato = new Prato("Lasanha", 32.90, "Lasanha de carne com molho branco");
        
        assertEquals("Lasanha", prato.getNome());
        assertEquals(32.90, prato.getPreco(), 0.001);
        assertEquals("Lasanha de carne com molho branco", prato.getDescricao());
    }
    
    @Test
    public void testToString() {
        Prato prato = new Prato("Lasanha", 32.90, "Lasanha de carne com molho branco");
        String expected = "Prato{nome='Lasanha', preco=32.9, descricao='Lasanha de carne com molho branco'}";
        
        assertEquals(expected, prato.toString());
    }
}
