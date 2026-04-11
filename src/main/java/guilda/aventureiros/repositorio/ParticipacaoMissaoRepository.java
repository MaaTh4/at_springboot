package guilda.aventureiros.repositorio;

import guilda.aventureiros.dominio.ParticipacaoMissao;
import guilda.aventureiros.dominio.StatusMissao;
import guilda.aventureiros.dto.RankingAventureiroDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

import java.util.Optional;

@Repository
public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, Long> {

    Long countByAventureiroId(Long aventureiroId);

    Optional<ParticipacaoMissao> findFirstByAventureiroIdOrderByDataRegistroDesc(Long aventureiroId);

    @Query("SELECT new guilda.aventureiros.dto.RankingAventureiroDTO(" +
           "a.id, a.nome, " +
           "COUNT(p.id), " +
           "SUM(p.recompensaOuro), " +
           "COUNT(CASE WHEN p.destaqueMvp = true THEN 1 END)) " +
           "FROM ParticipacaoMissao p " +
           "JOIN p.aventureiro a " +
           "JOIN p.missao m " +
           "WHERE (CAST(:dataInicio AS java.time.OffsetDateTime) IS NULL OR p.dataRegistro >= :dataInicio) " +
           "AND (CAST(:dataTermino AS java.time.OffsetDateTime) IS NULL OR p.dataRegistro <= :dataTermino) " +
           "AND (:statusMissao IS NULL OR m.status = :statusMissao) " +
           "GROUP BY a.id, a.nome " +
           "ORDER BY COUNT(p.id) DESC, SUM(p.recompensaOuro) DESC")
    List<RankingAventureiroDTO> obterRankingAventureiros(
            @Param("dataInicio") OffsetDateTime dataInicio,
            @Param("dataTermino") OffsetDateTime dataTermino,
            @Param("statusMissao") StatusMissao statusMissao);
}
