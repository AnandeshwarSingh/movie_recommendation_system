package com.example.demo.Repository;

import java.util.List;

import com.example.demo.Model.UserModel;

public interface UserRepository {
	
	public boolean addUser(UserModel user);
    public List<UserModel> getAllUsers();
    public UserModel getUserById(int id);
    public boolean updateUser(UserModel user);
    public boolean deleteUser(int id);
}
