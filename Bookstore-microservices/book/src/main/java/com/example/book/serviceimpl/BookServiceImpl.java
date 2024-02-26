package com.example.book.serviceimpl;

import com.example.book.dao.BookDao;
import com.example.book.entity.Book;
import com.example.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public Book findBookById(Integer id){
        return bookDao.findOne(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }
    @Override
    public Book SaveBook(Book b){
        return bookDao.AddBook(b);
    }
    @Override
    @Transactional
    public void DeleteBook(int bid){
        bookDao.DeleteBook(bid);
    }
    @Override
    public String getAuthorByName(String name){
        Book book=bookDao.findOneByName(name);
        if(book!=null)
            return book.getAuthor();
        return "";
    }
}
