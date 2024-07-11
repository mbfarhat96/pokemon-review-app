package com.mohsintech.mohsin.pokemon_review_app.controller;

import com.mohsintech.mohsin.pokemon_review_app.dto.ReviewDto;
import com.mohsintech.mohsin.pokemon_review_app.service.ReviewService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping("pokemon/{pokemonId}/review")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto,@PathVariable(value = "pokemonId")int pokemonId){
        ReviewDto response = reviewService.createReview(reviewDto,pokemonId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("pokemon/{pokemonId}/review")
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable(value = "pokemonId")int pokemonId){
        List<ReviewDto> response = reviewService.getReviewByPokemonId(pokemonId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("pokemon/{pokemonId}/review/{id}")
        public ResponseEntity<ReviewDto> getReview(@PathVariable(value = "pokemonId")int pokemonId, @PathVariable(value = "id")int reviewId){
        ReviewDto response = reviewService.getReview(pokemonId, reviewId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("pokemon/{pokemonId}/review/{id}")
    public ResponseEntity<ReviewDto> UpdateReview(@RequestBody ReviewDto reviewDto,@PathVariable(value = "pokemonId")int pokemonId, @PathVariable(value = "id")int reviewId){
        ReviewDto response = reviewService.updateReview(reviewDto,pokemonId, reviewId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("pokemon/{pokemonId}/review/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "pokemonId")int pokemonId, @PathVariable(value = "id")int reviewId){
        reviewService.deleteReview(pokemonId,reviewId);

        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }



}
