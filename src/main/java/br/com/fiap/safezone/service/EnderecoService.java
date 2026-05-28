package br.com.fiap.safezone.service;

import br.com.fiap.safezone.entity.Endereco;
import br.com.fiap.safezone.entity.Usuario;
import br.com.fiap.safezone.repository.EnderecoRepository;
import br.com.fiap.safezone.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Endereco> listar() {
        return enderecoRepository.findAll();
    }

    public Endereco buscarPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Endereço não encontrado"));
    }

    public Endereco buscarPorUsuario(Long usuarioId) {
        return enderecoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Endereço não encontrado para este usuário"));
    }

    public Endereco criar(Long usuarioId, Endereco endereco) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado"));
        endereco.setUsuario(usuario);
        return enderecoRepository.save(endereco);
    }

    public Endereco atualizar(Long id, Endereco dados) {
        Endereco endereco = buscarPorId(id);
        endereco.setPais(dados.getPais());
        endereco.setEstado(dados.getEstado());
        endereco.setCidade(dados.getCidade());
        endereco.setBairro(dados.getBairro());
        endereco.setRua(dados.getRua());
        endereco.setNumero(dados.getNumero());
        endereco.setComplemento(dados.getComplemento());
        endereco.setCep(dados.getCep());
        return enderecoRepository.save(endereco);
    }

    public void excluir(Long id) {
        enderecoRepository.deleteById(id);
    }
}
