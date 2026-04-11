package guilda.aventureiros.repositorio;

import guilda.aventureiros.dominio.Aventureiro;
import guilda.aventureiros.dominio.Classe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    @Query("SELECT a FROM Aventureiro a WHERE " +
           "(CAST(:nome AS String) IS NULL OR LOWER(a.nome) LIKE LOWER(CONCAT('%', CAST(:nome AS String), '%'))) AND " +
           "(:classe IS NULL OR a.classe = :classe) AND " +
           "(:ativo IS NULL OR a.ativo = :ativo) AND " +
           "(:minNivel IS NULL OR a.nivel >= :minNivel)")
    Page<Aventureiro> buscarFiltrado(
            @Param("nome") String nome,
            @Param("classe") Classe classe, 
            @Param("ativo") Boolean ativo, 
            @Param("minNivel") Integer minNivel, 
            Pageable pageable);
}
