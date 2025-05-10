package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.CustomException.GenreNotFoundException;
import com.example.demo.Model.MovieModel;
import com.example.demo.Model.UserModel;
import com.example.demo.Model.WatchlistModel;
import com.example.demo.Service.UserServiceImpl;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;
	
	// Create (Add a new user)
    @PostMapping("/add")
    public String addUser(@RequestBody UserModel user) {
        return userService.addUser(user) ? "User added successfully!" : "Failed to add user!";
    }
    
    // Read (Get all users)
    @GetMapping("/viewAllUser")
    public List<UserModel> getAllUsers() {
        List<UserModel>list= userService.getAllUsers();
        if(list.isEmpty()) {
        	throw new GenreNotFoundException("Data not found");
        }
        else {
        	return list;
        }
    }
	 
     //Read (Get user by ID) 
	 @GetMapping("/searchUserById/{id}") public UserModel getUserById(@PathVariable int id) {
	 	return userService.getUserById(id); }

    // Update (Modify user details)
    @PutMapping("/update")
    public String updateUser(@RequestBody UserModel user) {
        return userService.updateUser(user) ? "User updated successfully!" : "Failed to update user!";
    }

    // Delete (Remove a user)
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        return userService.deleteUser(id) ? "User deleted successfully!" : "Failed to delete user!";
    }

    @PostMapping("/addWatchlist")
    public ResponseEntity<String> addToWatchlist(@RequestBody WatchlistModel model) {
        boolean added = userService.addToWatchlist(model.getUserId(), model.getMovieId());
        return added ? ResponseEntity.ok("Added to watchlist") :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add");
    }

    @DeleteMapping("/removeWatchlist")
    public ResponseEntity<String> removeFromWatchlist(@RequestParam int userId, @RequestParam int movieId) {
        boolean removed = userService.removeFromWatchlist(userId, movieId);
        return removed ? ResponseEntity.ok("Removed from watchlist") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
    }
    @GetMapping("/watchlist/{userId}")
    public List<MovieModel> getWatchlist(@PathVariable int userId) {
        return userService.getWatchlistMovies(userId);
    }
}
