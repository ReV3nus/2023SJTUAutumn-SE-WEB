package com.example.mybookstore_backend.controller;

import com.example.mybookstore_backend.entity.MG_Book;
import com.example.mybookstore_backend.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiBook")
public class BooksController{

    @Autowired
    private BookService bookService;

    @QueryMapping
    public List<MG_Book> findBooksByName(@Argument String name) {
        return bookService.getBooksByName(name);
    }
    @GetMapping("/getbooks")
    public ResponseEntity<List<MG_Book>> getBooks()
    {
        return ResponseEntity.ok(bookService.getBooks());
//        return ;
    }
    @GetMapping("/getbook")
    public ResponseEntity<MG_Book> getBook(@RequestParam("id") Integer id)
    {
        return ResponseEntity.ok(bookService.findBookById(id));
    }
    @GetMapping("/GetBooksByTag")
    public ResponseEntity<List<MG_Book>> getBooksByTag(@RequestParam("tagName") String tagName)
    {
        return ResponseEntity.ok(bookService.getRelatedBooksByTagName(tagName));
    }
    @PostMapping("/AddBook")
    public ResponseEntity<MG_Book> addBook(@RequestBody Map<String, String> BookData)
    {
        String name = BookData.get("name");
        String author = BookData.get("author");
        String url = BookData.get("url");
        String isbn = BookData.get("isbn");
        Integer inventory = Integer.parseInt(BookData.get("inventory"));
        MG_Book newBook=new MG_Book(name,author,url,isbn,inventory);
        bookService.SaveBook(newBook);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/ModifyBook")
    public ResponseEntity<MG_Book> modifyBook(@RequestBody Map<String, String> BookData)
    {
        int bookid=Integer.parseInt(BookData.get("bookid"));
        String name = BookData.get("name");
        String author = BookData.get("author");
        String url = BookData.get("url");
        String isbn = BookData.get("isbn");
        Integer inventory = Integer.parseInt(BookData.get("inventory"));
        MG_Book newBook=new MG_Book(bookid,name,author,url,isbn,inventory);
        bookService.SaveBook(newBook);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/DeleteBook")
    public ResponseEntity<MG_Book> deleteBook(@RequestBody Map<String,String> BookData)
    {
        int bookid=Integer.parseInt(BookData.get("bookid"));
        System.out.println(bookid);
        bookService.DeleteBook(bookid);
        return ResponseEntity.ok().build();
    }
}