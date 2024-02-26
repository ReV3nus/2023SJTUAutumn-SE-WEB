package com.example.book.daoimpl;

import com.example.book.dao.BookDao;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findOne(Integer id) {
        Optional<Book> optional = bookRepository.findById(id);
        return optional.orElse(null);
    }
    @Override
    public Book AddBook(Book newBook){
        return bookRepository.save(newBook);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.getBooks();
    }


    @Override
    @Transactional
    public void DeleteBook(int bid){
        bookRepository.deleteByBookId(bid);
    }
    @Override
    public Book findOneByName(String name){return bookRepository.findBookByName(name);}


}