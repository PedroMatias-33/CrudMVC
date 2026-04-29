package br.ulbra.service;

import br.ulbra.dao.UsuarioDAO;
import br.ulbra.model.Usuario;
import java.util.List;

public class UsuarioService {

    private final UsuarioDAO dao;

    public UsuarioService(UsuarioDAO dao) {
        this.dao = dao;
    }

    public void salvar(Usuario usuario) {
        validar(usuario, false);
        dao.salvar(usuario);
    }

    public void atualizar(Usuario usuario) {
        validar(usuario, true);
        dao.atualizar(usuario);
    }

    public void deletar(int id) {
        if (id <= 0) {
            throw new RuntimeException("Selecione um usuário válido.");
        }
        dao.deletar(id);
    }

    public Usuario buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public List<Usuario> listar() {
        return dao.listar();
    }

    public Usuario autenticar(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Informe o e-mail.");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new RuntimeException("Informe a senha.");
        }

        return dao.autenticar(email.trim(), senha.trim());
    }

    private void validar(Usuario usuario, boolean exigeId) {
        if (exigeId && usuario.getId() <= 0) {
            throw new RuntimeException("Selecione um usuário para editar.");
        }

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome obrigatório.");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new RuntimeException("E-mail obrigatório.");
        }

        if (!emailValido(usuario.getEmail())) {
            throw new RuntimeException("E-mail inválido.");
        }

        if (!exigeId && (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty())) {
            throw new RuntimeException("Senha obrigatória.");
        }
    }

    private boolean emailValido(String email) {
        String emailTratado = email.trim();
        return emailTratado.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
