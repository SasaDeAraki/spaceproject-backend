package br.com.fiap.safezone.controller;

import br.com.fiap.safezone.entity.DispositivoIoT;
import br.com.fiap.safezone.service.DispositivoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dispositivos")
public class DispositivoController {

    private final DispositivoService dispositivoService;

    public DispositivoController(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    @GetMapping
    public List<DispositivoIoT> listar() {
        return dispositivoService.listar();
    }

    @GetMapping("/{id}")
    public DispositivoIoT buscarPorId(@PathVariable Long id) {
        return dispositivoService.buscarPorId(id);
    }

    @PostMapping
    public DispositivoIoT criar(@RequestBody DispositivoIoT dispositivo) {
        return dispositivoService.criar(dispositivo);
    }

    @PutMapping("/{id}")
    public DispositivoIoT atualizar(@PathVariable Long id, @RequestBody DispositivoIoT dispositivo) {
        return dispositivoService.atualizar(id, dispositivo);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        dispositivoService.excluir(id);
    }
}
