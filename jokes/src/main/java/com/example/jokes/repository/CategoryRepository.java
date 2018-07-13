package com.example.jokes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jokes.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
