package me.dionclei.dslist.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dionclei.dslist.dto.GameDTO;
import me.dionclei.dslist.dto.GameMinDTO;
import me.dionclei.dslist.dto.RequestUpdateGame;
import me.dionclei.dslist.entities.Game;
import me.dionclei.dslist.projections.GameMinProjection;
import me.dionclei.dslist.repositories.GameRepository;
import me.dionclei.dslist.services.exceptions.ResourceNotFoundException;

@Service
public class GameService {

    private GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    @Cacheable("allGames")
    @Transactional(readOnly = true)
    public List<GameMinDTO> findAll() {
        return repository.findAll().stream().map(GameMinDTO::new).toList();
    }

    @Cacheable(value = "gamesByList", key = "#listId")
    @Transactional(readOnly = true)
    public List<GameMinDTO> findByList(Long listId) {
        List<GameMinProjection> result = repository.searchByList(listId);
        if (result.isEmpty()) throw new ResourceNotFoundException("List not found");
        return result.stream().map(GameMinDTO::new).toList();
    }

    @Cacheable(value = "gamesByListPaged", key = "#listId + '-' + #skip")
    @Transactional(readOnly = true)
    public List<GameMinDTO> findByList(Long listId, Integer skip) {
        List<GameMinProjection> result = repository.searchByList(listId, skip);
        if (result.isEmpty()) throw new ResourceNotFoundException("List not found");
        return result.stream().map(GameMinDTO::new).toList();
    }

    @Cacheable("gamesCount")
    @Transactional(readOnly = true)
    public int countGames() {
        return repository.countGames();
    }

    @Cacheable("countByList")
    @Transactional(readOnly = true)
    public int countByList(Long listId) {
        return repository.countByList(listId);
    }

    @Cacheable(value = "gameById", key = "#id")
    @Transactional(readOnly = true)
    public GameDTO findById(Long id) {
        var result = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found"));
        return new GameDTO(result);
    }

    @Cacheable(value = "gamesPaged", key = "#page")
    public List<GameMinDTO> findAll(Integer page) {
        return repository.findAllPaged(page).stream().map(GameMinDTO::new).toList();
    }

    @CacheEvict(value = { "allGames", "gamesByList", "gamesPaged", "gamesByListPaged", "gamesCount" }, allEntries = true)
    @Transactional
    public GameDTO save(Game game) {
        return new GameDTO(repository.save(game));
    }

    @CachePut(value = "gameById", key = "#id")
    @CacheEvict(value = { "allGames", "gamesByList", "gamesPaged", "gamesByListPaged" }, allEntries = true)
    @Transactional
    public GameDTO update(Long id, RequestUpdateGame gameRequest) {
        Game game = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
        BeanUtils.copyProperties(fillNulls(gameRequest, game), game, "id");
        game = repository.save(game);
        return new GameDTO(game);
    }

    @CacheEvict(value = { "gameById", "allGames", "gamesByList", "gamesPaged", "gamesByListPaged", "gamesCount" }, allEntries = true)
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Game not found");
        }
        repository.deleteById(id);
    }

    private RequestUpdateGame fillNulls(RequestUpdateGame request, Game game) {
        return new RequestUpdateGame(
                request.title() != null ? request.title() : game.getTitle(),
                request.year() != null ? request.year() : game.getYear(),
                request.genre() != null ? request.genre() : game.getGenre(),
                request.platforms() != null ? request.platforms() : game.getPlatforms(),
                request.score() != null ? request.score() : game.getScore(),
                request.imgUrl() != null ? request.imgUrl() : game.getImgUrl(),
                request.shortDescription() != null ? request.shortDescription() : game.getShortDescription(),
                request.longDescription() != null ? request.longDescription() : game.getLongDescription()
        );
    }
}