/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Produto;
import utils.Conexao;

public class ProdutoDAO implements DAOGenerica {

    private final Connection conexao;

    public ProdutoDAO() throws SQLException, ClassNotFoundException {
        this.conexao = Conexao.abrirConexao();
    }

    @Override
    public void cadastrar(Object objeto) throws SQLException {
        String sql = "call cadastrarProduto(?, ?, ?, ?, ?, ?, ?, ?)";
        Produto produto = (Produto) objeto;
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, produto.getCodigoProduto());
            stmt.setString(2, produto.getNomeProduto());
            stmt.setDate(3, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(produto.getDataValidade()).getTime()));
            stmt.setInt(4, produto.getEstoqueProduto());
            stmt.setDouble(5, produto.getVlrCusto());
            stmt.setDouble(6, produto.getVlrVenda());
            stmt.setString(7, produto.getCodigoBarra());
            stmt.setString(8, produto.getDescricaoProduto());

            stmt.execute();

        } catch (SQLException | ParseException ex) {
            throw new SQLException("Erro ao gravar Produto");

        } finally {
            Conexao.encerrarConexao(conexao, stmt);
        }
    }

    @Override
    public Object consultar(int codigo) throws SQLException {
        String sql = "select * from produto where codigoProduto = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Produto produto = null;
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, codigo);
            rs = stmt.executeQuery();
            while (rs.next()) {
                produto = new Produto(rs.getInt("codigoProduto"), rs.getString("nomeProduto"), rs.getString("dataValidade"), rs.getInt("estoqueProduto"), rs.getDouble("vlrCusto"), rs.getDouble("vlrVenda"), rs.getString("codigoBarra"), rs.getString("descricaoProduto"));
            }
        } catch (SQLException ex) {
            throw new SQLException("Erro ao consultar Produto");
        } finally {
            Conexao.encerrarConexao(conexao, stmt, rs);
        }
        return produto;

    }

    @Override
    public List<Object> listar() throws SQLException {
        String sql = "select * from produto";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Object> lista = new ArrayList<>();
        try {
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto(rs.getInt("codigoProduto"), rs.getString("nomeProduto"), new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate("dataValidade")), rs.getInt("estoqueProduto"), rs.getDouble("vlrCusto"), rs.getDouble("vlrVenda"), rs.getString("codigoBarra"), rs.getString("descricaoProduto"));
                lista.add(produto);
            }
        } catch (SQLException ex) {
            throw new SQLException("Erro ao listar Cliente");
        } finally {
            Conexao.encerrarConexao(conexao, stmt, rs);
        }
        return lista;

    }

    @Override
    public void excluir(int codigo) throws SQLException {
        String sql = "delete from produto where codigoProduto = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, codigo);
            stmt.execute();
        } catch (SQLException ex) {
            throw new SQLException("Erro ao excluir produto");
        } finally {
            Conexao.encerrarConexao(conexao, stmt);
        }
    }

    public List<Object> PesquisarProduto(String nome) throws SQLException {
        String sql = "select * from produto where nomeProduto iLIKE concat( ?, '%')";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Produto produto = null;
        List<Object> listar = new ArrayList<>();
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1,nome);
            rs = stmt.executeQuery();
            while (rs.next()) {
                produto = new Produto(rs.getInt("codigoProduto"), rs.getString("nomeProduto"), rs.getString("dataValidade"), rs.getInt("estoqueProduto"), rs.getDouble("vlrCusto"), rs.getDouble("vlrVenda"), rs.getString("codigoBarra"), rs.getString("descricaoProduto"));
            listar.add(produto);
            }
        } catch (SQLException ex) {
            throw new SQLException("Erro ao consultar Produto");
        } finally {
            Conexao.encerrarConexao(conexao, stmt, rs);
        }
        
       
        return listar;

    }
  

}
