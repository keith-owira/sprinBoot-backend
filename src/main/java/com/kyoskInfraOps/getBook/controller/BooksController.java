package com.kyoskInfraOps.getBook.controller;

import com.kyoskInfraOps.getBook.model.BooksModel;
import com.kyoskInfraOps.getBook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class BooksController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public List<BooksModel> getBookDetails(){
        return  bookRepository.findAll();
    }

}
