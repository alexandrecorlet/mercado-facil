package com.ufcg.psoft.mercadofacil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;


@Service
public class ProdutoAlterarPadraoService implements ProdutoAlterarService {

    @Autowired
    ProdutoRepository<Produto, Long> produtoRepository;

    @Override
    public Produto alterar(Produto produto) {
        if (!ehCodigoDeBarraValido(produto.getCodigoBarra())) throw new RuntimeException("Código de barra inválido!");
        if (produto.getFabricante().isEmpty()) throw new RuntimeException("Fabricante inválido!");
        if (produto.getNome().isEmpty()) throw new RuntimeException("Nome inválido!");
        if (produto.getPreco() <= 0.0) throw new RuntimeException("Preço inválido!");
        return produtoRepository.update(produto);
    }

    private boolean ehCodigoDeBarraValido(String codigoBarra) {
        return (validarTamanhoCodigoDeBarra(codigoBarra)
                && validarIdCodigoDeBarra(codigoBarra)
                && validarDigitoVerificadorCodigoDeBarra(codigoBarra));
    }

    private boolean validarTamanhoCodigoDeBarra(String codigoBarra) {
        return codigoBarra.length() == 13;
    }

    private boolean validarIdCodigoDeBarra(String codigoBarra) {
        return Integer.parseInt(codigoBarra.substring(8, 12)) >= 1;
    }

    private boolean validarDigitoVerificadorCodigoDeBarra(String codigoBarra) {
        int soma = 0;
        for (int i = 0; i < codigoBarra.length() - 1; ++i) {
            int num = Integer.parseInt(String.valueOf(codigoBarra.charAt(i)));
            soma += i % 2 == 1 ? num * 3 : num;
        }
        int digitoVerificador = (10 - (soma % 10)) % 10;
        return digitoVerificador == Integer.parseInt(String.valueOf(codigoBarra.charAt(codigoBarra.length() - 1)));
    }

}
