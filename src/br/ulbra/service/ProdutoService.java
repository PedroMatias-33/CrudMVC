package br.ulbra.service;

import br.ulbra.dao.ProdutoDAO;
import br.ulbra.model.Produto;
import java.util.List;

public class ProdutoService {

    private ProdutoDAO dao;

    public ProdutoService(ProdutoDAO dao) {
        this.dao = dao;
    }

    public void salvar(Produto p) {
        dao.salvar(p);
    }

    public void atualizar(Produto p) {
        dao.atualizar(p);
    }

    public void deletar(int id) {
        dao.deletar(id);
    }

    public List<Produto> listar() {
        return dao.listar();
    }
}