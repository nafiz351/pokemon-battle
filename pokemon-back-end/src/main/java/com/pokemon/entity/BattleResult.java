package com.pokemon.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class BattleResult {
	@JsonProperty("pokemon")
	@JsonInclude(Include.NON_NULL)
	Pokemon pokemon;

	private Integer normalAttackHP;

	private Integer specialAttack01HP;

	private Integer specialAttack02HP;

	private Boolean winner;
	
	public BattleResult() {
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public Integer getNormalAttackHP() {
		return normalAttackHP;
	}

	public void setNormalAttackHP(Integer normalAttackHP) {
		this.normalAttackHP = normalAttackHP;
	}

	public Integer getSpecialAttack01HP() {
		return specialAttack01HP;
	}

	public void setSpecialAttack01HP(Integer specialAttack01HP) {
		this.specialAttack01HP = specialAttack01HP;
	}

	public Integer getSpecialAttack02HP() {
		return specialAttack02HP;
	}

	public void setSpecialAttack02HP(Integer specialAttack02HP) {
		this.specialAttack02HP = specialAttack02HP;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

}
