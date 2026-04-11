package guilda.aventureiros.dominio;

import jakarta.persistence.*;

@Entity
@Table(name = "companheiros", schema = "aventura")
public class Companheiro {

    @Id
    private Long aventureiroId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "aventureiro_id")
    private Aventureiro aventureiro;

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especie especie;

    @Column(name = "indice_lealdade", nullable = false)
    private Integer indiceLealdade;

    // Construtor
    public Companheiro() {
    }

    // Getters and Setters
    public Long getAventureiroId() { return aventureiroId; }
    public void setAventureiroId(Long aventureiroId) { this.aventureiroId = aventureiroId; }
    public Aventureiro getAventureiro() { return aventureiro; }
    public void setAventureiro(Aventureiro aventureiro) { this.aventureiro = aventureiro; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Especie getEspecie() { return especie; }
    public void setEspecie(Especie especie) { this.especie = especie; }
    public Integer getIndiceLealdade() { return indiceLealdade; }
    public void setIndiceLealdade(Integer indiceLealdade) { this.indiceLealdade = indiceLealdade; }
}
