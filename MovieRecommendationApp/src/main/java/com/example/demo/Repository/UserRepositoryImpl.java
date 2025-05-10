package com.example.demo.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.GenreModel;
import com.example.demo.Model.LanguageModel;
import com.example.demo.Model.MovieModel;
import com.example.demo.Model.UserModel;
import com.example.demo.Model.WatchlistModel;

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
		String sql = "SELECT * FROM users WHERE user_id = ?";
		List<UserModel>list=jdbcTemplate.query(sql,new RowMapper<UserModel>() {
			@Override
			public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserModel user=new UserModel(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
				return user;
			}
		},id);
		return list.size()>0?list.get(0):null;
	}

	//Update User
	@Override
	public boolean updateUser(UserModel user) {
		String sql = "UPDATE users SET name = ?, email = ?, phone_number = ? WHERE user_id = ?";
        int result = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPhoneNumber(), user.getUserId());
        return result > 0;
	}

	//Delete User
	@Override
	public boolean deleteUser(int id) {
		 String sql = "DELETE FROM users WHERE user_id = ?";
	        int result = jdbcTemplate.update(sql, id);
	        return result > 0;
	}

	 public UserModel findByEmailAndPassword(String email, String password) {
	        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
	        try {
	            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new UserModel(
	                    rs.getInt("user_id"),
	                    rs.getString("name"),
	                    rs.getString("email"),
	                    rs.getString("phone_number"),
	                    rs.getString("password")       
	            ), email, password);
	        } catch (Exception e) {
	            return null;
	        }
	    }

	@Override
	public int addToWatchlist(int userId, int movieId) {
		 String sql = "INSERT INTO watchlist (user_id, movie_id) VALUES (?, ?)";
	     return jdbcTemplate.update(sql, userId, movieId);
	}

	@Override
	public int removeFromWatchlist(int userId, int movieId) {
		String sql = "DELETE FROM watchlist WHERE user_id = ? AND movie_id = ?";
        return jdbcTemplate.update(sql, userId, movieId);
	}

	@Override
	public List<MovieModel> getWatchlistMovies(int userId) {
        String sql = "SELECT m.* FROM movies m JOIN watchlist w ON m.movie_id = w.movie_id WHERE w.user_id = ?;";

        return jdbcTemplate.query(sql,new RowMapper<MovieModel>() {
            @Override
            public MovieModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                MovieModel movie = new MovieModel();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setMovieName(rs.getString("title"));
                movie.setYear(rs.getString("release_year"));
                movie.setDuration(rs.getString("duration"));
                movie.setDirector(rs.getString("director_name"));
                movie.setActor(rs.getString("actor_name"));
                movie.setActress(rs.getString("actress_name"));
                movie.setDescription(rs.getString("description"));
                movie.setImageName(rs.getString("image_name"));
                movie.setUrl(rs.getString("url"));
                return movie;
            }
        },userId);
    }

	@Override
	public UserModel findByPhoneNumber(String phoneNumber) {
		String sql = "SELECT * FROM users WHERE phone_number = ?";
		try {
            return jdbcTemplate.queryForObject(sql,(rs, rowNum) -> {
                UserModel user = new UserModel();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhoneNumber(rs.getString("phone_number"));
                return user;
            },phoneNumber);
        } catch (Exception e) {
            return null; // Not found
        }
	}

	@Override
	public boolean updatePasswordByPhoneNumber(String phoneNumber, String newPassword) {
		String sql = "UPDATE users SET password = ? WHERE phone_number = ?";
        int rowsAffected = jdbcTemplate.update(sql, newPassword, phoneNumber);
        return rowsAffected > 0;
	}
}
