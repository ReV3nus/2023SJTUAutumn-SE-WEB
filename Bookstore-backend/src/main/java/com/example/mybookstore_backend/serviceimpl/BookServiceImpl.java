package com.example.mybookstore_backend.serviceimpl;

import com.example.mybookstore_backend.dao.BookDao;
import com.example.mybookstore_backend.entity.MG_Book;
import com.example.mybookstore_backend.service.BookService;
import com.example.mybookstore_backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TagService tagService;

    @Override
    public MG_Book findBookById(Integer id){
//        MG_Book book=null;
//        String bookJson = null;
//        try {
//            bookJson = (String) redisTemplate.opsForValue().get("book" + id);
//            System.out.println("Finding book " + id + " in Redis");
//        }
//        catch (Exception e)
//        {
//            System.err.println(e.getMessage());
//            return bookDao.findOne(id);
//        }
//        if (bookJson == null) {
//            System.out.println("Finding book " + id + " in Repository");
//            book = bookDao.findOne(id);
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                bookJson = objectMapper.writeValueAsString(book);
//                redisTemplate.opsForValue().set("book" + id, bookJson);
//            } catch (JsonProcessingException e) {
//                System.err.println(e.getMessage());
//            }
//        } else {
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                book = objectMapper.readValue(bookJson, MG_Book.class);
//            } catch (JsonProcessingException e) {
//                System.err.println(e.getMessage());
//            }
//            System.out.println("Book: " + id + " is in Redis");
//        }
//        return book;
        return bookDao.findOne(id);
    }

    @Override
    public List<MG_Book> getBooks() {
        return bookDao.getBooks();
    }
    @Override
    public MG_Book SaveBook(MG_Book b){
//        ObjectMapper objectMapper = new ObjectMapper();
//        String bookJson=null;
//        System.out.println("Saving book " + b.getBookId() + " in Redis");
//        try {
//            bookJson = objectMapper.writeValueAsString(b);
//            redisTemplate.opsForValue().set("book" + b.getBookId(), bookJson);
//        } catch (JsonProcessingException e) {
//            System.err.println(e.getMessage());
//        }
        return bookDao.AddBook(b);
    }
    @Override
    @Transactional
    public void DeleteBook(int bid){
//        try{
//            redisTemplate.opsForValue().getOperations().delete("book"+bid);
//            System.out.println("Removing book " + bid + " in Redis");
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
        bookDao.DeleteBook(bid);
    }

    @Override
    public List<MG_Book> getRelatedBooksByTagName(String tagName){
        List<String> types = tagService.getRelatedTags(tagName);

        return bookDao.getBooksWithTypes(types);
    }
    @Override
    public List<MG_Book> getBooksByName(String name){return bookDao.getBooksWithName(name);}
}
