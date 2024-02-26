package com.example.mybookstore_backend.daoimpl;

import com.example.mybookstore_backend.dao.BookDao;
import com.example.mybookstore_backend.entity.Book;
import com.example.mybookstore_backend.entity.MG_Book;
import com.example.mybookstore_backend.repository.BookRepository;
import com.example.mybookstore_backend.repository.MG_BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MG_BookRepository mg_bookRepository;

    @Override
    public MG_Book findOne(Integer id) {
//        Optional<Book> optional = bookRepository.findById(id);
//        return optional.orElse(null);
        return mg_bookRepository.findByBookId(id);
    }
    @Override
    public MG_Book AddBook(MG_Book newBook)
    {
        return mg_bookRepository.save(newBook);
    }

    @Override
    public List<MG_Book> getBooks() {
        return mg_bookRepository.findAll();
    }


    @Override
    @Transactional
    public void DeleteBook(int bid){
        mg_bookRepository.deleteByBookId(bid);
    }
    @Override
    public List<MG_Book> getBooksWithTypes(List<String> types){return mg_bookRepository.findAllByTypeIn(types);}
    @Override
    public List<MG_Book> getBooksWithName(String name){return mg_bookRepository.findAllByNameContaining(name);}

}