package com.example.jokes.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jokes.model.Category;
import com.example.jokes.model.Joke;
import com.example.jokes.repository.CategoryRepository;
import com.example.jokes.repository.JokeRepository;

@Controller
public class JokeController {
	
	/**
	 * Number of items per page while using pagination
	 */
	private static int ITEMS_PER_PAGE = 10;

	@Autowired
	private JokeRepository jokeRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
		
	/**
	 * Table of jokes. 
	 * Pagination is used and most liked joke is first while least liked is last.
	 * 
	 * @param model			values for view
	 * @param pageString	page number in pagination
	 * @return view of list of jokes
	 */
	@GetMapping("/")
	public String getJokes(ModelMap model, @RequestParam(value = "page", required = false) String pageString) {
		List<Joke> jokes = jokeRepository.findAll();
		jokes.sort((a, b) -> ((b.getLikes() - b.getDislikes()) - (a.getLikes() - a.getDislikes())));
		
		int maxPage = jokes.size() % ITEMS_PER_PAGE == 0 ? 
				jokes.size() / ITEMS_PER_PAGE : jokes.size() / ITEMS_PER_PAGE + 1;
		
		int page = 1;
		if (pageString != null && pageString.matches("\\d+")) {
			page = Integer.parseInt(pageString);
			if (page <= 0) page = 1;
			page = Math.min(page, maxPage);
		}
		
		List<JokeRepresentation> items = new ArrayList<>();
		int max = Math.min(page*ITEMS_PER_PAGE, jokes.size());
		
		for (int rank = 1 + (page-1)*ITEMS_PER_PAGE; rank <= max; rank++) {
			items.add(new JokeRepresentation(rank, jokes.get(rank - 1)));
		}
		
		model.put("items", items);
		model.put("numberOfPages", maxPage);
		model.put("currentPage", page);
		return "jokes";
	}
	
	/**
	 * Form for adding a joke.
	 * 
	 * @param jokeForm	data of new joke
	 * @param model		values for view
	 * @return view of form for adding a joke
	 */
	@GetMapping("/new")
	public String showForm(JokeForm jokeForm, ModelMap model) {
		List<Category> categories = categoryRepository.findAll();
		model.put("categories", categories);
		return "new_joke";
	}
	
	/**
	 * Adds a new joke and redirects to index.
	 * If data in form was invalid redirection is not made nor is joke added.
	 * 
	 * @param jokeForm			data of new joke
	 * @param bindingResult		for errors
	 * @param model				values for view
	 * @return view of all jokes, or form if there was an error while adding
	 */
	@PostMapping("/new")
	public String createJoke(@Valid JokeForm jokeForm, BindingResult bindingResult, ModelMap model) {
		
		Optional<Category> category = categoryRepository.findById(jokeForm.getCategory());
		
		if (bindingResult.hasErrors() || !category.isPresent()) {
			return showForm(jokeForm, model);
		}
		
		jokeRepository.save(new Joke(jokeForm.getContent(), category.get()));
		return "redirect:/";
	}
	
	/**
	 * Likes or dislikes a joke with given id.
	 * 
	 * @param id		joke id
	 * @param liked		'y' if joke was liked or 'n' if disliked
	 * @param request	http request - used for keeping track of current page in pagination
	 * @return same page where this was called
	 */
	@PostMapping("/like/{yn}/{id}")
	public String likeJoke(@PathVariable(value = "id") int id, @PathVariable(value = "yn") String liked, HttpServletRequest request) {
		
		Optional<Joke> optional = jokeRepository.findById(id);
		if (optional.isPresent()) {
			Joke joke = optional.get();
			if (liked.equalsIgnoreCase("y")) {
				jokeRepository.updateLikes(joke.getId(), joke.getLikes() + 1);
			} else if (liked.equalsIgnoreCase("n")) {
				jokeRepository.updateDislikes(joke.getId(), joke.getDislikes() + 1);
			}
		}		
		
		String page = request.getParameter("page");
		String result = "redirect:/";
		if (page != null) result += "?page=" + page;
		
		return result;
	}
}
