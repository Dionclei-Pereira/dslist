package me.dionclei.dslist.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dionclei.dslist.dto.GameDTO;
import me.dionclei.dslist.dto.GameMinDTO;
import me.dionclei.dslist.repositories.GameRepository;

@Service
public class GameService {
	
	private GameRepository repository;
	
	public GameService(GameRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly = true)
	public List<GameMinDTO> findAll() {
		return repository.findAll().stream().map(x -> new GameMinDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<GameMinDTO> findByList(Long listId) {
		return repository.searchByList(listId).stream().map(x -> new GameMinDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public GameDTO findById(Long id) {
		var result = repository.findById(id);
		if (result.isPresent()) {
			return new GameDTO(result.get());
		}
		return null;
	}
	
}
