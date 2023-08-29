package com.huawei.movie.service;

import com.huawei.movie.model.Movie;
import com.huawei.movie.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    //Mock sayesinde MovieRepository ornegi olusturdum.
    @Mock
    private MovieRepository movieRepository;


    //Calismadan once assagidaki islemlerin gerceklesmesini saglar
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        movieService = new MovieService(movieRepository);
    }

    @Test
    public void testGetMovieById() {
        // Test icin kullanilacak ornek bir film verisi olusturdum
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");

        // MovieRepository'den findById methodunun kopyasini olusturdum.
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        // getMovieById metodunu çağirdim
        Movie result = movieService.getMovieById(1L);

        // Sonuclarin kontrolleri yapiliyor
        assertEquals(1L, result.getId());
        assertEquals("Test Movie", result.getTitle());

        // MovieRepository'nin findById metodunun bir kez çagirdigini dogrular
        verify(movieRepository, times(1)).findById(1L);
    }



    @Test
    public void testSearchMoviesByTitle() {
        // Test için kullanilacak ornek test verisi olusturdum
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Avatar");

        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("Vikings");

        // MovieRepository'den donecek olan sonucu ayarlaadim
        when(movieRepository.findByTitleContaining("Avatar")).thenReturn(Arrays.asList(movie1));
        when(movieRepository.findByTitleContaining("Vikings")).thenReturn(Arrays.asList(movie2));

        // MovieService uzerinden searchMoviesByTitle metodunu cagirdim
        List<Movie> result1 = movieService.searchMoviesByTitle("Avatar");
        List<Movie> result2 = movieService.searchMoviesByTitle("Vikings");

        // Sonuclari kontrol ettim
        assertEquals(1, result1.size());
        assertEquals("Avatar", result1.get(0).getTitle());

        assertEquals(1, result2.size());
        assertEquals("Vikings", result2.get(0).getTitle());
    }

    @Test
    public void testAddMovie() {
        // Test için kullanilacak ornek film verisi olusturdum
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDirector("Test Director");

        // MovieRepository'nin save metodunun kopyasini aldim ve filmi kaydettim
        when(movieRepository.save(movie)).thenReturn(movie);

        // MovieService uzerinden addMovie metodunu cagirdim
        Movie result = movieService.addMovie(movie);

        // Sonuclarin kontrolunu yaptim
        verify(movieRepository, times(1)).save(movie);
        assertEquals("Test Movie", result.getTitle());
        assertEquals("Test Director", result.getDirector());
    }


    @Test
    public void testDeleteMovie() {
        // Test için kullanilacak ornek bir film verisi olusturdum
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");

        // MovieRepository'nin deleteById metodunun kopyasini aldim
        doNothing().when(movieRepository).deleteById(1L);

        // MovieService uzerinden deleteMovie metodunu cagirdim
        movieService.deleteMovieById(1L);

        // MovieRepository'nin deleteById metodunun bir kez cagrildigini dogruladim
        verify(movieRepository, times(1)).deleteById(1L);
    }


}