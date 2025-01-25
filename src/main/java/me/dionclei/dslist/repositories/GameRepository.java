package me.dionclei.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dionclei.dslist.entities.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
