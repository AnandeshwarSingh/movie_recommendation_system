package com.example.demo.Repository;

import java.util.List;

import com.example.demo.Model.GenreModel;
import com.example.demo.Model.MovieModel;
import com.example.demo.Model.UserModel;

public interface AdminRepository {
	
	public boolean validateAdmin(String username, String password);
	public boolean isAddGenre(GenreModel genre);
	public List<GenreModel> getAllGenre();
	public boolean isDeleteGenre(int id);
	public boolean isUpdateGenre(GenreModel genre);
	
}
