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
        return produtoRepository.update(produto);
    }

}
