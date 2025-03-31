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
import jakarta.validation.Valid;
import me.dionclei.dslist.dto.GameListDTO;
import me.dionclei.dslist.dto.GameMinDTO;
import me.dionclei.dslist.dto.PagedResult;
import me.dionclei.dslist.dto.ReplacementDTO;
import me.dionclei.dslist.dto.RequestCreateGameList;
import me.dionclei.dslist.entities.GameList;
import me.dionclei.dslist.exceptions.PageException;
import me.dionclei.dslist.services.GameListService;
import me.dionclei.dslist.services.GameService;

@RestController
@RequestMapping("/lists")
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
	public ResponseEntity<PagedResult<GameMinDTO>> findByList(@PathVariable Long listId, @RequestParam(defaultValue = "1") Integer page) {
	    final int pageSize = 10;
	    int totalGames = gameService.countByList(listId);
	    int totalPages = (int) Math.ceil((double) totalGames / pageSize);
	    
	    if (page < 1 || page > totalPages) {
	        throw new PageException("Page not found");
	    }
	    
	    List<GameMinDTO> games = gameService.findByList(listId, (page - 1) * pageSize);
	    PagedResult<GameMinDTO> paged = new PagedResult<GameMinDTO>(page, totalPages, games);
	    return ResponseEntity.ok().body(paged);
	}
	
	@PostMapping("/{listId}/replacement")
	public ResponseEntity<Void> move(@PathVariable Long listId, @RequestBody ReplacementDTO body) {
		gameListService.move(listId, body.sourceIndex(), body.destinationIndex());
		return ResponseEntity.ok().build();

	}
	
	@PostMapping("/{listId}/games/add-game/{gameId}")
	public ResponseEntity<GameMinDTO> addGame(@PathVariable Long listId, @PathVariable Long gameId) {
		return ResponseEntity.ok().body(gameListService.addGame(listId, gameId));
	}
	
	@PostMapping
	public ResponseEntity<GameListDTO> save(@RequestBody @Valid RequestCreateGameList gameRequest) {
		GameList gameList= new GameList();
		BeanUtils.copyProperties(gameRequest, gameList);
		return ResponseEntity.ok().body(gameListService.save(gameList));
	}
	
	@PutMapping("/{listId}")
	public ResponseEntity<GameListDTO> updateList(@PathVariable Long listId, @RequestBody @Valid RequestCreateGameList gameListRequest) {
	    GameListDTO updatedList = gameListService.update(listId, gameListRequest);
	    return ResponseEntity.ok().body(updatedList);
	}

	@DeleteMapping("/{listId}")
	public ResponseEntity<Void> deleteList(@PathVariable Long listId) {
	    gameListService.delete(listId);
	    return ResponseEntity.noContent().build();
	}
	
}
