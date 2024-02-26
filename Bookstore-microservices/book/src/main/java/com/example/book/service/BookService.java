package com.example.book.service;

import com.example.book.entity.Book;

import java.util.List;

public interface BookService {

    Book findBookById(Integer id);

    List<Book> getBooks();
    Book SaveBook(Book b);
    void DeleteBook(int bid);
    String getAuthorByName(String name);
}
