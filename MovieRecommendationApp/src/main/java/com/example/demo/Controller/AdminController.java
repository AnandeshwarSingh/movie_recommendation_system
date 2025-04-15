package com.example.demo.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.CustomException.GenreNotFoundException;
import com.example.demo.Model.AdminModel;
import com.example.demo.Model.GenreModel;
import com.example.demo.Model.LanguageModel;
import com.example.demo.Model.MovieModel;
import com.example.demo.Service.AdminServiceImpl;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminServiceImpl adminService;
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminModel admin) {
        boolean isValid = adminService.authenticateAdmin(admin.getUsername(), admin.getPassword());
        if (isValid) {
            return ResponseEntity.ok("Admin login successful!");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
	
	@PostMapping("/addGenre")
	public String addGenre(@RequestBody GenreModel genre) {
		return (adminService.isAddGenre(genre))?"Genre Added Successfully":"OOPs Failed to Add";
	}
	
	@GetMapping("/viewAllGenre")
	public List<GenreModel> getAllGenre(){
		List<GenreModel> mlist=adminService.getAllGenre();
		if(mlist.isEmpty()) {
			throw new GenreNotFoundException("There is no data in the database");
		}
			return mlist;	
	}
	
	@GetMapping("/searchGenreById/{id}")
	public GenreModel searchGenreById(@PathVariable("id") Integer id) {
		GenreModel genre=adminService.getGenreById(id);
		if(genre!=null)
			return genre;
		else
		{
			throw new GenreNotFoundException("Genre not found using "+id);
		}	
	}
	
	@DeleteMapping("/deleteGenreById/{gid}")
	public String deleteGenreById(@PathVariable("gid") Integer id) {
		if(adminService.isDeleteGenre(id))
			return "Genre Deleted Successfully";
		else
			throw new GenreNotFoundException("Genre not found using "+id);
	}
	
	@PutMapping("/updateGenre")
	public String updateGenre(@RequestBody GenreModel genre) 	{
		boolean b=adminService.isUpdateGenre(genre);
		if(b)
			return "Genre record updated with id "+genre;
		else {
			throw new GenreNotFoundException("Genre not found using "+genre.getGenre_id());
		}
	}
	
	@PostMapping("/addLanguage")
	public String addLanguage(@RequestBody LanguageModel language) {
        return (adminService.addLanguage(language)) ? "Language added successfully" : "Failed to add language";
    }
	
	@GetMapping("/viewAllLanguage")
	public List<LanguageModel> getAllLanguages(){
		List<LanguageModel>list=adminService.getAllLanguages();
		if(list.isEmpty()) {
			throw new GenreNotFoundException("There is no data in the database");
		}
			return list;	
	}
	
	@GetMapping("/searchLanguageById/{id}")
	public LanguageModel searchLanguageById(@PathVariable("id") Integer id) {
		LanguageModel lmodel=adminService.getLanguageById(id);
		if(lmodel!=null)
			return lmodel;
		else
		{
			throw new GenreNotFoundException("Language not found using "+id);
		}	
	}
	
	@DeleteMapping("/deleteLanguageById/{lid}")
	public String deleteLanguageById(@PathVariable("lid") Integer id) {
		if(adminService.isDeleteLanguage(id))
			return "Language Deleted Successfully";
		else
			throw new GenreNotFoundException("Langauge not found using "+id);
	}
	
	@PutMapping("/updateLanguage")
	public String updateLanguage(@RequestBody LanguageModel language) 	{
		boolean b=adminService.isUpdateLanguage(language);
		if(b)
			return "Genre record updated with id "+language;
		else {
			throw new GenreNotFoundException("Genre not found using "+language.getLanguageId());
		}
	}
	
	//ADD Movie
	@PostMapping("/addMovie")
	public String addMovie(@RequestBody MovieModel movie) {
		return (adminService.addMovie(movie))?"Genre Added Successfully":"OOPs Failed to Add";
	}
	
	//ADD IMAGE
	 private static final String IMAGE_DIR = "uploaded-images/";

	    @PostMapping("/upload")
	    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
	        try {
	            // Create directory if not exists
	            File directory = new File(IMAGE_DIR);
	            if (!directory.exists()) {
	                directory.mkdirs();
	            }

	            String fileName = file.getOriginalFilename();
	            Path path = Paths.get(IMAGE_DIR + fileName);
	            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	            // Return the URL to access the image
	            String fileUrl = "/images/" + fileName;
	            System.out.println("=============> Image name :"+fileUrl);
	            return ResponseEntity.ok(fileUrl);

	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Failed to upload image");
	        }
	    }
	    
	//View All Movie
	@GetMapping("/viewAllMovie")
	public List<Map<String,Object>> getAllMovie(){
		List<Map<String,Object>> mlist=adminService.getAllMovie();
		if(mlist.isEmpty()) {
			throw new GenreNotFoundException("There is no data in the database");
		}
			return mlist;	
	}
	
	@GetMapping("/searchMovieById/{movieId}")
    public Map<String, Object> getMovieById(@PathVariable Integer movieId) {
        return adminService.getMovieById(movieId);
    }

	//Delete Movie
	@DeleteMapping("/deleteMovie/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") int movieId) {
        boolean deleted = adminService.isDeleteMovie(movieId);

        if (deleted) {
            return ResponseEntity.ok("Movie deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Movie not found with ID: " + movieId);
        }
    }
	
	//Update Movie
	@PutMapping("/updateMovie")
    public ResponseEntity<String> updateMovie(@RequestBody MovieModel movie) {
        boolean updated = adminService.isUpdateMovie(movie);
        if (updated) {
            return ResponseEntity.ok("Movie updated successfully!");
        }
        return ResponseEntity.badRequest().body("Failed to update movie!");
    }
	
	@GetMapping("/getAllMovieByGenre/{genreId}")
    public List<Map<String,Object>> getAllMovieByGenre(@PathVariable Integer genreId) {
        return adminService.getAllMovieByGenre(genreId);
    }
	
	@GetMapping("/getAllMovieByLanguage/{languageId}")
    public List<Map<String,Object>> getAllMovieByLanguage(@PathVariable Integer languageId) {
        return adminService.getAllMovieByLanguage(languageId);
    }
	
}
