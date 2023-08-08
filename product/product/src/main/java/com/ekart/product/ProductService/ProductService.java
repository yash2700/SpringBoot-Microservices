package com.ekart.product.ProductService;

import com.ekart.product.Dtos.InventoryEntryDto;
import com.ekart.product.Dtos.ProductEntryDto;
import com.ekart.product.Dtos.ProductResponseDto;
import com.ekart.product.Entity.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
        ProductResponseDto createProduct(ProductEntryDto productEntryDto,String token) throws IOException;
        List<ProductResponseDto> getAllProducts();
        ProductResponseDto deleteProductById(long id,String token);
        BigDecimal getPriceOfProduct(Long productId);
        ProductResponseDto getProductById(long productId);
        List<ProductResponseDto> getAllProductsByList(List<Long> ids);

        String updateProductQuantity(InventoryEntryDto inventoryEntryDto,String type);
}
