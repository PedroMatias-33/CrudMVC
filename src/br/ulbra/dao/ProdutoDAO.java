package br.ulbra.dao;

import br.ulbra.model.Produto;
import java.util.List;

public interface ProdutoDAO {
    void salvar(Produto p);
    void atualizar(Produto p);
    void deletar(int id);
    List<Produto> listar();
}