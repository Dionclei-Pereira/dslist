package me.dionclei.dslist.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import me.dionclei.dslist.dto.GameDTO;
import me.dionclei.dslist.dto.GameMinDTO;
import me.dionclei.dslist.dto.RequestUpdateGame;
import me.dionclei.dslist.entities.Game;
import me.dionclei.dslist.projections.GameMinProjection;
import me.dionclei.dslist.repositories.GameRepository;
import me.dionclei.dslist.services.exceptions.ResourceNotFoundException;

public class GameServiceTests {
	
	@Mock
	private GameRepository repository;
	
	@Autowired
	@InjectMocks
	private GameService service;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	@DisplayName("Should find all")
	void findAllCase() {
		Game game = mock(Game.class);
		
		when(repository.findAll()).thenReturn(Arrays.asList(game));
		
		service.findAll();
		
		verify(repository, times(1)).findAll();
	}
	
	@Test
	@DisplayName("Should delete a game")
	void deleteCase1() {
		when(repository.existsById(any())).thenReturn(true);
		
		service.delete(1L);
		
		verify(repository, times(1)).existsById(any());
		verify(repository, times(1)).deleteById(any());
	}
	
	@Test
	@DisplayName("Should throw an exception")
	void deleteCase2() {
		when(repository.existsById(any())).thenReturn(false);
		
		ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(1L);
		});
		
		verify(repository, times(1)).existsById(any());
		assertEquals("Game not found", ex.getMessage());
	}
	
	@Test
    @DisplayName("Should find games by list")
    void findByListCase1() {
        Long listId = 1L;
        GameMinProjection p1 = mock(GameMinProjection.class);
        when(repository.searchByList(listId)).thenReturn(List.of(p1));

        List<GameMinDTO> result = service.findByList(listId);

        verify(repository, times(1)).searchByList(listId);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Should throw exception when findByList returns empty")
    void findByListCase2() {
        Long listId = 99L;
        when(repository.searchByList(listId)).thenReturn(List.of());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.findByList(listId);
        });

        assertEquals("List not found", ex.getMessage());
        verify(repository, times(1)).searchByList(listId);
    }

    @Test
    @DisplayName("Should find games by list with paging")
    void findByListWithSkipCase1() {
        Long listId = 1L;
        Integer skip = 4;
        GameMinProjection p1 = mock(GameMinProjection.class);
        when(repository.searchByList(listId, skip)).thenReturn(List.of(p1));

        List<GameMinDTO> result = service.findByList(listId, skip);

        verify(repository, times(1)).searchByList(listId, skip);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Should throw exception when findByList with skip returns empty")
    void findByListWithSkipCase2() {
        Long listId = 99L;
        Integer skip = 5;
        when(repository.searchByList(listId, skip)).thenReturn(List.of());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.findByList(listId, skip);
        });

        assertEquals("List not found", ex.getMessage());
        verify(repository, times(1)).searchByList(listId, skip);
    }

    @Test
    @DisplayName("Should return total games count")
    void countGamesCase() {
        when(repository.countGames()).thenReturn(42);

        int count = service.countGames();

        verify(repository, times(1)).countGames();
        assertEquals(42, count);
    }

    @Test
    @DisplayName("Should return count of games by list")
    void countByListCase() {
        Long listId = 1L;
        when(repository.countByList(listId)).thenReturn(10);

        int count = service.countByList(listId);

        verify(repository, times(1)).countByList(listId);
        assertEquals(10, count);
    }

    @Test
    @DisplayName("Should find game by id")
    void findByIdCase1() {
        Long id = 1L;
        Game game = mock(Game.class);
        when(repository.findById(id)).thenReturn(Optional.of(game));

        GameDTO dto = service.findById(id);

        verify(repository, times(1)).findById(id);
        assertNotNull(dto);
    }

    @Test
    @DisplayName("Should throw exception when findById not found")
    void findByIdCase2() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(id);
        });

        assertEquals("Game not found", ex.getMessage());
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should find all games paged")
    void findAllPagedCase() {
        Integer page = 0;
        Game game = new Game();
        when(repository.findAllPaged(page)).thenReturn(List.of(game));

        List<GameMinDTO> list = service.findAll(page);

        verify(repository, times(1)).findAllPaged(page);
        assertFalse(list.isEmpty());
    }

    @Test
    @DisplayName("Should save a game and evict caches")
    void saveCase() {
        Game game = new Game();
        when(repository.save(game)).thenReturn(game);

        GameDTO dto = service.save(game);

        verify(repository, times(1)).save(game);
        assertNotNull(dto);
    }

    @Test
    @DisplayName("Should update a game")
    void updateCase1() {
        Long id = 1L;
        RequestUpdateGame request = mock(RequestUpdateGame.class);
        Game game = new Game();
        when(repository.findById(id)).thenReturn(Optional.of(game));
        when(repository.save(game)).thenReturn(game);

        GameDTO dto = service.update(id, request);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(game);
        assertNotNull(dto);
    }

    @Test
    @DisplayName("Should throw exception when updating game not found")
    void updateCase2() {
        Long id = 99L;
        RequestUpdateGame request = mock(RequestUpdateGame.class);
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(id, request);
        });

        assertEquals("Game not found", ex.getMessage());
        verify(repository, times(1)).findById(id);
    }
}
