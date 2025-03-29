package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.CustomException.GenreNotFoundException;

import com.example.demo.Model.GenreModel;
import com.example.demo.Model.MovieModel;
import com.example.demo.Service.AdminServiceImpl;

@RestController
public class AdminController {
	
	List<GenreModel> mlist;
	
	@Autowired
	AdminServiceImpl adminService;
	
	@PostMapping("/addGenre")
	public String addGenre(@RequestBody GenreModel genre) {
		return (adminService.isAddGenre(genre))?"Genre Added Successfully":"OOPs Failed to Add";
	}
	
	@GetMapping("/viewAllGenre")
	public List<GenreModel> getAllGenre(){
		mlist=adminService.getAllGenre();
		if(mlist.isEmpty()) {
			throw new GenreNotFoundException("There is no data in the database");
		}
			return mlist;	
	}
	
	@DeleteMapping("/deleteGenreById/{gid}")
	public String deleteGenreById(@PathVariable("gid") Integer id) {
		if(adminService.isDeleteGenre(id))
			return "Genre Deleted Successfully";
		else
			throw new GenreNotFoundException("Genre not found using "+id);
	}
	
	@PutMapping("/updateGenre")
	public String updateEmployee(@RequestBody GenreModel genre) 	{
		boolean b=adminService.isUpdateGenre(genre);
		if(b)
			return "Genre record updated with id "+genre;
		else {
			throw new GenreNotFoundException("Genre not found using "+genre.getGenre_id());
		}
	}
	
}
