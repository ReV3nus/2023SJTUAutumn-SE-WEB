package com.example.mybookstore_backend.controller;

import com.example.mybookstore_backend.entity.*;
//import com.example.mybookstore_backend.kafka.KafkaProducer;
import com.example.mybookstore_backend.service.BookService;
import com.example.mybookstore_backend.service.CartService;
import com.example.mybookstore_backend.service.OrderItemService;
import com.example.mybookstore_backend.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.apache.kafka.common.KafkaException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apiOrder")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CartService cartService;
//    @Autowired
//    private KafkaProducer kafkaProducer;
    @PostMapping("/addItem")
    public ResponseEntity<String> AddToOrder(@RequestBody Integer BookId, HttpSession session) {
        String username=(String) session.getAttribute("username");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

//        Map<String,String> map = new HashMap<>();
//        map.put("username",username);
//        map.put("BookId",BookId.toString());
//        map.put("time",formattedDateTime);
//
//        try {
//            kafkaProducer.sendMessage("item-order",map);
//            return ResponseEntity.ok("Order placed successfully!");
//        } catch (KafkaException e) {
//            System.err.println(e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kafka Consumer Error: " + e.getMessage());
//        }
        MG_Book optBook=bookService.findBookById(BookId);

        optBook.Remove(1);
        bookService.SaveBook(optBook);

        OrderRecord orderRecord=new OrderRecord(username,formattedDateTime);
        OrderItem orderItem=new OrderItem(optBook.getBookId(),optBook.getName(),optBook.getAuthor(),optBook.getType(),optBook.getPrice(),1,username);
        orderItem.setOrderRecord(orderRecord);
        orderRecord.getOrderItems().add(orderItem);
        orderService.AddRecordToOrder(orderRecord);
        orderItemService.AddItemToOrder(orderItem);
        return ResponseEntity.ok("Order placed successfully!");
}
    @PostMapping("/cartPurchase")
    public ResponseEntity<String> CartPurchase(HttpSession session) {
        String username=(String) session.getAttribute("username");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

//        Map<String,String> map = new HashMap<>();
//        map.put("username",username);
//        map.put("time",formattedDateTime);
//
//
//        try {
//            kafkaProducer.sendMessage("cart-order",map);
//            return ResponseEntity.ok("Cart cleared successfully!");
//        } catch (KafkaException e) {
//            System.err.println(e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kafka Consumer Error: " + e.getMessage());
//        }

        List<MG_Book> OptBooks = new ArrayList<>();
        List<CartRecord> UserCart=cartService.getCart(username);
        for (CartRecord cartRecord : UserCart) {
            MG_Book optBook = bookService.findBookById(cartRecord.getBookid());
            optBook.Remove(1);
            bookService.SaveBook(optBook);
            OptBooks.add(optBook);
        }
        cartService.ClearCart(username);
        orderService.CreateRecordOfBooks(OptBooks,username,formattedDateTime);
        return ResponseEntity.ok("Cart cleared successfully!");
    }
    @GetMapping("/getOrderIds")
    public ResponseEntity<List<OrderRecord>> GetOrderIds(HttpSession session) {
        String username=(String) session.getAttribute("username");
        String usertype=(String) session.getAttribute("authority");

        List<OrderRecord>ret = null;
        if(Objects.equals(usertype, "User")) {
            ret=orderService.getOrder(username);
        }
        else if(Objects.equals(usertype, "Admin")){
            ret=orderService.getAllOrders();
        }
        Comparator<OrderRecord> comparator = Comparator.comparing(o -> {
            String timeStr = o.getTime(); // 假设时间字段名为 time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(timeStr, formatter);
        }, Comparator.reverseOrder());

        // 使用自定义比较器进行排序
        if (ret != null) {
            ret = ret.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(ret);
    }
    @GetMapping("/getOrderItems")
    public ResponseEntity<List<OrderItem>> GetOrderItems(@RequestParam("oid") Integer oid) {
        System.out.println("get order id"+oid);
        OrderRecord orderRecord=orderService.getRecord(oid);
        List<OrderItem> orderItems=orderRecord.getOrderItems();

        return ResponseEntity.ok(orderItems);
    }
}
