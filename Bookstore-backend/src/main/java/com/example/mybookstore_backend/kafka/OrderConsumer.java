//package com.example.mybookstore_backend.kafka;
//
//import com.example.mybookstore_backend.entity.*;
//import com.example.mybookstore_backend.service.BookService;
//import com.example.mybookstore_backend.service.CartService;
//import com.example.mybookstore_backend.service.OrderItemService;
//import com.example.mybookstore_backend.service.OrderService;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class OrderConsumer {
//    @Autowired
//    BookService bookService;
//    @Autowired
//    OrderService orderService;
//    @Autowired
//    OrderItemService orderItemService;
//    @Autowired
//    CartService cartService;
//    @Autowired
//    ObjectMapper objectMapper;
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate; // 用于向前端发送消息
//
//    @KafkaListener(topics = "item-order", groupId = "my-consumer-group")
//    public void handle_item_order(String jsonMessage) {
//        Map<String,String> message = new HashMap<>();
//        try{
//            message = objectMapper.readValue(jsonMessage, new TypeReference<Map<String, String>>() {});
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
//
//        System.out.println("got message:"+message);
//        Integer BookId=Integer.parseInt(message.get("BookId"));
//        String username=message.get("username");
//        String formattedDateTime=message.get("time");
//        try {
//            MG_Book optBook=bookService.findBookById(BookId);
//
//            optBook.Remove(1);
//            bookService.SaveBook(optBook);
//
//            OrderRecord orderRecord=new OrderRecord(username,formattedDateTime);
//            OrderItem orderItem=new OrderItem(optBook.getBookId(),optBook.getName(),optBook.getAuthor(),optBook.getType(),optBook.getPrice(),1,username);
//            System.out.print(orderRecord);
//            System.out.println();
//            orderItem.setOrderRecord(orderRecord);
//            orderRecord.getOrderItems().add(orderItem);
//            System.out.print(orderItem.__TestOrderRecord());
//            System.out.println();
//            orderService.AddRecordToOrder(orderRecord);
//            orderItemService.AddItemToOrder(orderItem);
//            messagingTemplate.convertAndSendToUser(username,"/queue/order-result", "Succeed!");
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//            messagingTemplate.convertAndSendToUser(username,"/queue/order-result", "Failed!");
//        }
//    }
//    @KafkaListener(topics = "cart-order", groupId = "my-consumer-group")
//    public void handle_cart_order(String jsonMessage) {
//        Map<String,String> message = new HashMap<>();
//        try{
//            message = objectMapper.readValue(jsonMessage, new TypeReference<Map<String, String>>() {});
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
//            System.out.println("got message:"+message);
//            String username=message.get("username");
//            String formattedDateTime=message.get("time");
//
//        try{
//            List<CartRecord> UserCart=cartService.getCart(username);
//            List<MG_Book> OptBooks = new ArrayList<>();
//            for (CartRecord cartRecord : UserCart) {
//                MG_Book optBook = bookService.findBookById(cartRecord.getBookid());
//                optBook.Remove(1);
//                bookService.SaveBook(optBook);
//                OptBooks.add(optBook);
//            }
//            cartService.ClearCart(username);
//            orderService.CreateRecordOfBooks(OptBooks,username,formattedDateTime);
//            messagingTemplate.convertAndSendToUser(username,"/queue/order-result", "Succeed!");
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//            messagingTemplate.convertAndSendToUser(username,"/queue/order-result", "Succeed!");
//        }
//    }
//}