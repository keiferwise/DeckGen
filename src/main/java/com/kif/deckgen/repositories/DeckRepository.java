package com.kif.deckgen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.kif.deckgen.models.Deck;

public interface DeckRepository extends JpaRepository<Deck, Long> {

}
