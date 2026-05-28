package br.com.fiap.safezone.repository;

import br.com.fiap.safezone.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByUsuarioId(Long usuarioId);

    List<Alerta> findByDispositivoId(Long dispositivoId);
}
