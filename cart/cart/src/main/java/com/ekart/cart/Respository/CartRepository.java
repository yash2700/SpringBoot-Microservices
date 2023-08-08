package com.ekart.cart.Respository;
import com.ekart.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    boolean existsByEmailId(String emailId);

    Cart findByEmailId(String emailId);
}
