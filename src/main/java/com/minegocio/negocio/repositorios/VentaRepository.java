package com.minegocio.negocio.repositorios;

import com.minegocio.negocio.entidades.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Venta> findByClienteIgnoreCase(String cliente);

    // Filtrar por monto mínimo
    List<Venta> findByTotalGreaterThanEqual(double total);

    @Query("SELECT v FROM Venta v JOIN v.detalles d WHERE d.producto.nombre = :nombreProducto")
    List<Venta> findByProducto(@Param("nombreProducto") String nombreProducto);
}
