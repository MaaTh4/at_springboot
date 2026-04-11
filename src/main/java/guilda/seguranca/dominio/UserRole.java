package guilda.seguranca.dominio;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_roles", schema = "audit")
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "granted_at")
    private OffsetDateTime grantedAt;

    public UserRole() {}

    public UserRole(Usuario usuario, Role role, OffsetDateTime grantedAt) {
        this.usuario = usuario;
        this.role = role;
        this.id = new UserRoleId(usuario.getId(), role.getId());
        this.grantedAt = grantedAt;
    }

    public UserRoleId getId() { return id; }
    public void setId(UserRoleId id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public OffsetDateTime getGrantedAt() { return grantedAt; }
    public void setGrantedAt(OffsetDateTime grantedAt) { this.grantedAt = grantedAt; }
}
