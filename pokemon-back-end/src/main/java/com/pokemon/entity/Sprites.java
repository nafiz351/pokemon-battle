package com.pokemon.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sprites {

	@JsonProperty("front_default")
	String image;

	public String getimage() {
		return image;
	}

	public void setimage(String image) {
		this.image = image;
	}
	
	
}