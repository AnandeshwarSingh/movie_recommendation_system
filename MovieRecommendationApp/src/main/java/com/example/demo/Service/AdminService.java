package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.GenreModel;
import com.example.demo.Model.MovieModel;

public interface AdminService {
	public boolean isAddGenre(GenreModel genre);
	public List<GenreModel> getAllGenre();
	public boolean isDeleteGenre(int id);
	public boolean isUpdateGenre(GenreModel genre);
}
