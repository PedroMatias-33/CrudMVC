package br.ulbra.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/crudmvc";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no banco de dados: " + e.getMessage(), e);
        }
    }
}
