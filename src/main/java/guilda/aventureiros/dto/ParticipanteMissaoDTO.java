package guilda.aventureiros.dto;

import guilda.aventureiros.dominio.PapelMissao;
import java.math.BigDecimal;

public class ParticipanteMissaoDTO {
    private String nomeAventureiro;
    private PapelMissao papel;
    private BigDecimal recompensaOuro;
    private Boolean destaqueMvp;

    public ParticipanteMissaoDTO() {}

    public ParticipanteMissaoDTO(String nomeAventureiro, PapelMissao papel, BigDecimal recompensaOuro, Boolean destaqueMvp) {
        this.nomeAventureiro = nomeAventureiro;
        this.papel = papel;
        this.recompensaOuro = recompensaOuro;
        this.destaqueMvp = destaqueMvp;
    }

    public String getNomeAventureiro() { return nomeAventureiro; }
    public void setNomeAventureiro(String nomeAventureiro) { this.nomeAventureiro = nomeAventureiro; }
    public PapelMissao getPapel() { return papel; }
    public void setPapel(PapelMissao papel) { this.papel = papel; }
    public BigDecimal getRecompensaOuro() { return recompensaOuro; }
    public void setRecompensaOuro(BigDecimal recompensaOuro) { this.recompensaOuro = recompensaOuro; }
    public Boolean getDestaqueMvp() { return destaqueMvp; }
    public void setDestaqueMvp(Boolean destaqueMvp) { this.destaqueMvp = destaqueMvp; }
}
