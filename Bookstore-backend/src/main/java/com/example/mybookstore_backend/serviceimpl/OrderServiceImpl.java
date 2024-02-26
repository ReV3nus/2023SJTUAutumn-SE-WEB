package com.example.mybookstore_backend.serviceimpl;

import com.example.mybookstore_backend.dao.OrderDao;
import com.example.mybookstore_backend.dao.OrderItemDao;
import com.example.mybookstore_backend.entity.*;
import com.example.mybookstore_backend.service.OrderService;
import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Override
    public OrderRecord AddRecordToOrder(OrderRecord record){
        return orderDao.AddRecord(record);
    }
    @Override
    public List<OrderRecord> getOrder(String user){
        return orderDao.getOrder(user);
    }
    @Override
    public OrderRecord getRecord(Integer rid)
    {
        return orderDao.getRecord(rid);
    }
    @Override
    public List<OrderRecord> getAllOrders(){return orderDao.getAllOrders();}
    @Override
    @Transactional
    public OrderRecord CreateRecordOfBooks(List<MG_Book> optBooks, String username, String formattedDateTime)
    {
        OrderRecord orderRecord=new OrderRecord(username,formattedDateTime);
        for (MG_Book book : optBooks) {
            OrderItem orderItem=new OrderItem(book.getBookId(),book.getName(),book.getAuthor(),book.getType(),book.getPrice(),1,username);
            orderItem.setOrderRecord(orderRecord);
            orderRecord.getOrderItems().add(orderItem);
            orderDao.AddRecord(orderRecord);
            orderItemDao.AddItem(orderItem);
        }
        return orderRecord;
    }
}
