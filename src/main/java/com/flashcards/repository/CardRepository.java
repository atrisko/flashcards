package com.flashcards.repository;

import java.util.List;
import java.util.Optional;
import com.flashcards.model.Card;

public interface CardRepository {

    List<Card> findAll();

    Optional<Card> findById(int id);

    void save(Card card);

    void deleteById(int id);

    void update(Card card);

    List<Card> findBySubject(String subject);

    
}
