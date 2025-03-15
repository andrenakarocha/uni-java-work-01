package restaurante;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import restaurante.Cardapio;
import restaurante.modelo.Prato;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class CardapioTest {
    
    private Cardapio cardapio;
    private Prato prato1;
    private Prato prato2;
    
    @BeforeEach
    public void setUp() {
        cardapio = new Cardapio();
        prato1 = new Prato("Lasanha", 32.90, "Lasanha de carne com molho branco");
        prato2 = new Prato("Suco de Laranja", 8.50, "Suco natural de laranja 500ml");
    }
    
    @Test
    public void testAdicionarPrato() {
        cardapio.adicionarPrato(prato1);
        
        List<Prato> pratos = cardapio.getPratos();
        assertEquals(1, pratos.size());
        assertEquals(prato1, pratos.get(0));
    }
    
    @Test
    public void testRemoverPrato() {
        cardapio.adicionarPrato(prato1);
        cardapio.adicionarPrato(prato2);
        
        assertEquals(2, cardapio.getPratos().size());
        
        cardapio.removerPrato(prato1);
        
        assertEquals(1, cardapio.getPratos().size());
        assertEquals(prato2, cardapio.getPratos().get(0));
    }
    
    @Test
    public void testBuscarPratoPorNome() {
        cardapio.adicionarPrato(prato1);
        cardapio.adicionarPrato(prato2);
        
        Prato resultado = cardapio.buscarPratoPorNome("Lasanha");
        assertEquals(prato1, resultado);
        
        resultado = cardapio.buscarPratoPorNome("lasanha");
        assertEquals(prato1, resultado);
        
        resultado = cardapio.buscarPratoPorNome("Hambúrguer");
        assertNull(resultado);
    }
    
    @Test
    public void testSalvarECarregarCardapio(@TempDir Path tempDir) throws IOException {
        // Adicionar pratos ao cardápio
        cardapio.adicionarPrato(prato1);
        cardapio.adicionarPrato(prato2);
        
        // Salvar cardápio em arquivo temporário
        File tempFile = tempDir.resolve("cardapio_test.txt").toFile();
        cardapio.salvarCardapioEmArquivo(tempFile.getPath());
        
        // Criar novo cardápio e carregar do arquivo
        Cardapio cardapioCarregado = new Cardapio();
        cardapioCarregado.carregarCardapioDeArquivo(tempFile.getPath());
        
        // Verificar se os pratos foram carregados corretamente
        assertEquals(2, cardapioCarregado.getPratos().size());
        
        Prato pratoCarregado1 = cardapioCarregado.buscarPratoPorNome("Lasanha");
        assertNotNull(pratoCarregado1);
        assertEquals(prato1.getNome(), pratoCarregado1.getNome());
        assertEquals(prato1.getPreco(), pratoCarregado1.getPreco(), 0.001);
        assertEquals(prato1.getDescricao(), pratoCarregado1.getDescricao());
        
        Prato pratoCarregado2 = cardapioCarregado.buscarPratoPorNome("Suco de Laranja");
        assertNotNull(pratoCarregado2);
        assertEquals(prato2.getNome(), pratoCarregado2.getNome());
        assertEquals(prato2.getPreco(), pratoCarregado2.getPreco(), 0.001);
        assertEquals(prato2.getDescricao(), pratoCarregado2.getDescricao());
    }
}
