package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {

    public static Connection abrirConexao() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/confeitaria", "postgres", "123456");
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException("Driver do banco de dados não encontrado!");
        } catch (SQLException ex) {
            throw new SQLException("Erro de conexão com o banco de dados!");
        }
    }

    private static void encerrar(Connection conexao, PreparedStatement requisicao, ResultSet resultado) throws SQLException {
        try {
            if (conexao != null) {
                conexao.close();
            }
            if (requisicao != null) {
                requisicao.close();
            }
            if (resultado != null) {
                resultado.close();
            }
        } catch (SQLException ex) {
            throw new SQLException("Erro ao encerrar conexão com o banco de dados!");
        }
    }

    public static void encerrarConexao(Connection conexao, PreparedStatement requisicao, ResultSet resultado) throws SQLException {
        encerrar(conexao, requisicao, resultado);
    }

    public static void encerrarConexao(Connection conexao, PreparedStatement requisicao) throws SQLException {
        encerrar(conexao, requisicao, null);
    }
}
