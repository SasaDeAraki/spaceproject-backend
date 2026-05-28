package br.com.fiap.safezone.controller;

import br.com.fiap.safezone.entity.DispositivoIoT;
import br.com.fiap.safezone.service.DispositivoService;
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

@WebMvcTest(DispositivoController.class)
class DispositivoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DispositivoService dispositivoService;

    @Test
    void deveListarDispositivos() throws Exception {
        DispositivoIoT dispositivo = new DispositivoIoT();
        dispositivo.setId(1L);
        dispositivo.setIdentificador("DEV-01");

        when(dispositivoService.listar()).thenReturn(List.of(dispositivo));

        mockMvc.perform(get("/dispositivos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].identificador").value("DEV-01"));
    }

    @Test
    void deveCriarDispositivo() throws Exception {
        DispositivoIoT dispositivo = new DispositivoIoT();
        dispositivo.setId(1L);
        dispositivo.setIdentificador("DEV-01");

        when(dispositivoService.criar(any(DispositivoIoT.class))).thenReturn(dispositivo);

        mockMvc.perform(post("/dispositivos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "identificador": "DEV-01",
                                  "latitude": -23.5,
                                  "longitude": -46.6
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identificador").value("DEV-01"));
    }

    @Test
    void deveAtualizarDispositivo() throws Exception {
        DispositivoIoT dispositivo = new DispositivoIoT();
        dispositivo.setId(1L);
        dispositivo.setIdentificador("DEV-02");

        when(dispositivoService.atualizar(org.mockito.ArgumentMatchers.eq(1L), any(DispositivoIoT.class))).thenReturn(dispositivo);

        mockMvc.perform(put("/dispositivos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "identificador": "DEV-02"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identificador").value("DEV-02"));
    }

    @Test
    void deveExcluirDispositivo() throws Exception {
        mockMvc.perform(delete("/dispositivos/1"))
                .andExpect(status().isOk());
    }
}
