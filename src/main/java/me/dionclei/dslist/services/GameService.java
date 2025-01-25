package me.dionclei.dslist.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dionclei.dslist.dto.GameMinDTO;
import me.dionclei.dslist.repositories.GameRepository;

@Service
public class GameService {
	
	@Autowired
	private GameRepository repository;
	
	public List<GameMinDTO> findAll() {
		return repository.findAll().stream().map(x -> new GameMinDTO(x)).collect(Collectors.toList());
	}
	
}
