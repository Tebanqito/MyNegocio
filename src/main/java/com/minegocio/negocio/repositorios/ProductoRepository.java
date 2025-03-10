package com.minegocio.negocio.repositorios;

import com.minegocio.negocio.entidades.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Filtrar por categoría
    List<Producto> findByCategoriaIgnoreCase(String categoria);

    // Filtrar por rango de precios
    List<Producto> findByPrecioUnitarioBetween(Double precioMin, Double precioMax);

    // Filtrar por stock
    List<Producto> findByStockBetween(Integer stockMin, Integer stockMax);

    // Filtrar por fecha de vencimiento
    @Query("SELECT p FROM Producto p WHERE p.fechaDeVencimiento BETWEEN :desde AND :hasta")
    List<Producto> filtrarPorFechaDeVencimiento(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    // Filtrar por nombre o código de barras
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) " +
            "OR p.codigoDeBarras LIKE CONCAT('%', :busqueda, '%')")
    List<Producto> filtrarPorNombreOCodigo(@Param("busqueda") String busqueda);

    @Transactional
    @Modifying
    @Query("UPDATE Producto p SET p.precioUnitario = p.precioUnitario * (1 + :porcentaje / 100)")
    void aumentarPrecioTodos(double porcentaje);

    @Transactional
    @Modifying
    @Query("UPDATE Producto p SET p.precioUnitario = p.precioUnitario * (1 + :porcentaje / 100) WHERE p.categoria = :categoria")
    void aumentarPrecioPorCategoria(String categoria, double porcentaje);

    @Transactional
    @Modifying
    @Query("UPDATE Producto p SET p.precioUnitario = p.precioUnitario * (1 + :porcentaje / 100) WHERE p.id = :id")
    void aumentarPrecioProducto(Long id, double porcentaje);

    @Transactional
    @Modifying
    @Query("UPDATE Producto p SET p.precioUnitario = p.precioUnitario * (1 - :porcentaje / 100) WHERE LOWER(p.marca) = LOWER(:marca)")
    void aplicarDescuentoPorMarca(String marca, double porcentaje);

    @Transactional
    @Modifying
    @Query("UPDATE Producto p SET p.precioUnitario = p.precioUnitario * (1 - :porcentaje / 100) WHERE p.stock <= :stockMinimo")
    void aplicarDescuentoPorStock(int stockMinimo, double porcentaje);

    @Transactional
    @Modifying
    @Query("UPDATE Producto p SET p.precioUnitario = p.precioUnitario * (1 - :porcentaje / 100) WHERE LOWER(p.categoria) = LOWER(:categoria)")
    void aplicarDescuentoPorCategoria(String categoria, double porcentaje);
}
