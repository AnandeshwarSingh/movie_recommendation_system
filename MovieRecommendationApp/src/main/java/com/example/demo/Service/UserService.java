package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.MovieModel;
import com.example.demo.Model.UserModel;

public interface UserService {
	
	public boolean addUser(UserModel user);
    public List<UserModel> getAllUsers();
    public UserModel getUserById(int id);
    public boolean updateUser(UserModel user);
    public boolean deleteUser(int id);
    public UserModel getUserByPhoneNumber(String phoneNumber);
    public boolean updatePasswordByPhoneNumber(String phoneNumber, String newPassword);
    
    public UserModel findByEmailAndPassword(String email, String password);
    public boolean addToWatchlist(int userId, int movieId);
    public boolean removeFromWatchlist(int userId, int movieId);
    public List<MovieModel> getWatchlistMovies(int userId);
}
