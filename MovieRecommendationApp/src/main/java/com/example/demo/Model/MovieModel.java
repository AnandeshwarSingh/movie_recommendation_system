package com.example.demo.Model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieModel extends RatingModel {
	
	private int movie_id;
	private String movieName;
	private String duration;
	private String genres;
	private String language;
	private String year;
	private String director;
	private String actor;
	private String actress;
	private String description;
	
}
