package guilda.aventureiros.servico;

import guilda.aventureiros.dominio.Aventureiro;
import guilda.aventureiros.dominio.Classe;
import guilda.aventureiros.dominio.Companheiro;
import guilda.aventureiros.dominio.ParticipacaoMissao;
import guilda.aventureiros.dto.AventureiroDetalheDTO;
import guilda.aventureiros.dto.AventureiroRequestDTO;
import guilda.aventureiros.dto.AventureiroResumoDTO;
import guilda.aventureiros.dto.CompanheiroRequestDTO;
import guilda.aventureiros.excecoes.AventureiroNaoEncontradoException;
import guilda.aventureiros.repositorio.AventureiroRepository;
import guilda.aventureiros.repositorio.ParticipacaoMissaoRepository;
import guilda.seguranca.dominio.Organizacao;
import guilda.seguranca.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AventureiroService {

    private final AventureiroRepository aventureiroRepository;
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    @Autowired
    public AventureiroService(AventureiroRepository aventureiroRepository, ParticipacaoMissaoRepository participacaoMissaoRepository) {
        this.aventureiroRepository = aventureiroRepository;
        this.participacaoMissaoRepository = participacaoMissaoRepository;
    }

    public AventureiroResumoDTO registrar(AventureiroRequestDTO dto) {
        Aventureiro aventureiro = new Aventureiro();
        aventureiro.setNome(dto.getNome());
        aventureiro.setClasse(dto.getClasse());
        aventureiro.setNivel(dto.getNivel());
        aventureiro.setAtivo(true);
        
        Organizacao org = new Organizacao(); org.setId(1L);
        Usuario usuario = new Usuario(); usuario.setId(1L);
        aventureiro.setOrganizacao(org);
        aventureiro.setUsuarioCadastro(usuario);

        Aventureiro salvo = aventureiroRepository.save(aventureiro);
        return new AventureiroResumoDTO(salvo);
    }

    public Page<AventureiroResumoDTO> buscarFiltrado(String nome, Classe classe, Boolean ativo, Integer minNivel, Pageable pageable) {
        return aventureiroRepository.buscarFiltrado(nome, classe, ativo, minNivel, pageable)
                .map(AventureiroResumoDTO::new);
    }

    public AventureiroDetalheDTO buscarPorId(Long id) {
        Aventureiro a = aventureiroRepository.findById(id)
                .orElseThrow(() -> new AventureiroNaoEncontradoException("Aventureiro não encontrado com id " + id));
        
        Long totalMissoes = participacaoMissaoRepository.countByAventureiroId(id);
        Optional<ParticipacaoMissao> ultimaMissaoOpt = participacaoMissaoRepository.findFirstByAventureiroIdOrderByDataRegistroDesc(id);
        String ultimaMissaoTitulo = ultimaMissaoOpt.map(pm -> pm.getMissao().getTitulo()).orElse(null);
        
        return new AventureiroDetalheDTO(a, totalMissoes, ultimaMissaoTitulo);
    }

    public AventureiroResumoDTO atualizar(Long id, AventureiroRequestDTO dto) {
        Aventureiro a = aventureiroRepository.findById(id)
                .orElseThrow(() -> new AventureiroNaoEncontradoException("Aventureiro não encontrado com id " + id));
        a.setNome(dto.getNome());
        a.setClasse(dto.getClasse());
        a.setNivel(dto.getNivel());
        return new AventureiroResumoDTO(aventureiroRepository.save(a));
    }

    public void inativar(Long id) {
        Aventureiro a = aventureiroRepository.findById(id)
                .orElseThrow(() -> new AventureiroNaoEncontradoException("Aventureiro não encontrado com id " + id));
        a.setAtivo(false);
        aventureiroRepository.save(a);
    }

    public void recrutar(Long id) {
        Aventureiro a = aventureiroRepository.findById(id)
                .orElseThrow(() -> new AventureiroNaoEncontradoException("Aventureiro não encontrado com id " + id));
        a.setAtivo(true);
        aventureiroRepository.save(a);
    }
    
    public AventureiroDetalheDTO definirCompanheiro(Long id, CompanheiroRequestDTO dto) {
        Aventureiro a = aventureiroRepository.findById(id)
                .orElseThrow(() -> new AventureiroNaoEncontradoException("Aventureiro não encontrado com id " + id));
        Companheiro c = new Companheiro();
        c.setNome(dto.getNome());
        c.setEspecie(dto.getEspecie());
        c.setIndiceLealdade(dto.getLealdade());
        
        c.setAventureiro(a);
        a.setCompanheiro(c);
        aventureiroRepository.save(a);
        
        Long totalMissoes = participacaoMissaoRepository.countByAventureiroId(id);
        Optional<ParticipacaoMissao> ultimaMissaoOpt = participacaoMissaoRepository.findFirstByAventureiroIdOrderByDataRegistroDesc(id);
        String ultimaMissaoTitulo = ultimaMissaoOpt.map(pm -> pm.getMissao().getTitulo()).orElse(null);
        
        return new AventureiroDetalheDTO(a, totalMissoes, ultimaMissaoTitulo);
    }
    
    public void removerCompanheiro(Long id) {
        Aventureiro a = aventureiroRepository.findById(id)
                .orElseThrow(() -> new AventureiroNaoEncontradoException("Aventureiro não encontrado com id " + id));
        if (a.getCompanheiro() != null) {
            a.getCompanheiro().setAventureiro(null);
        }
        a.setCompanheiro(null);
        aventureiroRepository.save(a);
    }
}
