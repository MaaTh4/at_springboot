package guilda.aventureiros.dto;

import java.util.List;

public class ErroPadraoResposta {
    private String mensagem;
    private List<String> detalhes;

    public ErroPadraoResposta() {}

    public ErroPadraoResposta(String mensagem, List<String> detalhes) {
        this.mensagem = mensagem;
        this.detalhes = detalhes;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<String> getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(List<String> detalhes) {
        this.detalhes = detalhes;
    }
}
