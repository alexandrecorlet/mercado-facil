package com.ufcg.psoft.mercadofacil.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.ProdutoAlterarService;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

@SpringBootTest
@DisplayName("Testes do Serviço de alteração do produto")
public class ProdutoAlterarServiceTests {

    @Autowired
    ProdutoAlterarService driver;

    @MockBean
    ProdutoRepository<Produto, Long> produtoRepository;

    Produto produto;

    @BeforeEach
    void setup() {
        Mockito.when(produtoRepository.find(10L))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("4012345678901")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez")
                        .preco(450.00)
                        .build()
                );
        produto = produtoRepository.find(10L);
    }

    @AfterEach
    void tearDown() {
        produto = null;
    }

    @Test
    @DisplayName("Quando um novo nome válido for fornecido para o produto")
    void quandoNovoNomeValido() {
        // Arrange
        produto.setNome("Produto Dez Atualizado");
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("4012345678901")
                        .nome("Produto Dez Atualizado")
                        .fabricante("Empresa Dez")
                        .preco(450.00)
                        .build()
                );
        // Act
        Produto resultado = driver.alterar(produto);

        // Assert
        assertEquals("Produto Dez Atualizado", resultado.getNome());
    }

    @Test
    @DisplayName("Quando um novo nome inválido for fornecido para o produto")
    void quandoNovoNomeInvalido() {
        // Arrange
        produto.setNome("");
        // Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        // Assert
        assertEquals("Nome inválido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando um preço inválido for fornecido para o produto")
    void precoMenorIgualAZero() {
        // Arrange
        produto.setPreco(0.0);
        // Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        // Assert
        assertEquals("Preço inválido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando um preço válido for fornecido para o produto")
    void quandoNovoPrecoValido() {
        // Arrange
        produto.setPreco(1999.00);
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("4012345678901")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez")
                        .preco(1999.00)
                        .build()
                );
        // Act
        Produto resultado = driver.alterar(produto);
        // Assert
        assertEquals(1999.00, resultado.getPreco());
    }

    @Test
    @DisplayName("Quando um fabricante inválido for fornecido para o produto")
    void quandoFabricanteInvalido() {
        // Arrange
        produto.setFabricante("");
        // Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        // Assert
        assertEquals("Fabricante inválido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando um fabricante válido for fornecido para o produto")
    void quandoFabricanteValido() {
        // Arrange
        produto.setFabricante("Fabricante Valido");
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("4012345678901")
                        .nome("Produto Dez")
                        .fabricante("Fabricante Valido")
                        .preco(450.00)
                        .build()
                );
        // Act
        Produto resultado = driver.alterar(produto);
        // Assert
        assertEquals("Fabricante Valido", resultado.getFabricante());
    }

    @Test
    @DisplayName("Quando um código de barra inválido (tamanho != 13) for fornecido para o produto")
    void quandoCodigoBarraTemTamanhoInvalido() {
        // Arrange
        produto.setCodigoBarra("000000000000");
        // Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        // Assert
        assertEquals("Código de barra inválido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando um código de barra inválido (id não começa a partir de 0001) for fornecido para o produto")
    void quandoCodigoBarraTemIDInvalido() {
        // Arrange
        produto.setCodigoBarra("7899137500004");
        // Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        // Assert
        assertEquals("Código de barra inválido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando um código de barra inválido (digito verificador errado) for fornecido para o produto")
    void quandoCodigoBarraTemDigitoVerificadorInvalido() {
        // Arrange
        produto.setCodigoBarra("4012345678900");
        // Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        // Assert
        assertEquals("Código de barra inválido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando um código de barra válido for fornecido para o produto")
    void quandoCodigoBarraValido() {
        // Arrange
        produto.setCodigoBarra("7899137500100");
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500100")
                        .nome("Produto Dez")
                        .fabricante("Fabricante Dez")
                        .preco(450.00)
                        .build()
                );
        // Act
        Produto resultado = driver.alterar(produto);
        // Assert
        assertEquals("7899137500100", resultado.getCodigoBarra());
    }
}
