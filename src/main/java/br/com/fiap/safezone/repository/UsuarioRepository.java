package br.com.fiap.safezone.repository;

import br.com.fiap.safezone.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}