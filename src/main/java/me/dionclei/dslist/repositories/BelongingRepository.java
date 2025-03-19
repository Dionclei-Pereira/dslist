package me.dionclei.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import me.dionclei.dslist.entities.Belonging;
import me.dionclei.dslist.entities.BelongingPK;

public interface BelongingRepository extends JpaRepository<Belonging, BelongingPK>{

}
