package guilda.seguranca.controle;

import guilda.seguranca.dominio.Role;
import guilda.seguranca.dominio.Usuario;
import guilda.seguranca.dto.UsuarioRequestDTO;
import guilda.seguranca.servico.SegurancaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seguranca")
public class SegurancaController {

    private final SegurancaService service;

    @Autowired
    public SegurancaController(SegurancaService service) {
        this.service = service;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(service.listarUsuarios());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> listarRoles() {
        return ResponseEntity.ok(service.listarRoles());
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(service.persistirUsuario(dto));
    }
}
