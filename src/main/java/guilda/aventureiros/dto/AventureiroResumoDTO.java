package guilda.aventureiros.dto;

import guilda.aventureiros.dominio.Aventureiro;
import guilda.aventureiros.dominio.Classe;

public class AventureiroResumoDTO {
    private Long id;
    private String nome;
    private Classe classe;
    private Integer nivel;
    private boolean ativo;

    private String nomeCompanheiro;

    public AventureiroResumoDTO() {}

    public AventureiroResumoDTO(Aventureiro aventureiro) {
        this.id = aventureiro.getId();
        this.nome = aventureiro.getNome();
        this.classe = aventureiro.getClasse();
        this.nivel = aventureiro.getNivel();
        this.ativo = aventureiro.getAtivo();
        if (aventureiro.getCompanheiro() != null) {
            this.nomeCompanheiro = aventureiro.getCompanheiro().getNome() + " (" + aventureiro.getCompanheiro().getEspecie() + ")";
        }
    }

    public String getNomeCompanheiro() {
        return nomeCompanheiro;
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
}
