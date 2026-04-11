package guilda.seguranca.dominio;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "audit_entries", schema = "audit")
public class AuditEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organizacao_id")
    private Long organizacaoId;

    @Column(name = "actor_user_id")
    private Long actorUserId;

    @Column(name = "actor_api_key_id")
    private Long actorApiKeyId;

    private String action;

    @Column(name = "entity_schema")
    private String entitySchema;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "occurred_at")
    private OffsetDateTime occurredAt;

    // Type inet in postgres, can be mapped as string
    private String ip;

    @Column(name = "user_agent")
    private String userAgent;

    // Type jsonb mapped as String or custom dialect. Usually String is safe if
    // wrapped by hibernate native functions or mapped to Object.
    @Column(columnDefinition = "jsonb")
    private String diff;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    private Boolean success;

    // Getters and Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrganizacaoId() { return organizacaoId; }
    public void setOrganizacaoId(Long organizacaoId) { this.organizacaoId = organizacaoId; }
    public Long getActorUserId() { return actorUserId; }
    public void setActorUserId(Long actorUserId) { this.actorUserId = actorUserId; }
    public Long getActorApiKeyId() { return actorApiKeyId; }
    public void setActorApiKeyId(Long actorApiKeyId) { this.actorApiKeyId = actorApiKeyId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getEntitySchema() { return entitySchema; }
    public void setEntitySchema(String entitySchema) { this.entitySchema = entitySchema; }
    public String getEntityName() { return entityName; }
    public void setEntityName(String entityName) { this.entityName = entityName; }
    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }
    public OffsetDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(OffsetDateTime occurredAt) { this.occurredAt = occurredAt; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public String getDiff() { return diff; }
    public void setDiff(String diff) { this.diff = diff; }
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }
}
