package com.huawei.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.huawei.movie.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

   /* List<Movie> findByImdbRating(Double imdbRating);
    List<Movie> findByDirectorContaining(String director);
    List<Movie> findByTitle(String type);*/



}
