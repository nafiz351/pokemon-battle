package com.pokemon;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.pokemon.service.PokemonService;

@SpringBootTest
class PokemonApplicationTests {

	PokemonService pokemonService;

	@BeforeEach
	void setUp() {
		pokemonService = new PokemonService();
	}

	@Test
	@DisplayName("Normal Attack Damage Test")
	void testDamageForNormalAttack() {
		int damage = pokemonService.getDamageForNormalAttack();
		assertTrue( damage>= 1 && damage <= 10, "Error, Normal attack damage should be between 1 to 10");
	}
	
	@Test
	@DisplayName("Special Attack Damage Test")
	void testDamageForSpecialAttack() {
		int damage = pokemonService.getDamageForSpecialAttack();
		assertTrue( damage>= 5 && damage <= 15, "Error, Special attack damage should be between 5 to 15");
	}

}
