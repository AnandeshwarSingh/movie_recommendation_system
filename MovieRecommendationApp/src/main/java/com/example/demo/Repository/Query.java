package com.example.demo.Repository;

public class Query {

	static String getAdmin="select * from admin WHERE  name=? AND password=?;";
	static String addGenre="insert into genres values('0',?);";
	static String showGenre="select * from genres";
	static String deleteGenre="delete from genres where  genre_id=?;";
	static String updateGenre="update genres set name=? where genre_id=?;";
	static String getGenre="select * from genres where genre_id=?";
}
