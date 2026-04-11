package guilda.aventureiros.dto;

import guilda.aventureiros.dominio.NivelPerigo;
import guilda.aventureiros.dominio.StatusMissao;

import java.math.BigDecimal;

public class MissaoMetricasDTO {
    private String titulo;
    private StatusMissao status;
    private NivelPerigo nivelPerigo;
    private Long quantidadeParticipantes;
    private BigDecimal totalRecompensasDistribuidas;

    public MissaoMetricasDTO(String titulo, StatusMissao status, NivelPerigo nivelPerigo,
                             Long quantidadeParticipantes, BigDecimal totalRecompensasDistribuidas) {
        this.titulo = titulo;
        this.status = status;
        this.nivelPerigo = nivelPerigo;
        this.quantidadeParticipantes = quantidadeParticipantes;
        this.totalRecompensasDistribuidas = totalRecompensasDistribuidas == null ? BigDecimal.ZERO : totalRecompensasDistribuidas;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public StatusMissao getStatus() { return status; }
    public void setStatus(StatusMissao status) { this.status = status; }
    public NivelPerigo getNivelPerigo() { return nivelPerigo; }
    public void setNivelPerigo(NivelPerigo nivelPerigo) { this.nivelPerigo = nivelPerigo; }
    public Long getQuantidadeParticipantes() { return quantidadeParticipantes; }
    public void setQuantidadeParticipantes(Long quantidadeParticipantes) { this.quantidadeParticipantes = quantidadeParticipantes; }
    public BigDecimal getTotalRecompensasDistribuidas() { return totalRecompensasDistribuidas; }
    public void setTotalRecompensasDistribuidas(BigDecimal totalRecompensasDistribuidas) { this.totalRecompensasDistribuidas = totalRecompensasDistribuidas; }
}
