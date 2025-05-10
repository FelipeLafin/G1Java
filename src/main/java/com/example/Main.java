package com.example;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
            try (Scanner scanner = new Scanner(System.in)) {
                PedidoDAO dao = new PedidoDAO("jdbc:sqlite:pedidos.db");
                PedidoFileDAO fileDAO = new PedidoFileDAO("pedidos.csv");
            
                while (true) {
                    System.out.println("\nMenu:");
                    System.out.println("1. Gravar pedidos no banco de dados");
                    System.out.println("2. Listar pedidos existentes");
                    System.out.println("3. Buscar por nome do cliente");
                    System.out.println("0. Sair");
                    System.out.print("\nEscolha: ");
                    int op = scanner.nextInt();
                    scanner.nextLine();
                    switch (op) {
                        case 1: {
                            System.out.print("\n");
                            fileDAO.gravarDados();
                            List<Pedido> lista = dao.listarTodosOsPedidos();
                            int totalPedidos = 0;
                            for (Pedido p : lista) {
                                p.getId();
                                totalPedidos++;
                            }
                            System.out.println("Total de dados existentes: " + totalPedidos);
                            }
                            System.out.println("Pedidos gravados com sucesso.");
                        break;
                        case 2: {
                            System.out.println("\n:");
                            List<Pedido> lista = dao.listarTodosOsPedidos();
                            for (Pedido p : lista) {
                                System.out.printf("ID: %2d | %-30s | %s | R$ %9.2f | %s%n", p.getId(), p.getCliente(), p.getData(), p.getTotal(), p.getStatus());
                            }
                        }
                        break;

                        case 3: {
                            System.out.println("\n:");
                            System.out.print("Nome do cliente a buscar: ");
                            String nome = scanner.next();
                           List<Pedido> encontrados = dao.buscarPedidosPorNomeCliente(nome);
                            if (encontrados.isEmpty()) {
                                System.out.println("\n:");
                                System.out.println("Nenhum pedido encontrado.");
                            } else {
                                for (Pedido p : encontrados) {
                                    System.out.printf("ID: %d | %-20s | %s | R$ %.2f | %s%n", p.getId(), p.getCliente(), p.getData(), p.getTotal(), p.getStatus());
                                }
                            }

                        }
                        break;
                        case 0: {
                            return;
                        }
                        default: {
                            System.out.println("Opção inválida.");
                        }
                    }
                }
            } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        } finally {
            System.out.println("Programa encerrado.");
        }
    }
}
