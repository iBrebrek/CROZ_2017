package com.example.jokes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "jokes")
public class Joke {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "content", unique = true, nullable = false)
	private String content;
	
	@Column(name = "likes")
	private int likes;
	
	@Column(name = "dislikes")
	private int dislikes;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	public Joke() {}
	
	public Joke(String content, Category category) {
		this.content = content;
		this.category = category;
		this.dislikes = 0;
		this.likes = 0;
	}

	public Joke(int id, String content, int likes, int dislikes, Category category) {
		this.id = id;
		this.content = content;
		this.likes = likes;
		this.dislikes = dislikes;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
