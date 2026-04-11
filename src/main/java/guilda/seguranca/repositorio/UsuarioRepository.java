package guilda.seguranca.repositorio;

import guilda.seguranca.dominio.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @EntityGraph(attributePaths = {"userRoles", "userRoles.role"})
    List<Usuario> findAll();
}
