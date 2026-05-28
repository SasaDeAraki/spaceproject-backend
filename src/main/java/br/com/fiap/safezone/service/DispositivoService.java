package br.com.fiap.safezone.service;

import br.com.fiap.safezone.entity.DispositivoIoT;
import br.com.fiap.safezone.repository.DispositivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;

    public DispositivoService(DispositivoRepository dispositivoRepository) {
        this.dispositivoRepository = dispositivoRepository;
    }

    public List<DispositivoIoT> listar() {
        return dispositivoRepository.findAll();
    }

    public DispositivoIoT buscarPorId(Long id) {
        return dispositivoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Dispositivo não encontrado"));
    }

    public DispositivoIoT criar(DispositivoIoT dispositivo) {
        dispositivo.setUltimaAtualizacao(LocalDateTime.now());
        return dispositivoRepository.save(dispositivo);
    }

    public DispositivoIoT atualizar(Long id, DispositivoIoT dados) {
        DispositivoIoT dispositivo = buscarPorId(id);
        dispositivo.setIdentificador(dados.getIdentificador());
        dispositivo.setLatitude(dados.getLatitude());
        dispositivo.setLongitude(dados.getLongitude());
        dispositivo.setUltimaAtualizacao(LocalDateTime.now());
        return dispositivoRepository.save(dispositivo);
    }

    public void excluir(Long id) {
        dispositivoRepository.deleteById(id);
    }
}
