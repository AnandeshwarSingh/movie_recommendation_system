package com.example.demo.Service;

import com.example.demo.Model.MovieModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.*;

@Service
public class CollaborativeFilteringService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getRecommendationsForUser(String username) {

        // Step 1: Fetch user IDs
        Map<Integer, String> userIdToName = new HashMap<>();
        Map<String, Integer> nameToUserId = new HashMap<>();
        jdbcTemplate.query("SELECT user_id, name FROM users", rs -> {
            int id = rs.getInt("user_id");
            String name = rs.getString("name");
            userIdToName.put(id, name);
            nameToUserId.put(name, id);
        });

        Integer targetUserId = nameToUserId.get(username);
        if (targetUserId == null) return Collections.emptyList();

        // Step 2: Fetch ratings
        String sql = "SELECT user_id, movie_id, rating FROM ratings";
        Map<Integer, Map<Integer, Double>> userRatings = new HashMap<>();

        jdbcTemplate.query(sql, rs -> {
            int userId = rs.getInt("user_id");
            int movieId = rs.getInt("movie_id");
            double rating = rs.getDouble("rating");
            userRatings.computeIfAbsent(userId, k -> new HashMap<>()).put(movieId, rating);
        });

        Map<Integer, Double> targetRatings = userRatings.get(targetUserId);
        if (targetRatings == null || targetRatings.isEmpty()) {
            return getDefaultRecommendations();
        }

        // Step 3: Similarity calculation
        Map<Integer, Double> similarities = new HashMap<>();
        for (Map.Entry<Integer, Map<Integer, Double>> entry : userRatings.entrySet()) {
            int otherUserId = entry.getKey();
            if (otherUserId == targetUserId) continue;

            double similarity = computeCosineSimilarity(targetRatings, entry.getValue());
            similarities.put(otherUserId, similarity);
        }

        // Step 4: Weighted scoring
        Map<Integer, Double> weightedScores = new HashMap<>();
        Map<Integer, Double> similaritySums = new HashMap<>();
        for (Map.Entry<Integer, Map<Integer, Double>> entry : userRatings.entrySet()) {
            int otherUserId = entry.getKey();
            double sim = similarities.getOrDefault(otherUserId, 0.0);
            if (sim <= 0) continue;

            for (Map.Entry<Integer, Double> movieEntry : entry.getValue().entrySet()) {
                int movieId = movieEntry.getKey();
                if (targetRatings.containsKey(movieId)) continue;

                weightedScores.put(movieId,
                        weightedScores.getOrDefault(movieId, 0.0) + sim * movieEntry.getValue());
                similaritySums.put(movieId,
                        similaritySums.getOrDefault(movieId, 0.0) + sim);
            }
        }

        // Step 5: Normalize
        Map<Integer, Double> predictedRatings = new HashMap<>();
        for (Map.Entry<Integer, Double> entry : weightedScores.entrySet()) {
            int movieId = entry.getKey();
            predictedRatings.put(movieId, entry.getValue() / similaritySums.get(movieId));
        }

        // Step 6: Sort and collect top results
        List<Map.Entry<Integer, Double>> sorted = new ArrayList<>(predictedRatings.entrySet());
        sorted.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        List<Map<String, Object>> recommendedMovies = new ArrayList<>();
        for (int i = 0; i < Math.min(10, sorted.size()); i++) {
            int movieId = sorted.get(i).getKey();
            Map<String, Object> movieMap = getMovieByIdWithNames(movieId);
            if (movieMap != null) {
                recommendedMovies.add(movieMap);
            }
        }

        return recommendedMovies;
    }

    private double computeCosineSimilarity(Map<Integer, Double> a, Map<Integer, Double> b) {
        Set<Integer> common = new HashSet<>(a.keySet());
        common.retainAll(b.keySet());
        if (common.isEmpty()) return 0;

        double dot = 0, normA = 0, normB = 0;
        for (Integer key : common) {
            dot += a.get(key) * b.get(key);
        }
        for (double val : a.values()) normA += val * val;
        for (double val : b.values()) normB += val * val;

        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private Map<String, Object> getMovieByIdWithNames(int movieId) {
        String sql = "SELECT m.movie_id, m.title, m.release_year, m.duration, m.director_name, " +
                "m.actor_name, m.actress_name, m.description, m.image_name, m.url, " +
                "mg.genre_id, ml.language_id, g.name, l.language_name " +
                "FROM movies m " +
                "LEFT JOIN movie_genres mg ON m.movie_id = mg.movie_id " +
                "LEFT JOIN genres g ON mg.genre_id = g.genre_id " +
                "LEFT JOIN movie_languages ml ON m.movie_id = ml.movie_id " +
                "LEFT JOIN language l ON ml.language_id = l.language_id " +
                "WHERE m.movie_id = ?";

        List<Map<String, Object>> result = jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
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
            movie.setGenreid(rs.getInt("genre_id"));
            movie.setLanguageid(rs.getInt("language_id"));
            movie.setUrl(rs.getString("url"));

            Map<String, Object> map = new HashMap<>();
            map.put("movie", movie);
            map.put("genre", rs.getString("name"));
            map.put("language", rs.getString("language_name"));
            return map;
        }, movieId);

        return result.isEmpty() ? null : result.get(0);
    }

    private List<Map<String, Object>> getDefaultRecommendations() {
        String sql = "SELECT m.movie_id, m.title, m.release_year, m.duration, m.director_name, " +
                "m.actor_name, m.actress_name, m.description, m.image_name, m.url, " +
                "mg.genre_id, ml.language_id, g.name, l.language_name " +
                "FROM movies m " +
                "LEFT JOIN movie_genres mg ON m.movie_id = mg.movie_id " +
                "LEFT JOIN genres g ON mg.genre_id = g.genre_id " +
                "LEFT JOIN movie_languages ml ON m.movie_id = ml.movie_id " +
                "LEFT JOIN language l ON ml.language_id = l.language_id " +
                "ORDER BY m.release_year DESC LIMIT 10";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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
            movie.setGenreid(rs.getInt("genre_id"));
            movie.setLanguageid(rs.getInt("language_id"));
            movie.setUrl(rs.getString("url"));

            Map<String, Object> map = new HashMap<>();
            map.put("movie", movie);
            map.put("genre", rs.getString("name"));
            map.put("language", rs.getString("language_name"));
            return map;
        });
    }
}
