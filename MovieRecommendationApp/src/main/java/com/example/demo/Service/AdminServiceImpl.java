package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.AdminModel;
import com.example.demo.Model.GenreModel;
import com.example.demo.Model.LanguageModel;
import com.example.demo.Model.MovieModel;
import com.example.demo.Repository.AdminRepositoryImpl;

@Service("adminService")
public class AdminServiceImpl implements AdminService{
	@Autowired
	AdminRepositoryImpl adminRepo;
	
	@Override
	public boolean authenticateAdmin(String username, String password) {
		return adminRepo.validateAdmin(username, password);
	}
	@Override
	public boolean isAddGenre(GenreModel genre) {
		return adminRepo.isAddGenre(genre);
	}

	@Override
	public List<GenreModel> getAllGenre() {
		return adminRepo.getAllGenre();
	}
	
	@Override
	public GenreModel getGenreById(int id) {
		return adminRepo.getGenreById(id);
	}

	@Override
	public boolean isDeleteGenre(int id) {
		return adminRepo.isDeleteGenre(id);
	}

	@Override
	public boolean isUpdateGenre(GenreModel genre) {
		return adminRepo.isUpdateGenre(genre);
	}
	@Override
	public boolean addLanguage(LanguageModel language) {
		return adminRepo.addLanguage(language);
	}
	@Override
	public List<LanguageModel> getAllLanguages() {
		return adminRepo.getAllLanguages();
	}
	
	@Override
	public LanguageModel getLanguageById(int id) {
		return adminRepo.getLanguageById(id);
	}
	@Override
	public boolean isUpdateLanguage(LanguageModel language) {
		return adminRepo.isUpdateLanguage(language);
	}
	@Override
	public boolean isDeleteLanguage(int id) {
		return adminRepo.isDeleteLanguage(id);
	}
	@Override
	public boolean addMovie(MovieModel movie) {
		return adminRepo.addMovie(movie);
	}
	@Override
	public List<Map<String,Object>> getAllMovie() {
		return adminRepo.getAllMovie();
	}
	@Override
	public boolean isDeleteMovie(int id) {
		return adminRepo.isDeleteMovie(id);
	}
	@Override
	public boolean isUpdateMovie(MovieModel movie) {
		return adminRepo.isUpdateMovie(movie);
	}
	
	@Override
	public Map<String, Object> getMovieById(int id) {
		return adminRepo.getMovieById(id);
	}
	@Override
	public List<Map<String, Object>> getAllMovieByGenre(int gid) {
		return adminRepo.getAllMovieByGenre(gid);
	}
	@Override
	public List<Map<String, Object>> getAllMovieByLanguage(int lid) {
		return adminRepo.getAllMovieByLanguage(lid);
	}

}
