package guilda.aventureiros.dto;

import guilda.aventureiros.dominio.Especie;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CompanheiroRequestDTO {

    @NotBlank(message = "O nome do companheiro não pode estar em branco")
    private String nome;

    @NotNull(message = "A espécie do companheiro não pode ser nula")
    private Especie especie;

    @NotNull(message = "A lealdade não pode ser nula")
    @Min(value = 0, message = "A lealdade mínima é 0")
    @Max(value = 100, message = "A lealdade máxima é 100")
    private Integer lealdade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Integer getLealdade() {
        return lealdade;
    }

    public void setLealdade(Integer lealdade) {
        this.lealdade = lealdade;
    }
}
