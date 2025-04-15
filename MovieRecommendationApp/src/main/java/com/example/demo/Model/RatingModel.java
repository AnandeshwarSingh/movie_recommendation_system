package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingModel{
	private int rateId;
	private int userId;
	private int movieId;
	private Float rating;
	private String review;
}
