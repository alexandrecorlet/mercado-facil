package com.ufcg.psoft.mercadofacil.repository;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;

@SpringBootTest
@DisplayName("Testes do repositório de lotes")
class LoteRepositoryTest {

    @Autowired
    LoteRepository<Lote, Long> driver;

    Lote lote;

    Produto produto;

    @BeforeEach
    void setUp() {
        produto = Produto.builder()
                .id(1L)
                .nome("Produto Base")
                .codigoBarra("123456789")
                .fabricante("Fabricante Base")
                .preco(100.00)
                .build();
        lote = Lote.builder()
                .id(1L)
                .numeroDeItens(100)
                .produto(produto)
                .build();
    }

    @AfterEach
    void tearDown() {
        produto = null;
        lote = null;
    }

    @Test
    @DisplayName("Inserir o primeiro lote de produtos no banco de dados")
    void inserirPrimeiroLoteNoBD() {
        // Arrange
        driver.deleteAll();
        // Act
        Lote resultado = driver.save(lote);
        // Assert
        assertNotNull(resultado);
        assertEquals(1, driver.findAll().size());
        assertEquals(lote.getId().longValue(), resultado.getId().longValue());
        assertEquals(produto, resultado.getProduto());
    }

    @Test
    @DisplayName("Inserir o segundo ou posterior lote de produtos no BD")
    void inserirSegundoOuPosteriorLoteDeProdutosNoBD() {
        // Arrange
        driver.deleteAll();
        Produto produto2 = Produto.builder()
                .id(2L)
                .nome("Produto Dois")
                .codigoBarra("987654321")
                .fabricante("Fabricante Dois")
                .preco(200.00)
                .build();
        Lote lote2 = lote.builder()
                .id(2L)
                .numeroDeItens(200)
                .produto(produto2)
                .build();
        driver.save(lote);
        // Act
        Lote resultado = driver.save(lote2);
        // Assert
        assertNotNull(resultado);
        assertEquals(2, driver.findAll().size());
        assertEquals(lote2.getId().longValue(), resultado.getId().longValue());
        assertEquals(produto2, resultado.getProduto());
    }

    @Test
    @DisplayName("Buscar lote de produtos no BD vazio")
    void buscarLoteDeProdutosNoBDVazio() {
        // Arrange
        driver.deleteAll();
        // Act
        Lote resultado = driver.find(lote.getId());
        // Assert
        assertEquals(0, driver.findAll().size());
        assertNull(resultado);
    }

    @Test
    @DisplayName("Buscar lote de produtos no BD")
    void buscarLoteDeProdutosNoBD() {
        // Arrange
        driver.save(lote);
        // Act
        Lote resultado = driver.find(lote.getId());
        // Assert
        assertNotNull(resultado);
        assertEquals(lote.getId().longValue(), resultado.getId().longValue());
        assertEquals(produto, resultado.getProduto());
    }

    @Test
    @DisplayName("Buscar lote de produtos no BD com mais de um lote")
    void buscarLoteDeProdutosNoBDComMaisDeUmLote() {
        // Arrange
        driver.deleteAll();
        Produto produto2 = Produto.builder()
                .id(2L)
                .nome("Produto Dois")
                .codigoBarra("987654321")
                .fabricante("Fabricante Dois")
                .preco(200.00)
                .build();
        Lote lote2 = lote.builder()
                .id(2L)
                .numeroDeItens(200)
                .produto(produto2)
                .build();
        driver.save(lote);
        driver.save(lote2);
        // Act
        Lote resultado = driver.find(lote2.getId());
        // Assert
        assertNotNull(resultado);
        assertEquals(lote2.getId().longValue(), resultado.getId().longValue());
        assertEquals(produto2, resultado.getProduto());
    }

    @Test
    @DisplayName("Listar lote de produtos no BD vazio")
    void listarLoteDeProdutosNoBDVazio() {
        // Arrange
        driver.deleteAll();
        // Act
        List<Lote> resultado = driver.findAll();
        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Listar lote de produtos no BD")
    void listarLoteDeProdutosNoBD() {
        // Arrange
        driver.deleteAll();
        Produto produto2 = Produto.builder()
                .id(2L)
                .nome("Produto Dois")
                .codigoBarra("987654321")
                .fabricante("Fabricante Dois")
                .preco(200.00)
                .build();
        Lote lote2 = lote.builder()
                .id(2L)
                .numeroDeItens(200)
                .produto(produto2)
                .build();
        driver.save(lote);
        driver.save(lote2);
        // Act
        List<Lote> resultado = driver.findAll();
        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Atualizar lote de produtos no BD vazio (ou que não foi adicionado no BD)")
    void atualizarLoteDeProdutosNoBDVazioOuQueNaoFoiAdicionadoNoBD() {
        // Arrange
        driver.deleteAll();
        lote.setNumeroDeItens(301);
        // Act
        Lote resultado = driver.update(lote);
        // Assert
        assertTrue(driver.findAll().isEmpty());
        assertNull(resultado);
    }

    @Test
    @DisplayName("Atualizar lote de produtos no BD")
    void atualizarLoteDeProdutosNoBD() {
        // Arrange
        Produto produto2 = Produto.builder()
                .id(2L)
                .nome("Produto Dois")
                .codigoBarra("987654321")
                .fabricante("Fabricante Dois")
                .preco(200.00)
                .build();
        driver.deleteAll();
        driver.save(lote);
        lote.setNumeroDeItens(1000);
        lote.setProduto(produto2);
        // Act
        Lote resultado = driver.update(lote);
        // Assert
        assertNotNull(resultado);
        assertEquals(lote.getId().longValue(), resultado.getId().longValue());
        assertEquals(1000, lote.getNumeroDeItens());
        assertEquals(produto2, resultado.getProduto());
    }

    @Test
    @DisplayName("Deletar lote de produtos no BD vazio (ou que não foi adicionado no BD)")
    void deletarLoteDeProdutosNoBDVazioOuQueNaoFoiAdicionadoNoBD() {
        // Arrange
        driver.deleteAll();
        int tamanhoAnterior = driver.findAll().size();
        //Act
        driver.delete(lote);
        //Assert
        assertTrue(driver.findAll().isEmpty());
        assertEquals(tamanhoAnterior, driver.findAll().size());
    }

    @Test
    @DisplayName("Deletar lote de produtos no BD")
    void deletarLoteDeProdutosNoBD() {
        // Arrange
        driver.deleteAll();
        Produto produto2 = Produto.builder()
                .id(2L)
                .nome("Produto Dois")
                .codigoBarra("987654321")
                .fabricante("Fabricante Dois")
                .preco(200.00)
                .build();
        Lote lote2 = lote.builder()
                .id(2L)
                .numeroDeItens(200)
                .produto(produto2)
                .build();
        driver.save(lote2);
        driver.save(lote);
        Integer tamanhoAnterior = driver.findAll().size();
        // Act
        driver.delete(lote);
        // Assert
        assertEquals(tamanhoAnterior - 1, driver.findAll().size());
        // Verifica se o lote de produtos que deletamos realmente foi deletado da lista
        for (Lote l : driver.findAll())
            assertNotEquals(l.getId().longValue(), lote.getId().longValue());
    }

    @Test
    @DisplayName("Deletar todos lotes de produtos no BD vazio")
    void deletarTodosLotesDeProdutosNoBDVazio() {
        // Act
        driver.deleteAll();
        // Assert
        assertTrue(driver.findAll().isEmpty());
    }

    @Test
    @DisplayName("Deletar todos lotes de produtos no BD")
    void deletarTodosLotesDeProdutosNoBD() {
        // Arrange
        driver.save(lote);
        // Act
        driver.deleteAll();
        // Assert
        assertTrue(driver.findAll().isEmpty());
    }

}