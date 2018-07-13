package com.example.jokes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.jokes.model.Joke;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Integer> {
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE jokes SET likes =:likes WHERE id =:id", nativeQuery = true)
	void updateLikes(@Param("id") Integer id, @Param("likes") Integer likes); 
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE jokes SET dislikes =:dislikes WHERE id =:id", nativeQuery = true)
	void updateDislikes(@Param("id") Integer id, @Param("dislikes") Integer dislikes); 
}
