package com.example.mybookstore_backend.repository;

import com.example.mybookstore_backend.entity.MG_Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MG_BookRepository extends MongoRepository<MG_Book,String> {
    MG_Book findByBookId(Integer bookid);
    void deleteByBookId(Integer bookid);
    List<MG_Book> findAllByTypeIn(List<String> types);
    List<MG_Book> findAllByNameContaining(String name);
}
