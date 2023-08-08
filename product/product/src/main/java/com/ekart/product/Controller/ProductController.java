package com.ekart.product.Controller;

import com.ekart.product.Dtos.InventoryEntryDto;
import com.ekart.product.Dtos.ProductEntryDto;
import com.ekart.product.Dtos.ProductResponseDto;
import com.ekart.product.Entity.Product;
import com.ekart.product.ProductServiceImpl.ProductServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
@Validated
public class ProductController {

    private static final Logger logger= LoggerFactory.getLogger(ProductController.class);
    @Autowired
    ProductServiceImpl productService;

    @PostMapping(value = "/create",consumes = "application/json",produces = "application/json")
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductEntryDto productEntryDto,@RequestHeader("AUTHORIZATION") String token) throws IOException {
        return new ResponseEntity<>(productService.createProduct(productEntryDto,token), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAllProducts",produces = "application/json")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }
    @CircuitBreaker(name = "productService",fallbackMethod = "deleteByProductIdFallback")
    @DeleteMapping(value = "/deleteById/{id}")
    public ResponseEntity<ProductResponseDto> deleteProductById(@PathVariable @Min(value = 1) Long id,@RequestHeader("AUTHORIZATION") String token){
        return  ResponseEntity.ok(productService.deleteProductById(id,token));
    }
    @GetMapping(value = "getPrice/{productId}")
    public ResponseEntity<BigDecimal> getPriceOfProduct(@PathVariable Long productId){
        return new ResponseEntity<>(productService.getPriceOfProduct(productId),HttpStatus.OK);
    }
    @GetMapping(value = "getProductById/{productId}",produces = "application/json")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable long productId){
        return new ResponseEntity<>(productService.getProductById(productId),HttpStatus.OK);
    }
    @GetMapping(value = "/getProductsByList",consumes = "application/json",produces = "application/json")
    public List<ProductResponseDto> getProductsByList(@RequestParam List<Long> ids){
            return productService.getAllProductsByList(ids);
    }
    @PutMapping(value = "/updateProductQuantity",consumes = "application/json")
    public ResponseEntity<String> updateProductQuantity(@RequestBody InventoryEntryDto inventoryEntryDto,@RequestParam String type){
        return new ResponseEntity<>(productService.updateProductQuantity(inventoryEntryDto,type),HttpStatus.OK);
    }

    public ResponseEntity<String> deleteByProductIdFallback(Long id,String token,Throwable throwable){
        logger.error("--------------delete by product id fall back--------------------");
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
