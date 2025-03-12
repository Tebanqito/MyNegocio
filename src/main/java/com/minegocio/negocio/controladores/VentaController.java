package com.minegocio.negocio.controladores;

import com.minegocio.negocio.entidades.Venta;
import com.minegocio.negocio.requests.VentaRequest;
import com.minegocio.negocio.servicios.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/por-fecha")
    public List<Venta> obtenerPorFecha(@RequestParam String fecha) {
        return ventaService.obtenerPorFecha(LocalDate.parse(fecha));
    }

    @GetMapping("/por-rango-fechas")
    public List<Venta> obtenerPorRangoFechas(@RequestParam String desde, @RequestParam String hasta) {
        return ventaService.obtenerPorRangoFechas(LocalDate.parse(desde), LocalDate.parse(hasta));
    }

    @GetMapping("/por-cliente")
    public List<Venta> obtenerPorCliente(@RequestParam String nombre) {
        return ventaService.obtenerPorCliente(nombre);
    }

    @GetMapping("/por-monto")
    public List<Venta> obtenerPorMontoMinimo(@RequestParam double minimo) {
        return ventaService.obtenerPorMontoMinimo(minimo);
    }

    @GetMapping("/por-producto")
    public List<Venta> obtenerPorProducto(@RequestParam String nombre) {
        return ventaService.obtenerPorProducto(nombre);
    }

    @PostMapping
    public Venta registrarVenta(@RequestBody VentaRequest request) {
        return ventaService.registrarVenta(request.getClienteId(), request.getDetalles());
    }

}
