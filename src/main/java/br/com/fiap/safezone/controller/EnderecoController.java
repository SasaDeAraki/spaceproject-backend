package br.com.fiap.safezone.controller;

import br.com.fiap.safezone.entity.Endereco;
import br.com.fiap.safezone.service.EnderecoService;
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
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public List<Endereco> listar() {
        return enderecoService.listar();
    }

    @GetMapping("/{id}")
    public Endereco buscarPorId(@PathVariable Long id) {
        return enderecoService.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public Endereco buscarPorUsuario(@PathVariable Long usuarioId) {
        return enderecoService.buscarPorUsuario(usuarioId);
    }

    @PostMapping("/usuario/{usuarioId}")
    public Endereco criar(@PathVariable Long usuarioId, @RequestBody Endereco endereco) {
        return enderecoService.criar(usuarioId, endereco);
    }

    @PutMapping("/{id}")
    public Endereco atualizar(@PathVariable Long id, @RequestBody Endereco endereco) {
        return enderecoService.atualizar(id, endereco);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        enderecoService.excluir(id);
    }
}
