package br.com.fiap.safezone.controller;

import br.com.fiap.safezone.entity.Alerta;
import br.com.fiap.safezone.service.AlertaService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alertas")
public class AlertaController {
    private final AlertaService service;

    public AlertaController(AlertaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Alerta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Alerta buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Alerta> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    @GetMapping("/dispositivo/{dispositivoId}")
    public List<Alerta> listarPorDispositivo(@PathVariable Long dispositivoId) {
        return service.listarPorDispositivo(dispositivoId);
    }

    @PostMapping
    public Alerta criar(@RequestBody Alerta alerta) {
        return service.criar(alerta);
    }

    @PutMapping("/{id}")
    public Alerta atualizar(@PathVariable Long id, @RequestBody Alerta alerta) {
        return service.atualizar(id, alerta);
    }

    @PatchMapping("/{id}/visualizar")
    public Alerta marcarComoVisualizado(@PathVariable Long id) {
        return service.marcarComoVisualizado(id);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
