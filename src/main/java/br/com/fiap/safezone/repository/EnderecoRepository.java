package br.com.fiap.safezone.repository;

import br.com.fiap.safezone.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Optional<Endereco> findByUsuarioId(Long usuarioId);
}
