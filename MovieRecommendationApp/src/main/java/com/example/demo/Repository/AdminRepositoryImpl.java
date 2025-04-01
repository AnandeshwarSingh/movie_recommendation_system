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
	
	@Override
	public boolean validateAdmin(String username, String password) {
		   String sql="select * from admin WHERE  name=? AND password=?;";
		   List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, username, password);
	        return !result.isEmpty();
	}
	
	@Override
	public boolean isAddGenre(GenreModel genre) {
		String sql="insert into genres values('0',?);";
		int val=jdbcTemplate.update(sql,genre.getName());
		return val>0;
	}

	@Override
	public List<GenreModel> getAllGenre() {
		String sql="select * from genres";
		List<GenreModel> list=jdbcTemplate.query(sql,(ResultSet rs, int rowNum)->{
				return new GenreModel(rs.getInt(1),rs.getString(2));
		});
		return list;
	}

	@Override
	public boolean isDeleteGenre(int id) {
		String sql="delete from genres where  genre_id=?;";
			int val=jdbcTemplate.update(sql,id);
			return val>0;
	}

	@Override
	public boolean isUpdateGenre(GenreModel genre) {
		String sql="update genres set name=? where genre_id=?;";
		int val=jdbcTemplate.update(sql,genre.getName(), genre.getGenre_id());
		return val>0;
	}

	@Override
	public boolean addLanguage(LanguageModel language) {
		String sql = "INSERT INTO language VALUES ('0',?)";
		int val=jdbcTemplate.update(sql,language.getLanguageName());
        return val>0;
	}

	@Override
	public List<LanguageModel> getAllLanguages() {
		  String sql = "SELECT * FROM language";
		  List<LanguageModel> list=jdbcTemplate.query(sql, new RowMapper<LanguageModel>() {
	            @Override
	            public LanguageModel mapRow(ResultSet rs, int rowNum) throws SQLException {
	                return new LanguageModel(rs.getInt(1),rs.getString(2));
	            }
	        });
		  return list;
	}

	@Override
	public LanguageModel getLanguageById(int id) {
		String sql = "SELECT * FROM language WHERE language_id = ?";
		//Search Incomplete
		return null;
	}

	@Override
	public boolean isUpdateLanguage(LanguageModel language) {
		String sql = "UPDATE language SET language_name = ? WHERE language_id = ?";
		int val=jdbcTemplate.update(sql, language.getLanguageName(), language.getLanguageId());
		return val>0;
	}

	@Override
	public boolean isDeleteLanguage(int id) {
		String sql = "DELETE FROM language WHERE language_id = ?";
		int val=jdbcTemplate.update(sql, id);
		return val>0;
	}

}
