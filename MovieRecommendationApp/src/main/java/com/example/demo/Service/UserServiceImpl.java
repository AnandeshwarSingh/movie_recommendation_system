package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.MovieModel;
import com.example.demo.Model.UserModel;
import com.example.demo.Repository.UserRepositoryImpl;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepositoryImpl userRepo;
	
	@Override
	public boolean addUser(UserModel user) {
		return userRepo.addUser(user);
	}

	@Override
	public List<UserModel> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.getAllUsers();
	}

	@Override
	public UserModel getUserById(int id) {
		// TODO Auto-generated method stub
		return userRepo.getUserById(id);
	}

	@Override
	public boolean updateUser(UserModel user) {
		// TODO Auto-generated method stub
		return userRepo.updateUser(user);
	}

	@Override
	public boolean deleteUser(int id) {
		// TODO Auto-generated method stub
		return userRepo.deleteUser(id);
	}

	@Override
	public UserModel findByEmailAndPassword(String email, String password) {
		// TODO Auto-generated method stub
		return userRepo.findByEmailAndPassword(email, password);
	}

	@Override
	public boolean addToWatchlist(int userId, int movieId) {
        return userRepo.addToWatchlist(userId, movieId) > 0;
    }

    public boolean removeFromWatchlist(int userId, int movieId) {
        return userRepo.removeFromWatchlist(userId, movieId) > 0;
    }

	@Override
	public List<MovieModel> getWatchlistMovies(int userId) {
		// TODO Auto-generated method stub
		return userRepo.getWatchlistMovies(userId);
	}

	@Override
	 public boolean updatePasswordByPhoneNumber(String phoneNumber, String newPassword) {
        return userRepo.updatePasswordByPhoneNumber(phoneNumber, newPassword);
    }

	@Override
	public UserModel getUserByPhoneNumber(String phoneNumber) {
		 return userRepo.findByPhoneNumber(phoneNumber);
	}

	
}
