package com.example.mybookstore_backend.dao;

import com.example.mybookstore_backend.entity.Book;
import com.example.mybookstore_backend.entity.MG_Book;

import java.util.List;

public interface BookDao {
    MG_Book findOne(Integer id);
    MG_Book AddBook(MG_Book newBook);

    List<MG_Book> getBooks();
    void DeleteBook(int bid);
    List<MG_Book> getBooksWithTypes(List<String> types);
    List<MG_Book> getBooksWithName(String name);
}
