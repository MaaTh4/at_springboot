package guilda.aventureiros.dominio;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "participacoes_missao", schema = "aventura", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"missao_id", "aventureiro_id"})
})
public class ParticipacaoMissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PapelMissao papel;

    @Column(name = "recompensa_ouro", precision = 10, scale = 2)
    private BigDecimal recompensaOuro;

    @Column(name = "destaque_mvp", nullable = false)
    private Boolean destaqueMvp;

    @Column(name = "data_registro", nullable = false, updatable = false)
    private OffsetDateTime dataRegistro;

    public ParticipacaoMissao() {
    }

    @PrePersist
    protected void onCreate() {
        this.dataRegistro = OffsetDateTime.now();
        if (this.destaqueMvp == null) {
            this.destaqueMvp = false;
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Missao getMissao() { return missao; }
    public void setMissao(Missao missao) { this.missao = missao; }
    public Aventureiro getAventureiro() { return aventureiro; }
    public void setAventureiro(Aventureiro aventureiro) { this.aventureiro = aventureiro; }
    public PapelMissao getPapel() { return papel; }
    public void setPapel(PapelMissao papel) { this.papel = papel; }
    public BigDecimal getRecompensaOuro() { return recompensaOuro; }
    public void setRecompensaOuro(BigDecimal recompensaOuro) { this.recompensaOuro = recompensaOuro; }
    public Boolean getDestaqueMvp() { return destaqueMvp; }
    public void setDestaqueMvp(Boolean destaqueMvp) { this.destaqueMvp = destaqueMvp; }
    public OffsetDateTime getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(OffsetDateTime dataRegistro) { this.dataRegistro = dataRegistro; }
}
