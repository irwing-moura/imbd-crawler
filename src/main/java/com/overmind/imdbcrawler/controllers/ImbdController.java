package com.overmind.imdbcrawler.controllers;

import com.overmind.imdbcrawler.services.SearchTenWorstMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImbdController {

    @Autowired
    private SearchTenWorstMovies searchTenWorstMovies;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> index() {
        return new ResponseEntity<>(searchTenWorstMovies.searchImdbWorstMovies().get(0), HttpStatus.OK);
    }
}
