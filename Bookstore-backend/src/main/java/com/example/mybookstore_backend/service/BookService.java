package com.example.mybookstore_backend.service;

import com.example.mybookstore_backend.entity.MG_Book;

import java.util.List;

public interface BookService {

    MG_Book findBookById(Integer id);

    List<MG_Book> getBooks();
    MG_Book SaveBook(MG_Book b);
    void DeleteBook(int bid);
    List<MG_Book> getRelatedBooksByTagName(String tagName);
    List<MG_Book> getBooksByName(String name);
}
