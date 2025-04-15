package com.example.demo.Repository;

import java.util.List;
import java.util.Map;

import com.example.demo.Model.GenreModel;
import com.example.demo.Model.LanguageModel;
import com.example.demo.Model.MovieModel;
import com.example.demo.Model.UserModel;

public interface AdminRepository {
	
	//Admin Authenticate
	public boolean validateAdmin(String username, String password);
	
	//CRUD Operation for Genre
	public boolean isAddGenre(GenreModel genre);
	public List<GenreModel> getAllGenre();
	public GenreModel getGenreById(int id);
	public boolean isDeleteGenre(int id);
	public boolean isUpdateGenre(GenreModel genre);
	
	//CRUD Operation for Language
	public boolean addLanguage(LanguageModel language);
	public List<LanguageModel> getAllLanguages();
	public LanguageModel getLanguageById(int id);
	public boolean isUpdateLanguage(LanguageModel language);
	public boolean isDeleteLanguage(int id);
	
	//CRUD Operation for Movie
	public boolean addMovie(MovieModel movie);
	public List<Map<String, Object>> getAllMovie();
	public Map<String, Object> getMovieById(int id);
	public boolean isDeleteMovie(int id);
	public boolean isUpdateMovie(MovieModel movie);
	
	public List<Map<String, Object>> getAllMovieByGenre(int gid);
	public List<Map<String, Object>> getAllMovieByLanguage(int lid);
}
