package guilda.aventureiros.dto;

import guilda.aventureiros.dominio.Classe;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AventureiroRequestDTO {

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @NotNull(message = "A classe não pode ser nula")
    private Classe classe;

    @NotNull(message = "O nível não pode ser nulo")
    @Min(value = 1, message = "O nível mínimo é 1")
    private Integer nivel;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
}
