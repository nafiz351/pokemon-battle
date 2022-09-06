package com.pokemon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pokemon.entity.BattleResult;
import com.pokemon.entity.Pokemon;
import com.pokemon.service.PokemonService;

@RestController
@CrossOrigin
@RequestMapping("/pokemon")
public class PokemonController {

	@Autowired
    private PokemonService pokemonService;
	
    @GetMapping
    public ResponseEntity<List<Pokemon>> getAllPokemons() {
    	List<Pokemon> pokemonList = pokemonService.findAllPokemons();
        return new ResponseEntity<>(pokemonList, HttpStatus.OK);
    }
    
    @GetMapping("/battle/{firstPokemon}/{secondPokemon}")
    public ResponseEntity<List<BattleResult>> getPokemonBattleResult (@PathVariable("firstPokemon") String firstPokemon, @PathVariable("secondPokemon") String secondPokemon) {
    	List<BattleResult> battleResult = pokemonService.findPokemonBattleResult(firstPokemon, secondPokemon);
        return new ResponseEntity<>(battleResult, HttpStatus.OK);
    }
}
