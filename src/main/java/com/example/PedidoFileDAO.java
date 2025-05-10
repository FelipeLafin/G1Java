package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoFileDAO {
    private String nomeArquivo = "pedidos.csv";

    public PedidoFileDAO() {
    }
    
    public PedidoFileDAO(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public List<Pedido> carregarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        int totalPedidosLidos = 0;
        int totalPedidosImportados = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                totalPedidosLidos++;
                String[] campos = linha.split(",");
                if (campos.length == 4) {
                    try {
                        String data = campos[0];
                        String cliente = campos[1];
                        double total = Double.parseDouble(campos[2]);
                        String status = campos[3];
                        pedidos.add(new Pedido(data, cliente, total, status));
                        totalPedidosImportados++;
                    } catch (Exception e) {
                        System.err.println("Erro ao processar linha: " + linha + " - " + e.getMessage());
                        System.out.println("Pedido não importado: " + linha);
                    }
                }
                else if(campos.length > 4) {
                    try {
                        int id = Integer.parseInt(campos[0]);
                        String data = campos[1];
                        String cliente = campos[2];
                        double total = Double.parseDouble(campos[3]);
                        String status = campos[4];
                        pedidos.add(new Pedido(id, data, cliente, total, status));
                        totalPedidosImportados++;
                    }
                    catch (Exception e) {
                        System.err.println("Erro ao processar linha: " + linha + " - " + e.getMessage());
                        System.out.println("Pedido não importado: " + linha);
                    }
                }
                else {
                    System.err.println("Formato inválido na linha: " + linha);
                    System.out.println("Pedido não importado: " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar pedidos do arquivo: " + e.getMessage());
        }
        System.out.println("Total de pedidos lidos: " + totalPedidosLidos);
        System.out.println("Total de pedidos importados: " + totalPedidosImportados);

        return pedidos;
    }

    public void gravarDados() {
        try {
            List<Pedido> pedidos = carregarPedidos();
            PedidoDAO dao = new PedidoDAO("jdbc:sqlite:pedidos.db");

            for (Pedido p : pedidos) {
                dao.inserir(p);
            }
        } catch (SQLException | NumberFormatException e) {
            System.err.println("Erro ao carregar pedidos do arquivo: " + e.getMessage());
        }
    }
}
