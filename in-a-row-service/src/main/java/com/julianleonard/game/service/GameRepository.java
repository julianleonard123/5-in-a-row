package com.julianleonard.game.service;

import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {

	Game findById(long id);
}