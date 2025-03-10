package com.minegocio.negocio.servicios;

import com.minegocio.negocio.entidades.Producto;
import com.minegocio.negocio.repositorios.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto agregarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        Producto existente = productoRepository.findById(id).orElseThrow();
        existente.setNombre(producto.getNombre());
        existente.setCategoria(producto.getCategoria());
        existente.setMarca(producto.getMarca());
        existente.setStock(producto.getStock());
        existente.setPrecioUnitario(producto.getPrecioUnitario());
        existente.setUnidadDeMedida(producto.getUnidadDeMedida());
        existente.setCodigoDeBarras(producto.getCodigoDeBarras());
        existente.setDescripcion(producto.getDescripcion());
        existente.setFechaDeVencimiento(producto.getFechaDeVencimiento());
        return productoRepository.save(existente);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public void aumentarPrecios(double porcentaje, String categoria) {
        List<Producto> productos;

        if (categoria != null && !categoria.isEmpty()) {
            productos = productoRepository.findByCategoriaIgnoreCase(categoria);
        } else {
            productos = productoRepository.findAll();
        }

        for (Producto producto : productos) {
            double nuevoPrecio = producto.getPrecioUnitario() * (1 + porcentaje / 100);
            producto.setPrecioUnitario(nuevoPrecio);
        }

        productoRepository.saveAll(productos);
    }

    public List<Producto> filtrarPorCategoria(String categoria) {
        return productoRepository.findByCategoriaIgnoreCase(categoria);
    }

    public List<Producto> filtrarPorRangoDePrecio(Double precioMin, Double precioMax) {
        return productoRepository.findByPrecioUnitarioBetween(precioMin, precioMax);
    }

    public List<Producto> filtrarPorStock(Integer stockMin, Integer stockMax) {
        return productoRepository.findByStockBetween(stockMin, stockMax);
    }

    public List<Producto> filtrarPorFechaDeVencimiento(LocalDate desde, LocalDate hasta) {
        return productoRepository.filtrarPorFechaDeVencimiento(desde, hasta);
    }

    public List<Producto> filtrarPorNombreOCodigo(String busqueda) {
        return productoRepository.filtrarPorNombreOCodigo(busqueda);
    }

    public void aumentarPrecioProducto(Long id, double porcentaje) {
        productoRepository.aumentarPrecioProducto(id, porcentaje);
    }

    public void aumentarPrecioTodos(double porcentaje) {
        productoRepository.aumentarPrecioTodos(porcentaje);
    }

    public void aumentarPrecioPorCategoria(String categoria, double porcentaje) {
        productoRepository.aumentarPrecioPorCategoria(categoria, porcentaje);
    }
}
