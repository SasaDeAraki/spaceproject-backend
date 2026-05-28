package br.com.fiap.safezone.service;

import br.com.fiap.safezone.entity.Alerta;
import br.com.fiap.safezone.entity.DispositivoIoT;
import br.com.fiap.safezone.entity.Usuario;
import br.com.fiap.safezone.repository.AlertaRepository;
import br.com.fiap.safezone.repository.DispositivoRepository;
import br.com.fiap.safezone.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaService {
    private final AlertaRepository alertaRepository;
    private final UsuarioRepository usuarioRepository;
    private final DispositivoRepository dispositivoRepository;

    public AlertaService(AlertaRepository alertaRepository,
                         UsuarioRepository usuarioRepository,
                         DispositivoRepository dispositivoRepository) {
        this.alertaRepository = alertaRepository;
        this.usuarioRepository = usuarioRepository;
        this.dispositivoRepository = dispositivoRepository;
    }

    public List<Alerta> listar() {
        return alertaRepository.findAll();
    }

    public Alerta buscarPorId(Long id) {
        return alertaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Alerta não encontrada"));
    }

    public List<Alerta> listarPorUsuario(Long usuarioId) {
        return alertaRepository.findByUsuarioId(usuarioId);
    }

    public List<Alerta> listarPorDispositivo(Long dispositivoId) {
        return alertaRepository.findByDispositivoId(dispositivoId);
    }

    public Alerta criar(Alerta alerta) {
        if (alerta.getUsuario() == null || alerta.getUsuario().getId() == null) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Usuário é obrigatório");
        }
        if (alerta.getDispositivo() == null || alerta.getDispositivo().getId() == null) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Dispositivo é obrigatório");
        }

        Usuario usuario = usuarioRepository.findById(alerta.getUsuario().getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado"));
        DispositivoIoT dispositivo = dispositivoRepository.findById(alerta.getDispositivo().getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Dispositivo não encontrado"));

        alerta.setUsuario(usuario);
        alerta.setDispositivo(dispositivo);
        alerta.setDataHora(LocalDateTime.now());
        alerta.setVisualizado(false);
        return alertaRepository.save(alerta);
    }

    public Alerta marcarComoVisualizado(Long id) {
        Alerta alerta = buscarPorId(id);
        alerta.setVisualizado(true);
        return alertaRepository.save(alerta);
    }

    public Alerta atualizar(Long id, Alerta dados) {
        Alerta alerta = buscarPorId(id);
        alerta.setLatitude(dados.getLatitude());
        alerta.setLongitude(dados.getLongitude());
        if (dados.getVisualizado() != null) {
            alerta.setVisualizado(dados.getVisualizado());
        }
        return alertaRepository.save(alerta);
    }

    public void excluir(Long id) {
        alertaRepository.deleteById(id);
    }
}
