package com.ekart.order.Repository;

import com.ekart.order.entity.CancelledOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelledOrderRepository extends JpaRepository<CancelledOrder,Long> {
}
