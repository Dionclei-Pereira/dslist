package me.dionclei.dslist.repositories;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import me.dionclei.dslist.entities.Game;
import me.dionclei.dslist.projections.GameMinProjection;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class GameRepositoryTests {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Test
	@DisplayName("Should not be empty")
	void searchByListCase1() {
		List<GameMinProjection> games = gameRepository.searchByList(1L);
		assertThat(games).isNotEmpty();
		assertThat(games.size()).isGreaterThan(0);
	}
	
	@Test
	@DisplayName("Should be empty")
	void searchByListCase2() {
		List<GameMinProjection> games = gameRepository.searchByList(99L);
		assertThat(games).isEmpty();
	}
	
	@Test
	@DisplayName("Should not be empty")
	void searchByListSkippingCase1() {
		List<GameMinProjection> games = gameRepository.searchByList(1L, 4);
		assertThat(games).isNotEmpty();
	}
	
	@Test
	@DisplayName("Should be empty")
	void searchByListSkippingCase2() {
		List<GameMinProjection> games = gameRepository.searchByList(99L, 5);
		assertThat(games).isEmpty();
	}
	
    @Test
    @DisplayName("shouldr return game count for existing list")
    void countByListCase1() {
        int count = gameRepository.countByList(1L);
        assertThat(count).isGreaterThan(0);
    }

    @Test
    @DisplayName("should return zero when list does not exist")
    void countByListCase2() {
        int count = gameRepository.countByList(99L);
        assertThat(count).isEqualTo(0);
    }

    @Test
    @DisplayName("should return ten games when paged")
    void findAllPagedCase1() {
        List<Game> games = gameRepository.findAllPaged(0);
        assertThat(games).hasSizeLessThanOrEqualTo(10);
        assertThat(games).isNotEmpty();
    }

    @Test
    @DisplayName("should return game count")
    void countGamesCase1() {
        int count = gameRepository.countGames();
        assertThat(count).isGreaterThan(0);
    }
}
