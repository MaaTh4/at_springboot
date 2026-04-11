package guilda.seguranca.servico;

import guilda.seguranca.dominio.Organizacao;
import guilda.seguranca.dominio.Role;
import guilda.seguranca.dominio.Usuario;
import guilda.seguranca.dto.UsuarioRequestDTO;
import guilda.seguranca.repositorio.OrganizacaoRepository;
import guilda.seguranca.repositorio.RoleRepository;
import guilda.seguranca.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class SegurancaService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final OrganizacaoRepository organizacaoRepository;

    @Autowired
    public SegurancaService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, OrganizacaoRepository organizacaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.organizacaoRepository = organizacaoRepository;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Role> listarRoles() {
        return roleRepository.findAll();
    }

    public Usuario persistirUsuario(UsuarioRequestDTO dto) {
        Organizacao org = organizacaoRepository.findById(dto.getOrganizacaoId())
                .orElseThrow(() -> new RuntimeException("Organização não encontrada"));
        
        Usuario u = new Usuario();
        u.setNome(dto.getNome());
        u.setEmail(dto.getEmail());
        u.setOrganizacao(org);
        u.setStatus("ATIVO");
        u.setCreatedAt(OffsetDateTime.now());
        
        return usuarioRepository.save(u);
    }
}
