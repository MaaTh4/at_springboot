package guilda.aventureiros.dto;

import guilda.aventureiros.dominio.NivelPerigo;
import guilda.aventureiros.dominio.StatusMissao;
import java.time.OffsetDateTime;

public class MissaoResumoDTO {
    private Long id;
    private String titulo;
    private StatusMissao status;
    private NivelPerigo nivelPerigo;
    private OffsetDateTime createdAt;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataTermino;

    public MissaoResumoDTO(Long id, String titulo, StatusMissao status, NivelPerigo nivelPerigo,
                           OffsetDateTime createdAt, OffsetDateTime dataInicio, OffsetDateTime dataTermino) {
        this.id = id;
        this.titulo = titulo;
        this.status = status;
        this.nivelPerigo = nivelPerigo;
        this.createdAt = createdAt;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public StatusMissao getStatus() { return status; }
    public void setStatus(StatusMissao status) { this.status = status; }
    public NivelPerigo getNivelPerigo() { return nivelPerigo; }
    public void setNivelPerigo(NivelPerigo nivelPerigo) { this.nivelPerigo = nivelPerigo; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(OffsetDateTime dataInicio) { this.dataInicio = dataInicio; }
    public OffsetDateTime getDataTermino() { return dataTermino; }
    public void setDataTermino(OffsetDateTime dataTermino) { this.dataTermino = dataTermino; }
}
