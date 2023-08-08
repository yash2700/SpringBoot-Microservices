package com.ekart.order.Repository;

import com.ekart.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByEmailId(String emailId);

    boolean existsByEmailId(String emailId);
    @Query(value = "select product_list from orders where id=?1",nativeQuery = true)
    List<Integer> getProductListByOrderId(long orderId);
}
