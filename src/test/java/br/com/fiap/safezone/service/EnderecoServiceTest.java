package br.com.fiap.safezone.service;

import br.com.fiap.safezone.entity.Endereco;
import br.com.fiap.safezone.entity.Usuario;
import br.com.fiap.safezone.repository.EnderecoRepository;
import br.com.fiap.safezone.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    @Test
    void deveCriarEnderecoAssociadoAoUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(10L);

        Endereco endereco = new Endereco();
        endereco.setCidade("Sao Paulo");

        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(usuario));
        when(enderecoRepository.save(any(Endereco.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Endereco resultado = enderecoService.criar(10L, endereco);

        assertThat(resultado.getUsuario()).isSameAs(usuario);
        assertThat(resultado.getCidade()).isEqualTo("Sao Paulo");
        verify(enderecoRepository).save(endereco);
    }

    @Test
    void deveBuscarEnderecoPorUsuario() {
        Endereco endereco = new Endereco();
        endereco.setId(3L);
        endereco.setCidade("Campinas");

        when(enderecoRepository.findByUsuarioId(10L)).thenReturn(Optional.of(endereco));

        Endereco resultado = enderecoService.buscarPorUsuario(10L);

        assertThat(resultado.getCidade()).isEqualTo("Campinas");
    }

    @Test
    void deveListarEnderecos() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);

        when(enderecoRepository.findAll()).thenReturn(List.of(endereco));

        assertThat(enderecoService.listar()).hasSize(1);
    }
}
