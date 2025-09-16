package com.app.bibliotecauniversitariapa.controllers;

import com.app.bibliotecauniversitariapa.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/authors")
@CrossOrigin
public class AuthorController {
    @Autowired
    private AuthorService authorService;
}
