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
	
	//CRUD Operation of Genre
	public boolean isAddGenre(GenreModel genre);
	public List<GenreModel> getAllGenre();
	public boolean isDeleteGenre(int id);
	public boolean isUpdateGenre(GenreModel genre);
	
	//CRUD Operation of Language
	public boolean addLanguage(LanguageModel language);
	public List<LanguageModel> getAllLanguages();
	public LanguageModel getLanguageById(int id);
	public boolean isUpdateLanguage(LanguageModel language);
	public boolean isDeleteLanguage(int id); 
	
}
