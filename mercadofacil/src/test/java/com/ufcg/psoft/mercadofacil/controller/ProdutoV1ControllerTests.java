package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Produtos")
public class ProdutoV1ControllerTests {

    @Autowired
    MockMvc driver;

    @Autowired
    ProdutoRepository<Produto, Long> produtoRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Produto produto;

    String urlTemplate;

    @BeforeEach
    void setup() {
        produto = produtoRepository.find(10L);
        urlTemplate = "/v1/produtos/" + produto.getId();
    }

    @AfterEach
    void tearDown() {
        produto = null;
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de campos obrigatórios")
    class ProdutoValidacaoCamposObrigatorios {

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados válidos")
        void quandoAlteramosNomeDoProdutoValido() throws Exception {
            // Arrange
            produto.setNome("Produto Dez Atualizado");

            // Act
            String responseJsonString = driver.perform(put(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertEquals("Produto Dez Atualizado", resultado.getNome());
        }

    }

    @Nested
    @DisplayName("Conjunto de casos de verificação da regra sobre o preço")
    class ProdutoValidacaoRegrasDoPreco {

        @Test
        @DisplayName("Quando alteramos o produto com preço válido")
        void quandoAlteramosPreçoDoProdutoValido() throws Exception {
            // Arrange
            produto.setPreco(450.00);

            // Act
            String responseJsonString = driver.perform(put(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();
            // Assert
            assertEquals(450.00, resultado.getPreco());
        }

    }

    @Nested
    @DisplayName("Conjunto de casos de verificação da validação do código de barras")
    class ProdutoValidacaoCodigoDeBarras {

        @Test
        @DisplayName("Quando alteramos o produto com código de barra válido")
        void quandoAlteramosCodigoBarraDoProdutoValido() throws Exception {
            // Arrange
            produto.setCodigoBarra("4012345678901");
            // Act
            String responseJsonString = driver.perform(put(urlTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();
            // Assert
            assertEquals("4012345678901", resultado.getCodigoBarra());
        }

    }

}