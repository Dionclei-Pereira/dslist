package me.dionclei.dslist.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.dslist.dto.GameDTO;
import me.dionclei.dslist.dto.GameMinDTO;
import me.dionclei.dslist.dto.PagedResult;
import me.dionclei.dslist.dto.RequestCreateGame;
import me.dionclei.dslist.entities.Game;
import me.dionclei.dslist.exceptions.PageException;
import me.dionclei.dslist.services.GameService;

@RestController
@RequestMapping("/games")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@GetMapping
	public ResponseEntity<PagedResult> findAllGames(@RequestParam(defaultValue = "1") Integer page) {
	    final int pageSize = 10;
	    int totalGames = gameService.countGames();
	    int totalPages = (int) Math.ceil((double) totalGames / pageSize);
	    if (page < 1 || page > totalPages) {
	        throw new PageException("Page not found");
	    }
	    
	    List<GameMinDTO> games = gameService.findAll((page - 1) * pageSize);
	    PagedResult paged = new PagedResult(page, totalPages, games);
	    return ResponseEntity.ok().body(paged);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GameDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(gameService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<GameDTO> save(@RequestBody RequestCreateGame gameRequest) {
		Game game = new Game();
		BeanUtils.copyProperties(gameRequest, game);
		return ResponseEntity.ok().body(gameService.save(game));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<GameDTO> update(@PathVariable Long id, @RequestBody RequestCreateGame gameRequest) {
	    GameDTO updatedGame = gameService.update(id, gameRequest);
	    return ResponseEntity.ok().body(updatedGame);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
	    gameService.delete(id);
	    return ResponseEntity.noContent().build();
	}
	
}
