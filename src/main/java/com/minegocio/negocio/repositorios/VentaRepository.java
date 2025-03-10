package com.minegocio.negocio.repositorios;

import com.minegocio.negocio.entidades.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {}
