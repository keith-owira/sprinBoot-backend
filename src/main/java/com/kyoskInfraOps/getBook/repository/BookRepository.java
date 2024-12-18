package com.kyoskInfraOps.getBook.repository;

import com.kyoskInfraOps.getBook.model.BooksModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<BooksModel,String> {

}
