package br.com.fiap.safezone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class DispositivoIoT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String identificador;

    private Double latitude;
    private Double longitude;

    private LocalDateTime ultimaAtualizacao;

    @JsonIgnore
    @OneToMany(mappedBy = "dispositivo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alerta> alertas;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<Alerta> alertas) {
        this.alertas = alertas;
    }
}
