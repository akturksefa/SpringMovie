package com.huawei.movie.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huawei.movie.model.Movie;
import com.huawei.movie.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private  MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) { this.movieRepository=movieRepository;}

    public Movie addMovie(Movie movie)
    {
        return movieRepository.save(movie);
    }

    public List<Movie> findAllMovie()
    {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long movieId)
    {
        return movieRepository.findById(movieId).get();
    }

    public Movie updateMovie(Long id,Movie movieRequest)
    {
        Movie existingMovie=movieRepository.findById(id).get();
        ////////////////////////////////////////////////////////////
        if (movieRequest.getTitle() != null){
            existingMovie.setTitle(movieRequest.getTitle());}
        if (movieRequest.getCountry()!=null){
            existingMovie.setCountry(movieRequest.getCountry());}
        if(movieRequest.getYear()!=null){
            existingMovie.setYear(movieRequest.getYear());}
        if (movieRequest.getActors()!=null){
            existingMovie.setActors(movieRequest.getActors());}
        if(movieRequest.getComingSoon()!=null){
            existingMovie.setComingSoon(movieRequest.getComingSoon());}
        if(movieRequest.getDirector()!=null){
            existingMovie.setDirector(movieRequest.getDirector());}
        if(existingMovie.getAwards()!=null){
            existingMovie.setAwards(movieRequest.getAwards());}
        if (existingMovie.getGenre()!=null){
            existingMovie.setGenre(movieRequest.getGenre());}
        if (existingMovie.getWriter()!=null){
            existingMovie.setWriter(movieRequest.getWriter());}
        if (existingMovie.getReleased()!=null){
            existingMovie.setReleased(movieRequest.getReleased());}
        if (existingMovie.getType()!=null){
            existingMovie.setType(movieRequest.getType());}
        if (existingMovie.getRuntime()!=null){
            existingMovie.setRuntime(movieRequest.getRuntime());}
        if (existingMovie.getLanguage()!=null){
            existingMovie.setLanguage(movieRequest.getLanguage());}
        if (existingMovie.getPlot()!=null){
            existingMovie.setPlot(movieRequest.getPlot());}

        return movieRepository.save(existingMovie);
    }

    public void deleteMovieById(Long movieId)
    {
        movieRepository.deleteById(movieId);
    }

    private List<Movie> movies = new ArrayList<>();
    public void addMoviesFromJsonFile() {
        try {
            ClassPathResource resource = new ClassPathResource("json/data.json");
            Reader reader = new InputStreamReader(resource.getInputStream());

            Gson gson = new GsonBuilder().create();
            Movie[] moviesArray = gson.fromJson(reader, Movie[].class);


            Collections.addAll(movies, moviesArray);
            movieRepository.saveAll(movies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> getMoviesSortedByImdbRating() {
        Sort sortByImdbRating = Sort.by(Sort.Direction.DESC, "imdbRating");
        return movieRepository.findAll(sortByImdbRating);
    }

    public List<Movie> getMoviesSortedByDirector(String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), "director");
        return movieRepository.findAll(sort);
    }

    public List<Movie> getMoviesSortedByType(String direction) {
        Sort sort=Sort.by(Sort.Direction.fromString(direction),"type");
        return movieRepository.findAll(sort);

    }

    public List<Movie> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContaining(title);
    }

}
