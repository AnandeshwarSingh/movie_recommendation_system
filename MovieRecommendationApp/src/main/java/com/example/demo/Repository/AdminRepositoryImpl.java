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
import com.example.demo.Model.MovieModel;
import com.example.demo.Model.UserModel;

@Repository("adminRepo")
public class AdminRepositoryImpl implements AdminRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	Query qry;
	
	@Override
	public boolean validateAdmin(String username, String password) {
		   List<Map<String, Object>> result = jdbcTemplate.queryForList(qry.getAdmin, username, password);
	        return !result.isEmpty();
	}
	
	@Override
	public boolean isAddGenre(GenreModel genre) {
		int val=jdbcTemplate.update(qry.addGenre,genre.getName());
		return val>0;
	}

	@Override
	public List<GenreModel> getAllGenre() {
		List<GenreModel> list=jdbcTemplate.query(qry.showGenre,(ResultSet rs, int rowNum)->{
				GenreModel genre=new GenreModel();
				genre.setGenre_id(rs.getInt(1));
				genre.setName(rs.getString(2));
				return genre;
		});
		return list;
	}

	@Override
	public boolean isDeleteGenre(int id) {
			int val=jdbcTemplate.update(qry.deleteGenre,id);
			return val>0;
	}

	@Override
	public boolean isUpdateGenre(GenreModel genre) {
		int val=jdbcTemplate.update(qry.updateGenre,genre.getName(), genre.getGenre_id());
		return val>0;
	}

}
