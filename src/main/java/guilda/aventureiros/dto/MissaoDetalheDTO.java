package guilda.aventureiros.dto;

import guilda.aventureiros.dominio.NivelPerigo;
import guilda.aventureiros.dominio.StatusMissao;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class MissaoDetalheDTO {
    private Long id;
    private String titulo;
    private StatusMissao status;
    private NivelPerigo nivelPerigo;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataTermino;
    private List<ParticipanteMissaoDTO> participantes = new ArrayList<>();

    public MissaoDetalheDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public StatusMissao getStatus() { return status; }
    public void setStatus(StatusMissao status) { this.status = status; }
    public NivelPerigo getNivelPerigo() { return nivelPerigo; }
    public void setNivelPerigo(NivelPerigo nivelPerigo) { this.nivelPerigo = nivelPerigo; }
    public OffsetDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(OffsetDateTime dataInicio) { this.dataInicio = dataInicio; }
    public OffsetDateTime getDataTermino() { return dataTermino; }
    public void setDataTermino(OffsetDateTime dataTermino) { this.dataTermino = dataTermino; }
    public List<ParticipanteMissaoDTO> getParticipantes() { return participantes; }
    public void setParticipantes(List<ParticipanteMissaoDTO> participantes) { this.participantes = participantes; }
}
