package br.com.fiap.safezone.controller;

import br.com.fiap.safezone.entity.Alerta;
import br.com.fiap.safezone.entity.DispositivoIoT;
import br.com.fiap.safezone.entity.Usuario;
import br.com.fiap.safezone.service.AlertaService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlertaController.class)
class AlertaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlertaService alertaService;

    @Test
    void deveListarAlertas() throws Exception {
        Alerta alerta = new Alerta();
        alerta.setId(1L);
        alerta.setLatitude(-23.0);

        when(alertaService.listar()).thenReturn(List.of(alerta));

        mockMvc.perform(get("/alertas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].latitude").value(-23.0));
    }

    @Test
    void deveCriarAlerta() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        DispositivoIoT dispositivo = new DispositivoIoT();
        dispositivo.setId(2L);

        Alerta alerta = new Alerta();
        alerta.setId(3L);
        alerta.setUsuario(usuario);
        alerta.setDispositivo(dispositivo);
        alerta.setLatitude(-23.5);
        alerta.setLongitude(-46.6);
        alerta.setVisualizado(false);

        when(alertaService.criar(any(Alerta.class))).thenReturn(alerta);

        mockMvc.perform(post("/alertas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "usuario": { "id": 1 },
                                  "dispositivo": { "id": 2 },
                                  "latitude": -23.5,
                                  "longitude": -46.6
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.visualizado").value(false));
    }

    @Test
    void deveMarcarAlertaComoVisualizado() throws Exception {
        Alerta alerta = new Alerta();
        alerta.setId(1L);
        alerta.setVisualizado(true);

        when(alertaService.marcarComoVisualizado(1L)).thenReturn(alerta);

        mockMvc.perform(patch("/alertas/1/visualizar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.visualizado").value(true));
    }

    @Test
    void deveAtualizarAlerta() throws Exception {
        Alerta alerta = new Alerta();
        alerta.setId(1L);
        alerta.setLatitude(-10.0);
        alerta.setVisualizado(true);

        when(alertaService.atualizar(org.mockito.ArgumentMatchers.eq(1L), any(Alerta.class))).thenReturn(alerta);

        mockMvc.perform(put("/alertas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "latitude": -10.0,
                                  "visualizado": true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(-10.0));
    }

    @Test
    void deveExcluirAlerta() throws Exception {
        mockMvc.perform(delete("/alertas/1"))
                .andExpect(status().isOk());
    }
}
