package guilda.aventureiros.dto;

import guilda.aventureiros.dominio.Aventureiro;
import guilda.aventureiros.dominio.Classe;
import guilda.aventureiros.dominio.Companheiro;

public class AventureiroDetalheDTO {
    private Long id;
    private String nome;
    private Classe classe;
    private Integer nivel;
    private boolean ativo;
    private Companheiro companheiro;
    private Long totalMissoes;
    private String ultimaMissaoTitulo;

    public AventureiroDetalheDTO(Aventureiro aventureiro, Long totalMissoes, String ultimaMissaoTitulo) {
        this.id = aventureiro.getId();
        this.nome = aventureiro.getNome();
        this.classe = aventureiro.getClasse();
        this.nivel = aventureiro.getNivel();
        this.ativo = aventureiro.getAtivo();
        this.companheiro = aventureiro.getCompanheiro();
        this.totalMissoes = totalMissoes;
        this.ultimaMissaoTitulo = ultimaMissaoTitulo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Classe getClasse() {
        return classe;
    }

    public Integer getNivel() {
        return nivel;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Companheiro getCompanheiro() {
        return companheiro;
    }

    public Long getTotalMissoes() {
        return totalMissoes;
    }

    public String getUltimaMissaoTitulo() {
        return ultimaMissaoTitulo;
    }
}
