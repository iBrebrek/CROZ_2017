package com.example.jokes.controller;

import com.example.jokes.model.Joke;

public class JokeRepresentation {
	
	private int rank;
	private Joke joke;
	private int difference;
	
	public JokeRepresentation() {};

	public JokeRepresentation(int rank, Joke joke) {
		this.rank = rank;
		this.joke = joke;
		difference = joke.getLikes() - joke.getDislikes();
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Joke getJoke() {
		return joke;
	}

	public void setJoke(Joke joke) {
		this.joke = joke;
	}

	public int getDifference() {
		return difference;
	}

	public void setDifference(int difference) {
		this.difference = difference;
	}
}
