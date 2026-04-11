package guilda.aventureiros.dto;

import java.math.BigDecimal;

public class RankingAventureiroDTO {
    private Long aventureiroId;
    private String nomeAventureiro;
    private Long totalParticipacoes;
    private BigDecimal totalRecompensas;
    private Long quantidadeDestaques;

    public RankingAventureiroDTO(Long aventureiroId, String nomeAventureiro, Long totalParticipacoes,
                                 BigDecimal totalRecompensas, Long quantidadeDestaques) {
        this.aventureiroId = aventureiroId;
        this.nomeAventureiro = nomeAventureiro;
        this.totalParticipacoes = totalParticipacoes;
        this.totalRecompensas = totalRecompensas == null ? BigDecimal.ZERO : totalRecompensas;
        this.quantidadeDestaques = quantidadeDestaques;
    }

    public Long getAventureiroId() { return aventureiroId; }
    public void setAventureiroId(Long aventureiroId) { this.aventureiroId = aventureiroId; }
    public String getNomeAventureiro() { return nomeAventureiro; }
    public void setNomeAventureiro(String nomeAventureiro) { this.nomeAventureiro = nomeAventureiro; }
    public Long getTotalParticipacoes() { return totalParticipacoes; }
    public void setTotalParticipacoes(Long totalParticipacoes) { this.totalParticipacoes = totalParticipacoes; }
    public BigDecimal getTotalRecompensas() { return totalRecompensas; }
    public void setTotalRecompensas(BigDecimal totalRecompensas) { this.totalRecompensas = totalRecompensas; }
    public Long getQuantidadeDestaques() { return quantidadeDestaques; }
    public void setQuantidadeDestaques(Long quantidadeDestaques) { this.quantidadeDestaques = quantidadeDestaques; }
}
