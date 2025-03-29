package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.GenreModel;
import com.example.demo.Model.MovieModel;
import com.example.demo.Repository.AdminRepositoryImpl;

@Service("adminService")
public class AdminServiceImpl implements AdminService{
	@Autowired
	AdminRepositoryImpl adminRepo;
	
	@Override
	public boolean isAddGenre(GenreModel genre) {
		return adminRepo.isAddGenre(genre);
	}

	@Override
	public List<GenreModel> getAllGenre() {
		return adminRepo.getAllGenre();
	}

	@Override
	public boolean isDeleteGenre(int id) {
		return adminRepo.isDeleteGenre(id);
	}

	@Override
	public boolean isUpdateGenre(GenreModel genre) {
		return adminRepo.isUpdateGenre(genre);
	}

}
