package com.example.jokes.controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class JokeForm {

	// dokumentacija tvrdi da NotNull zabrani prazan string ali to nije istina, zato je dodan min=1
	@Size(min=1)
	@NotNull
	private String content;

	@NotNull
	private int category;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
}
