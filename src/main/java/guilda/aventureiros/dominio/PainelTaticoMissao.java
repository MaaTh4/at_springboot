package guilda.aventureiros.dominio;

import org.hibernate.annotations.Immutable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Immutable
@Table(name = "vw_painel_tatico", schema = "operacoes")
public class PainelTaticoMissao {

    @Id
    @Column(name = "missao_id")
    private Long missaoId;

    private String titulo;

    @Enumerated(EnumType.STRING)
    private StatusMissao status;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_perigo")
    private NivelPerigo nivelPerigo;

    @Column(name = "organizacao_id")
    private Long organizacaoId;

    @Column(name = "total_participantes")
    private Integer totalParticipantes;

    @Column(name = "nivel_medio_equipe")
    private Double nivelMedioEquipe;

    @Column(name = "total_recompensa")
    private BigDecimal totalRecompensa;

    @Column(name = "total_mvps")
    private Integer totalMvps;

    @Column(name = "participantes_com_companheiro")
    private Integer participantesComCompanheiro;

    @Column(name = "ultima_atualizacao")
    private OffsetDateTime ultimaAtualizacao;

    @Column(name = "indice_prontidao")
    private Double indiceProntidao;

    protected PainelTaticoMissao() {
    }

    public Long getMissaoId() {
        return missaoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public StatusMissao getStatus() {
        return status;
    }

    public NivelPerigo getNivelPerigo() {
        return nivelPerigo;
    }

    public Long getOrganizacaoId() {
        return organizacaoId;
    }

    public Integer getTotalParticipantes() {
        return totalParticipantes;
    }

    public Double getNivelMedioEquipe() {
        return nivelMedioEquipe;
    }

    public BigDecimal getTotalRecompensa() {
        return totalRecompensa;
    }

    public Integer getTotalMvps() {
        return totalMvps;
    }

    public Integer getParticipantesComCompanheiro() {
        return participantesComCompanheiro;
    }

    public OffsetDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public Double getIndiceProntidao() {
        return indiceProntidao;
    }
}
