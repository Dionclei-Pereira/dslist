package me.dionclei.dslist.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import me.dionclei.dslist.dto.RequestCreateGameList;
import me.dionclei.dslist.entities.Game;
import me.dionclei.dslist.entities.GameList;
import me.dionclei.dslist.projections.GameMinProjection;
import me.dionclei.dslist.repositories.BelongingRepository;
import me.dionclei.dslist.repositories.GameListRepository;
import me.dionclei.dslist.repositories.GameRepository;
import me.dionclei.dslist.services.exceptions.ResourceNotFoundException;

public class GameListServiceTests {

    @Mock
    private GameListRepository repository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private BelongingRepository belongingRepository;
    
	@Autowired
    @InjectMocks
    private GameListService service;
    
    @BeforeEach
    void setup() {
    	MockitoAnnotations.openMocks(this);
    }
    
    @Test
    @DisplayName("Should find all game lists")
    void findAllCase() {
        GameList list1 = new GameList(1L, "RPGs");
        GameList list2 = new GameList(2L, "Platformers");

        when(repository.findAll()).thenReturn(List.of(list1, list2));

        var result = service.findAll();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should save a game list")
    void saveCase() {
        GameList list = new GameList(null, "New List");
        GameList saved = new GameList(1L, "New List");

        when(repository.save(any())).thenReturn(saved);

        var result = service.save(list);

        assertEquals("New List", result.getName());
        verify(repository, times(1)).save(list);
    }

    @Test
    @DisplayName("Should throw when adding game with nonexistent list")
    void addGameListNotFound() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(new Game()));
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.addGame(1L, 1L));
    }

    @Test
    @DisplayName("Should throw when adding nonexistent game")
    void addGameGameNotFound() {
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.addGame(1L, 1L));
    }

    @Test
    @DisplayName("Should update a game list")
    void updateCase() {
        GameList list = new GameList(1L, "Old Name");
        RequestCreateGameList req = new RequestCreateGameList("Updated Name");

        when(repository.findById(1L)).thenReturn(Optional.of(list));
        when(repository.save(any())).thenReturn(list);

        var result = service.update(1L, req);

        assertEquals("Updated Name", result.getName());
        verify(repository, times(1)).save(list);
    }

    @Test
    @DisplayName("Should throw when updating nonexistent game list")
    void updateNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, new RequestCreateGameList("")));
    }

    @Test
    @DisplayName("Should delete a game list")
    void deleteCase() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw when deleting nonexistent game list")
    void deleteNotFoundCase() {
        when(repository.existsById(1L)).thenReturn(false);

        var ex = assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
        assertEquals("Game list not found", ex.getMessage());
    }

    @Test
    @DisplayName("Should move game position")
    void moveCase() {
        GameMinProjection g1 = projection(1L);
        GameMinProjection g2 = projection(2L);

        List<GameMinProjection> list = new ArrayList<>(List.of(g1, g2));

        when(gameRepository.searchByList(1L)).thenReturn(list);

        service.move(1L, 0, 1);

        verify(repository, times(1)).updateBelongingPosition(1L, g1.getId(), 1);
        verify(repository, times(1)).updateBelongingPosition(1L, g2.getId(), 0);
    }

    @Test
    @DisplayName("Should throw when moving with invalid index")
    void moveIndexOutOfBounds() {
        when(gameRepository.searchByList(1L)).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> service.move(1L, 0, 1));
    }

    private GameMinProjection projection(Long id) {
        return new GameMinProjection() {
        	
            public Long getId() { return id; }
            public String getTitle() { return "Game " + id; }
            public Integer getGameYear() { return 2020; }
            public String getImgUrl() { return ""; }
            public String getShortDescription() { return ""; }
            public Integer getPosition() { return 0; }
        };
    }
}