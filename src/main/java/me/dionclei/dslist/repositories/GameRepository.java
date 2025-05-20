package me.dionclei.dslist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.dionclei.dslist.entities.Game;
import me.dionclei.dslist.projections.GameMinProjection;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
	
	@Query(nativeQuery = true, value = """
			SELECT tb_game.id, tb_game.title, tb_game.game_year AS `year`, tb_game.img_url AS imgUrl,
			tb_game.short_description AS shortDescription, tb_belonging.position
			FROM tb_game
			INNER JOIN tb_belonging ON tb_game.id = tb_belonging.game_id
			WHERE tb_belonging.list_id = :listId
			ORDER BY tb_belonging.position
				""")
	List<GameMinProjection> searchByList(Long listId);
	
    @Query(nativeQuery = true, value = """
            SELECT COUNT(*)
            FROM tb_game
            INNER JOIN tb_belonging ON tb_game.id = tb_belonging.game_id
            WHERE tb_belonging.list_id = :listId
        """)
    int countByList(@Param("listId") Long listId);
    
    @Query(nativeQuery = true, value = """
    		SELECT *
    		FROM tb_game
    		LIMIT 10
    		OFFSET :skipGames
    	""")
    List<Game> findAllPaged(Integer skipGames);
    		
    @Query(nativeQuery = true, value = """
            SELECT COUNT(*)
            FROM tb_game
        """)
    int countGames();

	
	@Query(nativeQuery = true, value = """
			SELECT tb_game.id, tb_game.title, tb_game.game_year AS `year`, tb_game.img_url AS imgUrl,
			tb_game.short_description AS shortDescription, tb_belonging.position
			FROM tb_game
			INNER JOIN tb_belonging ON tb_game.id = tb_belonging.game_id
			WHERE tb_belonging.list_id = :listId
			ORDER BY tb_belonging.position LIMIT 10 OFFSET :skipGames
		""")
	List<GameMinProjection> searchByList(Long listId, Integer skipGames);
	
}
