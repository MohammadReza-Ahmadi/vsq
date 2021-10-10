package com.vosouq.prototype.user.service;

import com.vosouq.prototype.user.model.Order;
import com.vosouq.prototype.user.model.OrderItem;
import com.vosouq.prototype.user.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class OrderRepositoryTests {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Test
    @Transactional
    public void sample_test() {
        
        Optional<Order> orderOptional = orderRepository.findById(1);
        
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderItem> items = order.getOrderItems();
            items.forEach(item -> System.out.println(item.getPrice()));
        }
        
        System.out.println("");
        
    }
    
}
