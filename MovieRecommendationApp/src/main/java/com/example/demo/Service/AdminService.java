package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import com.example.demo.Model.AdminModel;
import com.example.demo.Model.GenreModel;
import com.example.demo.Model.LanguageModel;
import com.example.demo.Model.MovieModel;

public interface AdminService {
	
	//Admin Authenticate
	boolean authenticateAdmin(String username, String password);
	
	//CRUD Operation of Genre
	public boolean isAddGenre(GenreModel genre);
	public List<GenreModel> getAllGenre();
	public boolean isDeleteGenre(int id);
	public boolean isUpdateGenre(GenreModel genre);
	
	//CRUD Operation of Language
	public boolean addLanguage(LanguageModel language);
	public List<LanguageModel> getAllLanguages();
//	public LanguageModel getLanguageById(int id);
	public boolean isUpdateLanguage(LanguageModel language);
	public boolean isDeleteLanguage(int id); 
	
	//CRUD Operation For Movie
	public boolean addMovie(MovieModel movie);
	public List<Map<String,Object>> getAllMovie();
	public boolean isDeleteMovie(int id);
	public boolean isUpdateMovie(MovieModel movie);
}
