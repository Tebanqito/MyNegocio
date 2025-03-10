package com.minegocio.negocio.dto;

import java.util.List;

public class ReciboDTO {
    private String numeroRecibo;
    private String fecha;
    private String cliente;
    private List<ProductoDTO> productos;
    private double total;
    private String metodoDePago;

    public ReciboDTO() {
    }

    public ReciboDTO(String numeroRecibo, String fecha, String cliente, List<ProductoDTO> productos, double total, String metodoDePago) {
        this.numeroRecibo = numeroRecibo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.productos = productos;
        this.total = total;
        this.metodoDePago = metodoDePago;
    }

    public String getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }
}
