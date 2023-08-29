package com.huawei.movie.controller;

import com.huawei.movie.model.Movie;
import com.huawei.movie.service.MovieService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/save")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie)
    {
        Movie addMovie = movieService.addMovie(movie);
        return new ResponseEntity<Movie>(addMovie, HttpStatus.CREATED);

    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovie()
    {
        List<Movie> allMovie=movieService.findAllMovie();
        return new ResponseEntity<List<Movie>>(allMovie,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Long id)
    {
        Movie MovieById=movieService.getMovieById(id);
        return new ResponseEntity<Movie>(MovieById,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id")Long id)
    {
        movieService.deleteMovieById(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/upd/{id}")
    public ResponseEntity<Movie> updateMovieById(@PathVariable Long id,@RequestBody Movie movieRequest)
    {
        Movie existingMovie=movieService.updateMovie(id, movieRequest);
        return new ResponseEntity<Movie>(existingMovie,HttpStatus.OK);
    }
    @PostMapping("/add-movies-from-json")
    public ResponseEntity<String> addMoviesFromJson()
    {
        movieService.addMoviesFromJsonFile();
        return ResponseEntity.ok("JSON verileri başarıyla eklendi.");
    }

    @GetMapping("/sorted-by-imdb-rating")
    public List<Movie> getMoviesSortedByImdbRating()
    {
        return movieService.getMoviesSortedByImdbRating();
    }

    @GetMapping("/sorted-by-director")
    public List<Movie> getMoviesSortedByDirector(@RequestParam(defaultValue = "ASC") String direction)
    {
        return movieService.getMoviesSortedByDirector(direction);
    }

    @GetMapping("/sorted-by-type")
    public List<Movie> getMoviesSortedByType(@RequestParam(defaultValue = "ASC")String direction)
    {
        return movieService.getMoviesSortedByType(direction);
    }

    @GetMapping("/search-by-title")
    public List<Movie> searchMoviesByTitle(@RequestParam String title)
    {
        return movieService.searchMoviesByTitle(title);
    }
}
