package com.ekart.order.ServiceImpl;

import com.ekart.order.Dto.InventoryCheckDto;
import com.ekart.order.Dto.OrderEntryDto;
import com.ekart.order.Dto.OrderResponseDto;
import com.ekart.order.Exceptions.OrdersNotFoundException;
import com.ekart.order.Exceptions.ProductNotAvailableException;
import com.ekart.order.Exceptions.TimeExceededException;
import com.ekart.order.Helper.RestTemplateHelper;
import com.ekart.order.Repository.CancelledOrderRepository;
import com.ekart.order.Repository.OrderRepository;
import com.ekart.order.Service.OrderService;
import com.ekart.order.entity.CancelledOrder;
import com.ekart.order.entity.Order;
import com.ekart.order.entity.Product;
import com.ekart.order.enums.Errors;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    RestTemplateHelper restTemplateHelper;

    @Autowired
    CancelledOrderRepository cancelledOrderRepository;

    @Override
    public OrderResponseDto order(OrderEntryDto orderEntryDto,String token) throws JsonProcessingException {
        List<Long> idsAvailable = restTemplateHelper.getAvailableProducts(orderEntryDto.getProductIds(),token);
        List<Long> productsRequested = orderEntryDto.getProductIds();
        HashSet<Long> set1 = new HashSet<>();
        set1.addAll(productsRequested);
        HashSet<Long> set2 = new HashSet<>();
        set2.addAll(idsAvailable);
        System.out.println(set1 + " " + set2);
        if (set1.size() > set2.size()) {
            throw new ProductNotAvailableException(Errors.PRODUCT_NOT_AVAILABLE);
        }
//
        List<Product> products = restTemplateHelper.getProductByProductId(idsAvailable,token);
        Order order = Order.builder()
                .emailId(orderEntryDto.getEmailId())
                .isCancelled(false)
                .productList(productsRequested.stream().map(i->i.toString()).toList())
                .build();

        HashMap<Long, Integer> map = new HashMap<>();
        for (Long i : productsRequested) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        BigDecimal price = BigDecimal.valueOf(0);
        for (Product i : products) {
            Long key = i.getId();
            if (map.containsKey(key)) {
                price=price.add(i.getPrice().multiply(BigDecimal.valueOf(map.get(key))));
                System.out.println(map.get(key)+"id");
                System.out.println(price+"price");
            }
        }
        for (Product i : products) {
            if (map.containsKey(i.getId())) {
                i.setQuantity(map.get(i.getId()));
            }
        }
        System.out.println(price+"final price");
        order.setPrice(price);
        order.setLocalDateTime(LocalDateTime.now().plusDays(15));
        orderRepository.save(order);
        logger.info("order with id :" + order.getId() + " is saved into db!");
        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .id(order.getId())
                .products(products)
                .localDateTime(order.getLocalDateTime())
                .build();
        restTemplateHelper.order(productsRequested, orderEntryDto.getEmailId(), order.getId(),token);
        return orderResponseDto;
    }

    @Override
    public List<Order> viewAllOrders(String emailId,String token) throws JsonProcessingException {
        if (!orderRepository.existsByEmailId(emailId)) {
            throw new OrdersNotFoundException(Errors.ORDERS_NOT_FOUND);
        }
        List<Order> orders = orderRepository.findAllByEmailId(emailId);

        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for (Order i : orders) {
            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .id(i.getId())
                    .localDateTime(i.getLocalDateTime())
                    .build();
           List<String> ids=i.getProductList();
            HashMap<Long,Integer> map=getProductsWithCount(ids.stream().map(l->Integer.valueOf(l)).toList());
            List<Product> products = restTemplateHelper.getProductByProductId(i.getProductList()
                    .stream().map(k->Long.valueOf(k)).toList(),token);
            for (Product j : products) {
                j.setQuantity(map.get(j.getId()));
            }
            orderResponseDto.setProducts(products);
            orderResponseDtos.add(orderResponseDto);
        }
    return orders;
    }

    @Override
    public String cancelOrder(String emailId, Long orderId,String token) {
        Order order=orderRepository.findById(orderId).get();
        if(ChronoUnit.DAYS.between(LocalDateTime.now(),order.getLocalDateTime())>=0 && !order.isCancelled()) {
            List<Integer> ids = order.getProductList().stream()
                    .map(Integer::valueOf).toList();
            restTemplateHelper.cancelOrder(orderId,ids,token);
            CancelledOrder cancelledOrder= CancelledOrder.builder()
                    .orderId(orderId)
                    .productIds(order.getProductList())
                    .build();
            cancelledOrderRepository.save(cancelledOrder);
            order.setCancelled(true);
            order.setLocalDateTime(LocalDateTime.now());
            orderRepository.save(order);
            return "Success";
        }
//        List<Integer> ids = order.getProductList().stream()
//                .map(Integer::valueOf).toList();
//        restTemplateHelper.cancelOrder(orderId,ids);
//        orderRepository.delete(order);
//        return "Success";
        else throw  new TimeExceededException(Errors.TIME_EXCEEDED);
    }




    public HashMap<Long, Integer> getProductsWithCount(List<Integer> products) {
        HashMap<Long, Integer> map = new HashMap<>();
        for (Integer i: products) {
            map.put(Long.valueOf(i),map.getOrDefault(Long.valueOf(i),0)+1);
        }
        System.out.println(map);
        return map;
    }
}
