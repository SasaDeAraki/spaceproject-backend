package br.com.fiap.safezone.service;

import br.com.fiap.safezone.entity.DispositivoIoT;
import br.com.fiap.safezone.repository.DispositivoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DispositivoServiceTest {

    @Mock
    private DispositivoRepository dispositivoRepository;

    @InjectMocks
    private DispositivoService dispositivoService;

    @Test
    void deveCriarDispositivoComUltimaAtualizacao() {
        DispositivoIoT dispositivo = new DispositivoIoT();
        dispositivo.setIdentificador("DEV-01");

        when(dispositivoRepository.save(any(DispositivoIoT.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DispositivoIoT resultado = dispositivoService.criar(dispositivo);

        assertThat(resultado.getIdentificador()).isEqualTo("DEV-01");
        assertThat(resultado.getUltimaAtualizacao()).isNotNull();
    }

    @Test
    void deveAtualizarDispositivo() {
        DispositivoIoT existente = new DispositivoIoT();
        existente.setId(1L);

        DispositivoIoT dados = new DispositivoIoT();
        dados.setIdentificador("DEV-99");
        dados.setLatitude(-23.5);
        dados.setLongitude(-46.6);

        when(dispositivoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(dispositivoRepository.save(any(DispositivoIoT.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DispositivoIoT resultado = dispositivoService.atualizar(1L, dados);

        assertThat(resultado.getIdentificador()).isEqualTo("DEV-99");
        assertThat(resultado.getLatitude()).isEqualTo(-23.5);
        assertThat(resultado.getUltimaAtualizacao()).isNotNull();
    }
}
