package br.com.fiap.safezone.service;

import br.com.fiap.safezone.entity.Usuario;
import br.com.fiap.safezone.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveListarUsuarios() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Ana");

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Ana");
    }

    @Test
    void deveBuscarUsuarioPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Ana");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorId(1L);

        assertThat(resultado.getNome()).isEqualTo("Ana");
    }

    @Test
    void deveLancar404QuandoUsuarioNaoExistir() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.buscarPorId(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Usu");
    }

    @Test
    void deveCriarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Ana");

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.criar(usuario);

        assertThat(resultado.getNome()).isEqualTo("Ana");
        verify(usuarioRepository).save(eq(usuario));
    }

    @Test
    void deveAtualizarUsuario() {
        Usuario existente = new Usuario();
        existente.setId(1L);
        existente.setNome("Antigo");

        Usuario dados = new Usuario();
        dados.setNome("Novo");
        dados.setEmail("novo@email.com");
        dados.setSenhaHash("hash");
        dados.setCpf("12345678901");
        dados.setTelefone("11999999999");
        dados.setTipoSanguineo("O+");
        dados.setDataNascimento(LocalDate.of(2000, 1, 1));

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.atualizar(1L, dados);

        assertThat(resultado.getNome()).isEqualTo("Novo");
        assertThat(resultado.getEmail()).isEqualTo("novo@email.com");
        assertThat(resultado.getCpf()).isEqualTo("12345678901");
    }

    @Test
    void deveExcluirUsuario() {
        usuarioService.excluir(7L);

        verify(usuarioRepository).deleteById(7L);
    }
}
