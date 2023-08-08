package com.ekart.order.Service;

import com.ekart.order.Dto.OrderEntryDto;
import com.ekart.order.Dto.OrderResponseDto;
import com.ekart.order.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface OrderService {
    OrderResponseDto order(OrderEntryDto orderEntryDto,String token) throws JsonProcessingException;
    List<Order> viewAllOrders(String emailId,String token) throws JsonProcessingException;

    String cancelOrder(String emailId,Long orderId,String token);

}
