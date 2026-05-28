package br.com.fiap.safezone.controller;

import br.com.fiap.safezone.entity.Endereco;
import br.com.fiap.safezone.service.EnderecoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EnderecoService enderecoService;

    @Test
    void deveListarEnderecos() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setCidade("Sao Paulo");

        when(enderecoService.listar()).thenReturn(List.of(endereco));

        mockMvc.perform(get("/enderecos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cidade").value("Sao Paulo"));
    }

    @Test
    void deveCriarEnderecoParaUsuario() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setCidade("Campinas");

        when(enderecoService.criar(org.mockito.ArgumentMatchers.eq(10L), any(Endereco.class))).thenReturn(endereco);

        mockMvc.perform(post("/enderecos/usuario/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "cidade": "Campinas",
                                  "estado": "SP",
                                  "pais": "Brasil"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cidade").value("Campinas"));
    }

    @Test
    void deveBuscarEnderecoDoUsuario() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setId(2L);
        endereco.setCidade("Sorocaba");

        when(enderecoService.buscarPorUsuario(10L)).thenReturn(endereco);

        mockMvc.perform(get("/enderecos/usuario/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cidade").value("Sorocaba"));
    }

    @Test
    void deveAtualizarEndereco() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setCidade("Curitiba");

        when(enderecoService.atualizar(org.mockito.ArgumentMatchers.eq(1L), any(Endereco.class))).thenReturn(endereco);

        mockMvc.perform(put("/enderecos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "cidade": "Curitiba"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cidade").value("Curitiba"));
    }

    @Test
    void deveExcluirEndereco() throws Exception {
        mockMvc.perform(delete("/enderecos/1"))
                .andExpect(status().isOk());
    }
}
