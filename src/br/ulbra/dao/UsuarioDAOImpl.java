package br.ulbra.dao;

import br.ulbra.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAOImpl implements UsuarioDAO {

    private Connection conn;

    public UsuarioDAOImpl() {
        this.conn = ConnectionFactory.getConnection();
    }

    public UsuarioDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nome, email, senha) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, gerarHash(usuario.getSenha()));
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Usuario usuario) {
        boolean alterarSenha = usuario.getSenha() != null && !usuario.getSenha().trim().isEmpty();
        String sql = alterarSenha
                ? "UPDATE Usuarios SET nome = ?, email = ?, senha = ? WHERE id_usuario = ?"
                : "UPDATE Usuarios SET nome = ?, email = ? WHERE id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());

            if (alterarSenha) {
                stmt.setString(3, gerarHash(usuario.getSenha()));
                stmt.setInt(4, usuario.getId());
            } else {
                stmt.setInt(3, usuario.getId());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM Usuarios WHERE id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT id_usuario, nome, email, senha FROM Usuarios WHERE id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return montarUsuario(rs);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<Usuario>();
        String sql = "SELECT id_usuario, nome, email, senha FROM Usuarios ORDER BY id_usuario";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(montarUsuario(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar usuários: " + e.getMessage(), e);
        }

        return lista;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        String sql = "SELECT id_usuario, nome, email, senha FROM Usuarios WHERE email = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, email);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String senhaBanco = rs.getString("senha");

                    if (senhaConfere(senha, senhaBanco)) {
                        return montarUsuario(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao autenticar usuário: " + e.getMessage(), e);
        }

        return null;
    }

    private Usuario montarUsuario(ResultSet rs) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id_usuario"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        return usuario;
    }

    private String gerarHash(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    private boolean senhaConfere(String senhaDigitada, String senhaBanco) {
        if (senhaBanco == null || senhaBanco.trim().isEmpty()) {
            return false;
        }

        if (senhaBanco.startsWith("$2a$") || senhaBanco.startsWith("$2b$") || senhaBanco.startsWith("$2y$")) {
            return BCrypt.checkpw(senhaDigitada, senhaBanco);
        }

        return senhaBanco.equals(senhaDigitada);
    }
}
