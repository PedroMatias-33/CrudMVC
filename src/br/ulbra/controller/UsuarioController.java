package br.ulbra.controller;

import br.ulbra.model.Usuario;
import br.ulbra.service.UsuarioService;
import java.util.List;

public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    public void salvar(String nome, String email, String senha) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        service.salvar(usuario);
    }

    public void atualizar(String id, String nome, String email, String senha) {
        Usuario usuario = new Usuario();
        usuario.setId(Integer.parseInt(id));
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        service.atualizar(usuario);
    }

    public void deletar(String id) {
        service.deletar(Integer.parseInt(id));
    }

    public List<Usuario> listar() {
        return service.listar();
    }

    public Usuario autenticar(String email, String senha) {
        return service.autenticar(email, senha);
    }
}
