package me.dionclei.dslist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.dslist.dto.GameListDTO;
import me.dionclei.dslist.services.GameListService;

@RestController
@RequestMapping("/games")
public class GameListController {
	
	@Autowired
	private GameListService gameListService;
	
	@GetMapping
	public ResponseEntity<List<GameListDTO>> findAll() {
		return ResponseEntity.ok().body(gameListService.findAll());
	}
	
}
