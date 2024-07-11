package com.mohsintech.mohsin.pokemon_review_app.repository;

import com.mohsintech.mohsin.pokemon_review_app.dto.ReviewDto;
import com.mohsintech.mohsin.pokemon_review_app.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByPokemonId(int pokemonId);
}
