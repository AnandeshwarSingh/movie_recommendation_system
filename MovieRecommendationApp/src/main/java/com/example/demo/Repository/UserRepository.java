package com.example.demo.Repository;

import java.util.List;

import com.example.demo.Model.MovieModel;
import com.example.demo.Model.UserModel;
import com.example.demo.Model.WatchlistModel;

public interface UserRepository {
	
	public boolean addUser(UserModel user);
    public List<UserModel> getAllUsers();
    public UserModel getUserById(int id);
    public boolean updateUser(UserModel user);
    public boolean deleteUser(int id);
    public UserModel findByPhoneNumber(String phoneNumber);
    public boolean updatePasswordByPhoneNumber(String phoneNumber, String newPassword);
    
    public UserModel findByEmailAndPassword(String email, String password);
    public int addToWatchlist(int userId, int movieId);
    public int removeFromWatchlist(int userId, int movieId);
    public List<MovieModel> getWatchlistMovies(int userId);
}
