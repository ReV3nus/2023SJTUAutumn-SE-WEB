package com.example.mybookstore_backend.entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "book")
@Data
public class MG_Book {
    @Id
    private String _id;
    @Field("id")
    private int bookId;

    private String isbn;
    private String name;
    private String type;
    private String author;
    private Double price;
    private String description;
    private Integer inventory;
    private String image;
    public MG_Book()
    {

    }
    public MG_Book(String Name,String Author,String Image,String Isbn,Integer Inventory)
    {
        name=Name;
        author=Author;
        image=Image;
        isbn=Isbn;
        inventory=Inventory;
    }
    public MG_Book(int BookID,String Name,String Author,String Image,String Isbn,Integer Inventory)
    {
        bookId=BookID;
        name=Name;
        author=Author;
        image=Image;
        isbn=Isbn;
        inventory=Inventory;
    }
    public MG_Book(Book book)
    {
        bookId=book.getBookId();
        isbn=book.getIsbn();
        name=book.getName();
        type=book.getType();
        author=book.getAuthor();
        price=book.getPrice();
        inventory=book.getInventory();
        image=book.getImage();
        description=book.getDescription();
    }
    public void Remove(int num)
    {
        inventory-=num;
    }
}
