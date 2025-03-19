package me.dionclei.dslist.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.dslist.dto.GameDTO;
import me.dionclei.dslist.dto.GameMinDTO;
import me.dionclei.dslist.dto.RequestCreateGame;
import me.dionclei.dslist.entities.Game;
import me.dionclei.dslist.services.GameService;

@RestController
@RequestMapping("/games")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@GetMapping
	public ResponseEntity<List<GameMinDTO>> findAllGames() {
		return ResponseEntity.ok().body(gameService.findAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<GameDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(gameService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<GameDTO> save(@RequestBody RequestCreateGame gameRequest) {
		Game game = new Game();
		BeanUtils.copyProperties(gameRequest, game);
		return ResponseEntity.ok().body(gameService.save(game));
	}
	
}
