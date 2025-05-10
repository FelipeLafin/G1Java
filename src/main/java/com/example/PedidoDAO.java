package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    private Connection conexao;

    public PedidoDAO(String url) throws SQLException {
        conexao = DriverManager.getConnection(url);
        criarTabelaSeNaoExistir();
    }

    private void criarTabelaSeNaoExistir() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS pedidos (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "data TEXT NOT NULL," +
                     "cliente TEXT NOT NULL," +
                     "total FLOAT NOT NULL," +
                     "status TEXT NOT NULL)";
        conexao.createStatement().execute(sql);
    }

    public List<Pedido> buscarPedidosPorNomeCliente(String nome) throws SQLException {
    String sql = "SELECT * FROM pedidos WHERE LOWER(cliente) LIKE ?";
    PreparedStatement stmt = conexao.prepareStatement(sql);
    stmt.setString(1, "%" + nome.toLowerCase() + "%");
    ResultSet rs = stmt.executeQuery();
    
    List<Pedido> pedidos = new ArrayList<>();
    while (rs.next()) {
        pedidos.add(new Pedido(
            rs.getInt("id"),
            rs.getString("data"),
            rs.getString("cliente"),
            rs.getDouble("total"),
            rs.getString("status")
        ));
    }
    return pedidos;
}


    public List<Pedido> listarTodosOsPedidos() throws SQLException {
    String sql = "SELECT * FROM pedidos";
    Statement stmt = conexao.createStatement();
    ResultSet rs = stmt.executeQuery(sql);
    List<Pedido> pedidos = new ArrayList<>();
    while (rs.next()) {
        pedidos.add(new Pedido(
            rs.getInt("id"),
            rs.getString("data"),
            rs.getString("cliente"),
            rs.getDouble("total"),
            rs.getString("status")
        ));
    }
    return pedidos;
}


    public void inserir(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos (data, cliente, total, status) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, pedido.getData());
        stmt.setString(2, pedido.getCliente());
        stmt.setDouble(3, pedido.getTotal());
        stmt.setString(4, pedido.getStatus());
        stmt.executeUpdate();
    }
}
