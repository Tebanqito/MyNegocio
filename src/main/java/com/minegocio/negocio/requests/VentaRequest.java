package com.minegocio.negocio.requests;

import com.minegocio.negocio.controladores.VentaController;

import java.util.List;

public class VentaRequest {
    private String cliente;
    private List<VentaRequestDetalle> detalles;

    public VentaRequest() {
    }

    public VentaRequest(String cliente, List<VentaRequestDetalle> detalles) {
        this.cliente = cliente;
        this.detalles = detalles;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<VentaRequestDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<VentaRequestDetalle> detalles) {
        this.detalles = detalles;
    }
}
