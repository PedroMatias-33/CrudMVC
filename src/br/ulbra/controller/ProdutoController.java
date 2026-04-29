package br.ulbra.controller;

import br.ulbra.model.Produto;
import br.ulbra.service.ProdutoService;
import java.util.List;

public class ProdutoController {

    private ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    public void salvar(String nome) {
        Produto p = new Produto();
        p.setNome(nome);
        service.salvar(p);
    }

    public void atualizar(String id, String nome) {
        Produto p = new Produto();
        p.setId(Integer.parseInt(id));
        p.setNome(nome);
        service.atualizar(p);
    }

    public void deletar(String id) {
        service.deletar(Integer.parseInt(id));
    }

    public List<Produto> listar() {
        return service.listar();
    }
}