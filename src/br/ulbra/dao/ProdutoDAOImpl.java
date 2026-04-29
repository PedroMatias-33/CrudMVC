package br.ulbra.dao;

import br.ulbra.model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOImpl implements ProdutoDAO {

    private Connection conn;

    public ProdutoDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void salvar(Produto p) {
        try {
            String sql = "INSERT INTO produtos (nome) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, p.getNome());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar produto");
        }
    }

    @Override
    public void atualizar(Produto p) {
        try {
            String sql = "UPDATE produtos SET nome = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar produto");
        }
    }

    @Override
    public void deletar(int id) {
        try {
            String sql = "DELETE FROM produtos WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar produto");
        }
    }

    @Override
    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM produtos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                lista.add(p);
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar produtos");
        }

        return lista;
    }
}