package br.com.fiap.safezone.service;

import br.com.fiap.safezone.entity.Usuario;
import br.com.fiap.safezone.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado"));
    }

    public Usuario criar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario dados) {
        Usuario usuario = buscarPorId(id);
        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());
        usuario.setSenhaHash(dados.getSenhaHash());
        usuario.setCpf(dados.getCpf());
        usuario.setTelefone(dados.getTelefone());
        usuario.setTipoSanguineo(dados.getTipoSanguineo());
        usuario.setDataNascimento(dados.getDataNascimento());
        return usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
}
