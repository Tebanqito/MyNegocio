package com.minegocio.negocio.repositorios;
import com.minegocio.negocio.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Cliente> findByNombre(@Param("nombre") String nombre);

    Optional<Cliente> findByEmail(String email);

    @Query("SELECT c FROM Cliente c WHERE c.telefono LIKE CONCAT('%', :telefono, '%')")
    List<Cliente> findByTelefono(@Param("telefono") String telefono);

    @Query("SELECT c FROM Cliente c WHERE (SELECT COUNT(v) FROM Venta v WHERE v.cliente = c) >= :minCompras")
    List<Cliente> findByComprasMinimas(@Param("minCompras") int minCompras);
}
