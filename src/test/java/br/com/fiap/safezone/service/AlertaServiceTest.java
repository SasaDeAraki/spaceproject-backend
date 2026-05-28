package br.com.fiap.safezone.service;

import br.com.fiap.safezone.entity.Alerta;
import br.com.fiap.safezone.entity.DispositivoIoT;
import br.com.fiap.safezone.entity.Usuario;
import br.com.fiap.safezone.repository.AlertaRepository;
import br.com.fiap.safezone.repository.DispositivoRepository;
import br.com.fiap.safezone.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertaServiceTest {

    @Mock
    private AlertaRepository alertaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private DispositivoRepository dispositivoRepository;

    @InjectMocks
    private AlertaService alertaService;

    @Test
    void deveCriarAlertaComUsuarioEDispositivoValidos() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        DispositivoIoT dispositivo = new DispositivoIoT();
        dispositivo.setId(2L);

        Alerta alerta = new Alerta();
        alerta.setUsuario(usuario);
        alerta.setDispositivo(dispositivo);
        alerta.setLatitude(-23.0);
        alerta.setLongitude(-46.0);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(dispositivoRepository.findById(2L)).thenReturn(Optional.of(dispositivo));
        when(alertaRepository.save(any(Alerta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Alerta resultado = alertaService.criar(alerta);

        assertThat(resultado.getUsuario()).isSameAs(usuario);
        assertThat(resultado.getDispositivo()).isSameAs(dispositivo);
        assertThat(resultado.getDataHora()).isNotNull();
        assertThat(resultado.getVisualizado()).isFalse();
        verify(alertaRepository).save(alerta);
    }

    @Test
    void deveLancarErroQuandoUsuarioNaoForEnviado() {
        Alerta alerta = new Alerta();

        assertThatThrownBy(() -> alertaService.criar(alerta))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Usu");
    }

    @Test
    void deveMarcarAlertaComoVisualizado() {
        Alerta alerta = new Alerta();
        alerta.setId(5L);
        alerta.setVisualizado(false);

        when(alertaRepository.findById(5L)).thenReturn(Optional.of(alerta));
        when(alertaRepository.save(any(Alerta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Alerta resultado = alertaService.marcarComoVisualizado(5L);

        assertThat(resultado.getVisualizado()).isTrue();
    }

    @Test
    void deveListarAlertasPorUsuario() {
        Alerta alerta = new Alerta();
        alerta.setId(1L);

        when(alertaRepository.findByUsuarioId(10L)).thenReturn(List.of(alerta));

        assertThat(alertaService.listarPorUsuario(10L)).hasSize(1);
    }
}
