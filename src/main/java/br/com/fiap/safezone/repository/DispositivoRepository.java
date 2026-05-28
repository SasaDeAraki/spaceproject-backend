package br.com.fiap.safezone.repository;

import br.com.fiap.safezone.entity.DispositivoIoT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispositivoRepository extends JpaRepository<DispositivoIoT, Long> {}
