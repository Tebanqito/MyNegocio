package com.minegocio.negocio.requests;


import java.util.List;

public class VentaRequest {
    private Long clienteId;
    private List<VentaRequestDetalle> detalles;

    public VentaRequest() {
    }

    public VentaRequest(Long clienteId, List<VentaRequestDetalle> detalles) {
        this.clienteId = clienteId;
        this.detalles = detalles;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<VentaRequestDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<VentaRequestDetalle> detalles) {
        this.detalles = detalles;
    }
}
