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
        if (produto.getFabricante().isEmpty()) throw new RuntimeException("Fabricante inválido!");
        if (produto.getNome().isEmpty()) throw new RuntimeException("Nome inválido!");
        if (produto.getPreco() <= 0.0) throw new RuntimeException("Preço inválido!");
        return produtoRepository.update(produto);
    }

}
