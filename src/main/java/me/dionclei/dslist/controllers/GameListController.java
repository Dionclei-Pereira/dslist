package me.dionclei.dslist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.dslist.dto.GameListDTO;
import me.dionclei.dslist.dto.GameMinDTO;
import me.dionclei.dslist.dto.ReplacementDTO;
import me.dionclei.dslist.services.GameListService;
import me.dionclei.dslist.services.GameService;

@RestController
@RequestMapping("/games")
public class GameListController {
	
	@Autowired
	private GameListService gameListService;
	
	@Autowired
	private GameService gameService;
	
	@GetMapping
	public ResponseEntity<List<GameListDTO>> findAll() {
		return ResponseEntity.ok().body(gameListService.findAll());
	}
	
	@GetMapping("/{listId}/games")
	public ResponseEntity<List<GameMinDTO>> findByList(@PathVariable Long listId) {
		return ResponseEntity.ok().body(gameService.findByList(listId));
	}
	
	@PostMapping("/{listId}/replacement")
	public ResponseEntity<Void> move(@PathVariable Long listId, @RequestBody ReplacementDTO body) {
		gameListService.move(listId, body.sourceIndex(), body.destinationIndex());
		return ResponseEntity.ok().build();
	}
	
}
