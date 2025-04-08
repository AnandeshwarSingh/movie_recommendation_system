package com.example.demo.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.AdminModel;
import com.example.demo.Model.GenreModel;
import com.example.demo.Model.LanguageModel;
import com.example.demo.Model.MovieModel;
import com.example.demo.Model.UserModel;

@Repository("adminRepo")
public class AdminRepositoryImpl implements AdminRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//LOGIN
	@Override
	public boolean validateAdmin(String username, String password) {
		   String sql="select * from admin WHERE  name=? AND password=?;";
		   List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, username, password);
	        return !result.isEmpty();
	}
	
	//ADD GENRE
	@Override
	public boolean isAddGenre(GenreModel genre) {
		String sql="insert into genres values('0',?);";
		int val=jdbcTemplate.update(sql,genre.getName());
		return val>0;
	}

	//VIEW GENRE
	@Override
	public List<GenreModel> getAllGenre() {
		String sql="select * from genres order by genre_id asc";
		List<GenreModel> list=jdbcTemplate.query(sql,(ResultSet rs, int rowNum)->{
				return new GenreModel(rs.getInt(1),rs.getString(2));
		});
		return list;
	}

	//SEARCH GENRE
	@Override
	public GenreModel getGenreById(int id) {
		List<GenreModel>list=jdbcTemplate.query("select * from genres where genre_id=?",new RowMapper<GenreModel>() {

			@Override
			public GenreModel mapRow(ResultSet rs, int rowNum) throws SQLException {
				GenreModel genre=new GenreModel();
				genre.setGenre_id(rs.getInt(1));
				genre.setName(rs.getString(2));
				return genre;
			}
		},id);
		return list.size()>0?list.get(0):null;
	}

	
	//DELETE GENRE
	@Override
	public boolean isDeleteGenre(int id) {
		String sql="delete from genres where  genre_id=?;";
			int val=jdbcTemplate.update(sql,id);
			return val>0;
	}

	//UPDATE GENRE
	@Override
	public boolean isUpdateGenre(GenreModel genre) {
		String sql="update genres set name=? where genre_id=?;";
		int val=jdbcTemplate.update(sql,genre.getName(), genre.getGenre_id());
		return val>0;
	}
	
	// Add Language

	@Override
	public boolean addLanguage(LanguageModel language) {
		String sql = "INSERT INTO language VALUES ('0',?)";
		int val=jdbcTemplate.update(sql,language.getLanguageName());
        return val>0;
	}
	
	// View Language
	@Override
	public List<LanguageModel> getAllLanguages() {
		  String sql = "SELECT * FROM language order by language_id asc";
		  List<LanguageModel> list=jdbcTemplate.query(sql, new RowMapper<LanguageModel>() {
	            @Override
	            public LanguageModel mapRow(ResultSet rs, int rowNum) throws SQLException {
	                return new LanguageModel(rs.getInt(1),rs.getString(2));
	            }
	        });
		  return list;
	}
	
	//Get Language By Id

	@Override
	public LanguageModel getLanguageById(int id) {
		String sql = "SELECT * FROM language WHERE language_id = ?";
		List<LanguageModel>list=jdbcTemplate.query(sql,new RowMapper<LanguageModel>() {

			@Override
			public LanguageModel mapRow(ResultSet rs, int rowNum) throws SQLException {
				LanguageModel lmodel=new LanguageModel();
				lmodel.setLanguageId(rs.getInt(1));
				lmodel.setLanguageName(rs.getString(2));
				return lmodel;
			}
		},id);
		return list.size()>0?list.get(0):null;
	}
	
	// Update Language
	@Override
	public boolean isUpdateLanguage(LanguageModel language) {
		String sql = "UPDATE language SET language_name = ? WHERE language_id = ?";
		int val=jdbcTemplate.update(sql, language.getLanguageName(), language.getLanguageId());
		return val>0;
	}
	
	// Delete Language
	@Override
	public boolean isDeleteLanguage(int id) {
		String sql = "DELETE FROM language WHERE language_id = ?";
		int val=jdbcTemplate.update(sql, id);
		return val>0;
	}

	//ADD Movie
	@Override
	public boolean addMovie(MovieModel movie) {
		 String sql = "INSERT INTO movies (title, release_year, description, duration, director_name, actor_name, actress_name, image_name) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

		    // Insert movie details
		    int val = jdbcTemplate.update(sql, movie.getMovieName(), movie.getYear(), movie.getDescription(), movie.getDuration(), movie.getDirector(), movie.getActor(), movie.getActress(), movie.getImageName());

		    // Retrieve the last inserted movie_id
		    Integer movieId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

		    if (val > 0 && movieId != null) {
		        // Insert movie-genre relation
		        jdbcTemplate.update("INSERT INTO movie_genres (movie_id, genre_id) VALUES (?, ?)", movieId, movie.getGenreid());
		        // Insert movie-language relation
		        jdbcTemplate.update("INSERT INTO movie_languages (movie_id, language_id) VALUES (?, ?)", movieId, movie.getLanguageid());
		        return true;
		    }
		    return false;
	}

	//View All Movie
	@Override
	public List<Map<String,Object>> getAllMovie() {
		String sql="SELECT m.movie_id, m.title, m.release_year, m.description,m.duration,m.director_name, m.actor_name, m.actress_name,m.image_name ,GROUP_CONCAT(DISTINCT g.name) AS genres,GROUP_CONCAT(DISTINCT l.language_name) AS language FROM movies m  LEFT JOIN movie_genres mg ON m.movie_id = mg.movie_id LEFT JOIN genres g ON mg.genre_id = g.genre_id LEFT JOIN movie_languages ml ON m.movie_id = ml.movie_id LEFT JOIN language l ON ml.language_id = l.language_id GROUP BY m.movie_id order by m.movie_id asc;";
		
		return jdbcTemplate.queryForList(sql);
	}

	//Delete Movie
	@Override
	public boolean isDeleteMovie(int id) {
		String deletemovieSql = "DELETE FROM movies WHERE movie_id = ?";
        int val=jdbcTemplate.update(deletemovieSql, id);
		return val>0;
	}

	//Update Movie
	@Override
	public boolean isUpdateMovie(MovieModel movie) {
		String sql = "UPDATE movies SET title=?, release_year=?, description=?, duration=?,director_name=?, actor_name=?, actress_name=? WHERE movie_id=?";
		
		int updatedRows = jdbcTemplate.update(sql, movie.getMovieName(), movie.getYear(), movie.getDescription(), 
				    movie.getDuration(), movie.getDirector(), movie.getActor(), 
	                movie.getActress(), movie.getMovieId());
		if(updatedRows>0) {
			 jdbcTemplate.update("DELETE FROM movie_genres WHERE movie_id=?", movie.getMovieId());
			 jdbcTemplate.update("INSERT INTO movie_genres (movie_id, genre_id) VALUES (?, ?)", movie.getMovieId(), movie.getGenreid());
			 
			 jdbcTemplate.update("DELETE FROM movie_languages WHERE movie_id=?", movie.getMovieId());
			 jdbcTemplate.update("INSERT INTO movie_languages (movie_id, language_id) VALUES (?, ?)", movie.getMovieId(), movie.getLanguageid());
			 
			 return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> getMovieById(int id) {
		String sql = "SELECT m.movie_id, m.title, m.release_year, m.description, m.duration, " +
                "m.director_name, m.actor_name, m.actress_name,m.created_at, " +
                "GROUP_CONCAT(DISTINCT g.name) AS genres, " +
                "GROUP_CONCAT(DISTINCT l.language_name) AS language " +
                "FROM movies m " +
                "LEFT JOIN movie_genres mg ON m.movie_id = mg.movie_id " +
                "LEFT JOIN genres g ON mg.genre_id = g.genre_id " +
                "LEFT JOIN movie_languages ml ON m.movie_id = ml.movie_id " +
                "LEFT JOIN language l ON ml.language_id = l.language_id " +
                "WHERE m.movie_id = ? " +
                "GROUP BY m.movie_id";

   return jdbcTemplate.queryForMap(sql, id);
	}
		
}
