package com.ekart.product.Repository;

import com.ekart.product.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByIdIn(List<Long> ids);
}
