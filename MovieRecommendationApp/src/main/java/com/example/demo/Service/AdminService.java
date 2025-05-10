package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import com.example.demo.Model.AdminModel;
import com.example.demo.Model.ContactModel;
import com.example.demo.Model.DashboardStats;
import com.example.demo.Model.GenreModel;
import com.example.demo.Model.LanguageModel;
import com.example.demo.Model.MovieModel;
import com.example.demo.Model.RatingModel;

public interface AdminService {
	
	//Admin Authenticate
	public AdminModel findByEmailAndPassword(String email, String password);
	
	//CRUD Operation of Genre
	public boolean isAddGenre(GenreModel genre);
	public List<GenreModel> getAllGenre();
	public GenreModel getGenreById(int id);
	public boolean isDeleteGenre(int id);
	public boolean isUpdateGenre(GenreModel genre);
	
	//CRUD Operation of Language
	public boolean addLanguage(LanguageModel language);
	public List<LanguageModel> getAllLanguages();
	public LanguageModel getLanguageById(int id);
	public boolean isUpdateLanguage(LanguageModel language);
	public boolean isDeleteLanguage(int id); 
	
	//CRUD Operation For Movie
	public boolean addMovie(MovieModel movie);
	public List<Map<String,Object>> getAllMovie();
	public Map<String, Object> getMovieById(int id);
	public boolean isDeleteMovie(int id);
	public boolean isUpdateMovie(MovieModel movie);
	public List<Map<String, Object>> searchMovies(String keyword);
	
	public List<Map<String, Object>> getAllMovieByGenre(int gid);
	public List<Map<String, Object>> getAllMovieByLanguage(int lid);
	
	public boolean addRating(RatingModel rating);
	public List<Map<String, Object>> getAllRating();
	public Map<String, Object> getRatingById(int id);
	public boolean hasUserRatedMovie(int userId, int movieId);
	
	public DashboardStats getDashboardStats();
	public List<Map<String,Object>> getTopMovies();
	public List<Map<String,Object>> getLatestMovies();
	public List<Map<String,Object>> getLatestUsers();
	public List<Map<String,Object>> getLatestRating();
	
	public boolean addContact(ContactModel contact);
	public List<ContactModel> getAllContact();
}
