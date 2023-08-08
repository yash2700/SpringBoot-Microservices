package com.ekart.inventory.Repository;

import com.ekart.inventory.Entity.Inventory;
import com.ekart.inventory.Entity.InventoryCheck;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    @Query(value = "SELECT DISTINCT productId, COUNT(productId) AS quantity FROM Inventory WHERE isAvailable = true group by productId")
    List<Tuple> getInventoryCheck();
    @Query(value = "select * from Inventory where is_Available=true and product_Id=?1 limit ?2",nativeQuery = true)
    List<Inventory> getInventoryByProductIdAvailableLimit(long productId, long quantity);

    List<Inventory> findByOrderId(Long orderId);

    void deleteByProductId(Long productId);
}
