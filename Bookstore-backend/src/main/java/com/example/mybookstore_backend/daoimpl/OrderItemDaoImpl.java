package com.example.mybookstore_backend.daoimpl;

import com.example.mybookstore_backend.dao.OrderItemDao;
import com.example.mybookstore_backend.entity.OrderItem;
import com.example.mybookstore_backend.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    @Transactional
    public OrderItem AddItem(OrderItem item){
        //int result=10/0;
         return orderItemRepository.save(item);
    }
}
