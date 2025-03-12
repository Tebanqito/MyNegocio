package com.minegocio.negocio.repositorios;
import com.minegocio.negocio.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
