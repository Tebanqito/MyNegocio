package com.minegocio.negocio.controladores;

import com.minegocio.negocio.entidades.Producto;
import com.minegocio.negocio.servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/filtrar/categoria")
    public List<Producto> filtrarPorCategoria(@RequestParam String categoria) {
        return productoService.filtrarPorCategoria(categoria);
    }

    @GetMapping("/filtrar/precio")
    public List<Producto> filtrarPorRangoDePrecio(@RequestParam Double precioMin, @RequestParam Double precioMax) {
        return productoService.filtrarPorRangoDePrecio(precioMin, precioMax);
    }

    @GetMapping("/filtrar/stock")
    public List<Producto> filtrarPorStock(@RequestParam Integer stockMin, @RequestParam Integer stockMax) {
        return productoService.filtrarPorStock(stockMin, stockMax);
    }

    @GetMapping("/filtrar/vencimiento")
    public List<Producto> filtrarPorFechaDeVencimiento(@RequestParam String desde, @RequestParam String hasta) {
        LocalDate fechaDesde = LocalDate.parse(desde);
        LocalDate fechaHasta = LocalDate.parse(hasta);
        return productoService.filtrarPorFechaDeVencimiento(fechaDesde, fechaHasta);
    }

    @GetMapping("/filtrar/nombre")
    public List<Producto> filtrarPorNombreOCodigo(@RequestParam String busqueda) {
        return productoService.filtrarPorNombreOCodigo(busqueda);
    }

    @PostMapping
    public Producto agregarProducto(@RequestBody Producto producto) {
        return productoService.agregarProducto(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizarProducto(id, producto);
    }

    @PutMapping("/aumentar-precios")
    public void aumentarPrecios(@RequestParam double porcentaje, @RequestParam(required = false) String categoria) {
        productoService.aumentarPrecios(porcentaje, categoria);
    }

    @PutMapping("/descuento/marca")
    public void aplicarDescuentoPorMarca(@RequestParam String marca, @RequestParam double porcentaje) {
        productoService.aplicarDescuentoPorMarca(marca, porcentaje);
    }

    @PutMapping("/descuento/stock")
    public void aplicarDescuentoPorStock(@RequestParam int stockMinimo, @RequestParam double porcentaje) {
        productoService.aplicarDescuentoPorStock(stockMinimo, porcentaje);
    }

    @PutMapping("/descuento/categoria")
    public void aplicarDescuentoPorCategoria(@RequestParam String categoria, @RequestParam double porcentaje) {
        productoService.aplicarDescuentoPorCategoria(categoria, porcentaje);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    // Nuevo: Aumentar precio de un producto por ID
    @PatchMapping("/{id}/aumentar-precio")
    public void aumentarPrecioProducto(@PathVariable Long id, @RequestParam double porcentaje) {
        productoService.aumentarPrecioProducto(id, porcentaje);
    }

    // Nuevo: Aumentar precio de todos los productos
    @PatchMapping("/aumentar-precio-todos")
    public void aumentarPrecioTodos(@RequestParam double porcentaje) {
        productoService.aumentarPrecioTodos(porcentaje);
    }

    // Nuevo: Aumentar precio de productos por categoría
    @PatchMapping("/aumentar-precio-categoria")
    public void aumentarPrecioPorCategoria(@RequestParam String categoria, @RequestParam double porcentaje) {
        productoService.aumentarPrecioPorCategoria(categoria, porcentaje);
    }
}
