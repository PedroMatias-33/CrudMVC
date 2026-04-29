package br.ulbra.dao;

import br.ulbra.model.Usuario;
import java.util.List;



public interface UsuarioDAO {

    Usuario autenticar(String email, String senha);
    void salvar(Usuario usuario);
    void atualizar(Usuario usuario);
    void deletar(int id);
    Usuario buscarPorId(int id);
    List<Usuario> listar();
}
