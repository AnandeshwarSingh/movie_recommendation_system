package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DashboardStats {
	private int totalMovies;
    private int totalLanguages;
    private int totalGenres;
    private int totalUsers;
    private int totalReviews;
    private int totalEnquiry;
}
