package com.minegocio.negocio.controladores;

import com.minegocio.negocio.entidades.Venta;
import com.minegocio.negocio.requests.VentaRequest;
import com.minegocio.negocio.servicios.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaService.listarVentas();
    }

    @GetMapping("/{id}/recibo")
    public ResponseEntity<byte[]> generarRecibo(@PathVariable Long id) {
        byte[] pdf = ventaService.generarRecibo(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=recibo_venta_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }


    @PostMapping
    public Venta registrarVenta(@RequestBody VentaRequest request) {
        return ventaService.registrarVenta(request.getClienteId(), request.getDetalles());
    }

}
