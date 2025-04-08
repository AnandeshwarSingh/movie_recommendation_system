package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.CustomException.GenreNotFoundException;
import com.example.demo.Model.UserModel;
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

	/*
	 * Read (Get user by ID)
	 * 
	 * @GetMapping("/{id}") public UserModel getUserById(@PathVariable int id) {
	 * return userRepository.getUserById(id); }
	 */

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

}
