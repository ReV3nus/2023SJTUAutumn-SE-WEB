package com.example.mybookstore_backend.service;

import com.example.mybookstore_backend.entity.Book;
import com.example.mybookstore_backend.entity.MG_Book;
import com.example.mybookstore_backend.entity.OrderItem;
import com.example.mybookstore_backend.entity.OrderRecord;

import java.util.List;

public interface OrderService {
    OrderRecord AddRecordToOrder(OrderRecord record);
    List<OrderRecord>getOrder(String user);
    OrderRecord getRecord(Integer rid);
    List<OrderRecord>getAllOrders();
    OrderRecord CreateRecordOfBooks(List<MG_Book>optBooks, String username, String formattedDateTime);
}
