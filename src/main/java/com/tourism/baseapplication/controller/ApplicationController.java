package com.tourism.baseapplication.controller;

import com.tourism.baseapplication.model.ApplicationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/application")
public class ApplicationController {
    @GetMapping
    public ResponseEntity<String> get (){
        return new ResponseEntity<>("Im working", HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationDTO> create (@RequestBody ApplicationDTO applicationDTO){
        return  new ResponseEntity<>(applicationDTO, HttpStatus.CREATED);
    }
}
