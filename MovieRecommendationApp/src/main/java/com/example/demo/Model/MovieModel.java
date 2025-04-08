package com.example.demo.Model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieModel{
	
	private int movieId;
	private String movieName;
	private String year;
	private String duration;
	private String imageUrl;
	private String director;
	private String actor;
	private String actress;
	private String description;
	private String imageName;
	private int genreid;
	private int languageid;
	
}
