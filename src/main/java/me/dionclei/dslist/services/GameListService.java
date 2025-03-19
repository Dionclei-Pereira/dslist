package me.dionclei.dslist.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dionclei.dslist.dto.GameListDTO;
import me.dionclei.dslist.dto.GameMinDTO;
import me.dionclei.dslist.dto.RequestCreateGameList;
import me.dionclei.dslist.entities.Belonging;
import me.dionclei.dslist.entities.GameList;
import me.dionclei.dslist.projections.GameMinProjection;
import me.dionclei.dslist.repositories.BelongingRepository;
import me.dionclei.dslist.repositories.GameListRepository;
import me.dionclei.dslist.repositories.GameRepository;
import me.dionclei.dslist.services.exceptions.DatabaseException;
import me.dionclei.dslist.services.exceptions.ResourceNotFoundException;

@Service
public class GameListService {
	
	private GameListRepository repository;
	private GameRepository gameRepository;
	private BelongingRepository belongingRepository;
	
	public GameListService(GameListRepository repository, GameRepository gameRepository, BelongingRepository belongingRepository) {
		this.repository = repository;
		this.gameRepository = gameRepository;
		this.belongingRepository = belongingRepository;
	}
	
	@Transactional(readOnly = true)
	public List<GameListDTO> findAll() {
		return repository.findAll().stream().map(x -> new GameListDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public GameListDTO save(GameList gameList) {
		return new GameListDTO(repository.save(gameList));
	}
	
	@Transactional
	public GameMinDTO addGame(Long listId, Long gameId) {
		var game = gameRepository.findById(gameId);
		var list = repository.findById(listId);
		var index = countList(listId);
		if(!game.isPresent()) throw new ResourceNotFoundException("Game not found");
		if(!list.isPresent()) throw new ResourceNotFoundException("List not found");
		Belonging belonging = new Belonging(game.get(), list.get(), index);
		belongingRepository.save(belonging);
		return new GameMinDTO(game.get());
	}
	
	@Transactional(readOnly = true)
	private Integer countList(Long listId) {
		var result = gameRepository.searchByList(listId);
		if (result.isEmpty()) throw new ResourceNotFoundException("List not found");
		List<GameMinProjection> list = result;
		return list.size();
	}
	
	@Transactional
	public void move(Long listId, int sourceIndex, int destinationIndex) {
		var result = gameRepository.searchByList(listId);
		if (result.isEmpty()) throw new ResourceNotFoundException("List not found");
		List<GameMinProjection> list = result;
		GameMinProjection obj = list.remove(sourceIndex);
		list.add(destinationIndex, obj);
		
		int min = sourceIndex < destinationIndex ? sourceIndex : destinationIndex;
		int max = sourceIndex < destinationIndex ? destinationIndex : sourceIndex;
		
		for (int i = min; i <= max; i++) {
			repository.updateBelongingPosition(listId, list.get(i).getId(), i);
		}
	}
	
	@Transactional
    public GameListDTO update(Long listId, RequestCreateGameList gameListRequest) {
        GameList gameList = repository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("Game list not found"));

        BeanUtils.copyProperties(gameListRequest, gameList, "id");
        gameList = repository.save(gameList);
        
        return new GameListDTO(gameList);
    }

    @Transactional
    public void delete(Long listId) {
        if (!repository.existsById(listId)) {
            throw new ResourceNotFoundException("Game list not found");
        }
           repository.deleteById(listId);
    }
}
