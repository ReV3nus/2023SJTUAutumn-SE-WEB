package com.example.book.dao;

import com.example.book.entity.Book;

import java.util.List;

public interface BookDao {
    Book findOne(Integer id);
    Book findOneByName(String name);
    Book AddBook(Book newBook);

    List<Book> getBooks();
    void DeleteBook(int bid);
}
