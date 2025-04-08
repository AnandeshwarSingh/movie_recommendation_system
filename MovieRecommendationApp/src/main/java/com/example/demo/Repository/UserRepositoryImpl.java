package com.example.demo.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.GenreModel;
import com.example.demo.Model.UserModel;

@Repository("userRepo")
public class UserRepositoryImpl implements UserRepository{

	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	//ADD User
	@Override
	public boolean addUser(UserModel user) {
		System.out.println(user);
		String sql = "INSERT INTO users (name, email, phone_number, password) VALUES (?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPhoneNumber(), user.getPassword());
        return result > 0;
	}

	//View User
	@Override
	public List<UserModel> getAllUsers() {
		String sql = "SELECT * FROM users";
		List<UserModel> list=jdbcTemplate.query(sql,(ResultSet rs, int rowNum)->{
			return new UserModel(rs.getInt("user_id"),rs.getString("name"),rs.getString("email"),          rs.getString("phone_number"),rs.getString("password"));});
		return list;
	}

	//Search User
	@Override
	public UserModel getUserById(int id) {
		
		return null;
	}

	//Update User
	@Override
	public boolean updateUser(UserModel user) {
		String sql = "UPDATE users SET name = ?, email = ?, phone_number = ?, password = ? WHERE user_id = ?";
        int result = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPhoneNumber(), user.getPassword(), user.getUserId());
        return result > 0;
	}

	//Delete User
	@Override
	public boolean deleteUser(int id) {
		 String sql = "DELETE FROM users WHERE user_id = ?";
	        int result = jdbcTemplate.update(sql, id);
	        return result > 0;
	}

}
