package com.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pokemon.entity.BattleResult;
import com.pokemon.entity.Pokemon;

@Service
public class PokemonService {

	@Value("${api.url}")
	private String apiUrl;
	
	@Value("${api.limit}")
	private int apiLimit;

	@Autowired
	private RestTemplate template = new RestTemplate();

	
	@Cacheable(cacheNames = "findAllPokemons", cacheManager = "cachefindAllPokemons")
	public List<Pokemon> findAllPokemons() {
		List<Pokemon> pokemonList = new ArrayList<>();
		for (int pid = 1; pid <= apiLimit; pid++) {
			pokemonList.add(template.getForObject(apiUrl + "/pokemon/{id}", Pokemon.class, pid));
		}
		return pokemonList;
	}
	
	public List<BattleResult> findPokemonBattleResult(String firstPokemon, String secondPokemon) {  
		List<BattleResult> battleResults = new ArrayList<>();

		BattleResult battleResult01 = new BattleResult();
		BattleResult battleResult02 = new BattleResult();
		
		int initialHP = 20;
		
		//get and set Pokemon information for BattleResult
		Pokemon pokemon01 = template.getForObject(apiUrl + "/pokemon/{name}", Pokemon.class, firstPokemon);		
		Pokemon pokemon02 = template.getForObject(apiUrl + "/pokemon/{name}", Pokemon.class, secondPokemon);
		battleResult01.setPokemon(pokemon01);
		battleResult02.setPokemon(pokemon02);
		
		if(pokemon01 != null && pokemon02 != null) {
			
			boolean firstPokemonWinner = false;
			boolean secondPokemonWinner = false;
			
			// get Damage and set HP after normal attack
			int firstPokemonNormalAttackHP = initialHP - getDamageForNormalAttack();
			battleResult01.setNormalAttackHP(firstPokemonNormalAttackHP);
			
			int secondPokemonNormalAttackHP = initialHP - getDamageForNormalAttack();
			battleResult02.setNormalAttackHP(secondPokemonNormalAttackHP);
			
			// get Damage and set HP after 1st special attack
			int firstPokemonSpecialAttack01HP = firstPokemonNormalAttackHP - getDamageForSpecialAttack();
			battleResult01.setSpecialAttack01HP(firstPokemonSpecialAttack01HP);
			
			int secondPokemonSpecialAttack01HP = secondPokemonNormalAttackHP - getDamageForSpecialAttack();
			battleResult02.setSpecialAttack01HP(secondPokemonSpecialAttack01HP);
			
			int firstPokemonSpecialAttack02HP = 0;
			int secondPokemonSpecialAttack02HP = 0;
			
			//Calculate winner if one of the player will win both attack			
			if(firstPokemonNormalAttackHP > secondPokemonNormalAttackHP && firstPokemonSpecialAttack01HP > secondPokemonSpecialAttack01HP) {
				firstPokemonWinner = true;
			}else if(firstPokemonNormalAttackHP < secondPokemonNormalAttackHP && firstPokemonSpecialAttack01HP < secondPokemonSpecialAttack01HP) {
				secondPokemonWinner=true;
			}
			
			// get Damage and set HP after 2nd special attack and Calculate winner
			if(!firstPokemonWinner && !secondPokemonWinner) {
				firstPokemonSpecialAttack02HP = firstPokemonSpecialAttack01HP - getDamageForSpecialAttack();
				battleResult01.setSpecialAttack02HP(firstPokemonSpecialAttack02HP);
				
				secondPokemonSpecialAttack02HP = secondPokemonSpecialAttack01HP - getDamageForSpecialAttack();
				battleResult02.setSpecialAttack02HP(secondPokemonSpecialAttack02HP);
				

				if(firstPokemonSpecialAttack02HP > secondPokemonSpecialAttack02HP) {
					firstPokemonWinner = true;
				}else{
					secondPokemonWinner=true;
				}
			}
			battleResult01.setWinner(firstPokemonWinner);
			battleResult02.setWinner(secondPokemonWinner);
			
			battleResults.add(battleResult01);
			battleResults.add(battleResult02);
		}		
		
		return battleResults;
	}

	public int getDamageForNormalAttack() {
		Random rand = new Random();
		int min = 1;
		int max =10;
		return rand.nextInt((max - min) + 1) + min;
	}
	
	public int getDamageForSpecialAttack() {
		Random rand = new Random();
		int min = 5;
		int max =15;
		return rand.nextInt((max - min) + 1) + min;
	}
}
