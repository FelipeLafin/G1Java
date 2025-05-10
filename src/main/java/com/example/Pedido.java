package com.example;
public class Pedido {
    private int id;
    private String data;
    private String cliente;
    private double total;
    private String status;

    public Pedido(String data, String cliente, double total, String status) {
        this.data = data;
        this.cliente = cliente;
        this.total = total;
        this.status = status;
    }

    public Pedido(int id, String data, String cliente, double total, String status) {
        this(data, cliente, total, status);
        this.id = id;
    }

    public int getId() { return id; }
    public String getData() { return data; }
    public String getCliente() { return cliente; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setData(String data) { this.data = data; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setTotal(double total) { this.total = total; }
    public void setStatus(String status) { this.status = status; }
}