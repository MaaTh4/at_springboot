package guilda.aventureiros.repositorio;

import guilda.aventureiros.dominio.Missao;
import guilda.aventureiros.dominio.NivelPerigo;
import guilda.aventureiros.dominio.StatusMissao;
import guilda.aventureiros.dto.MissaoMetricasDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Long> {

    @Query("SELECT m FROM Missao m WHERE " +
           "(:status IS NULL OR m.status = :status) AND " +
           "(:nivelPerigo IS NULL OR m.nivelPerigo = :nivelPerigo) AND " +
           "(CAST(:dataInicio AS java.time.OffsetDateTime) IS NULL OR m.dataInicio >= :dataInicio OR (m.dataInicio IS NULL AND m.createdAt >= :dataInicio)) AND " +
           "(CAST(:dataTermino AS java.time.OffsetDateTime) IS NULL OR m.dataInicio <= :dataTermino OR (m.dataInicio IS NULL AND m.createdAt <= :dataTermino))")
    Page<Missao> buscarFiltrado(
            @Param("status") StatusMissao status,
            @Param("nivelPerigo") NivelPerigo nivelPerigo,
            @Param("dataInicio") OffsetDateTime dataInicio,
            @Param("dataTermino") OffsetDateTime dataTermino,
            Pageable pageable);

    @Query("SELECT m FROM Missao m LEFT JOIN FETCH m.participacoes p LEFT JOIN FETCH p.aventureiro WHERE m.id = :id")
    Optional<Missao> findByIdComParticipantesEAventureiros(@Param("id") Long id);

    @Query("SELECT new guilda.aventureiros.dto.MissaoMetricasDTO(" +
           "m.titulo, m.status, m.nivelPerigo, COUNT(DISTINCT p.id), SUM(p.recompensaOuro)) " +
           "FROM Missao m " +
           "LEFT JOIN m.participacoes p " +
           "WHERE (CAST(:dataInicio AS java.time.OffsetDateTime) IS NULL OR m.dataInicio >= :dataInicio OR (m.dataInicio IS NULL AND m.createdAt >= :dataInicio)) " +
           "AND (CAST(:dataTermino AS java.time.OffsetDateTime) IS NULL OR m.dataInicio <= :dataTermino OR (m.dataInicio IS NULL AND m.createdAt <= :dataTermino)) " +
           "GROUP BY m.id, m.titulo, m.status, m.nivelPerigo " +
           "ORDER BY m.titulo ASC")
    List<MissaoMetricasDTO> obterMetricas(
            @Param("dataInicio") OffsetDateTime dataInicio,
            @Param("dataTermino") OffsetDateTime dataTermino);
}
