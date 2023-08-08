package com.ekart.product.ProductServiceImpl;

import com.ekart.product.Dtos.InventoryEntryDto;
import com.ekart.product.Dtos.ProductEntryDto;
import com.ekart.product.Dtos.ProductResponseDto;
import com.ekart.product.Entity.Product;
import com.ekart.product.Exceptions.ProductNotFoundException;
import com.ekart.product.ProductService.ProductService;
import com.ekart.product.Repository.ProductRepository;
import com.ekart.product.enums.Errors;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ekart.product.helper.RestTemplateHelper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    Environment environment;

    @Autowired
    RestTemplateHelper restTemplateHelper;
    @Autowired
    private DiscoveryClient client;

    @Autowired
    RestTemplate restTemplate;
    private String inventoryUrl="";

    private static final Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductResponseDto createProduct(ProductEntryDto productEntryDto,String token) throws IOException {
        Product product=ProductEntryDto.mapToProductEntity(productEntryDto);
        productRepository.save(product);
        List<ServiceInstance> list=client.getInstances("InventoryMS");
        System.out.println(list.toString());
        logger.info("Product with id:"+product.getId()+" saved");
        InventoryEntryDto inventoryEntryDto= InventoryEntryDto.builder()
                .productId(product.getId())
                .quantity(productEntryDto.getQuantity())
                .build();
        String inventoryResponse=restTemplateHelper.addToInventory(inventoryEntryDto,token);
        logger.info(inventoryResponse);
        ProductResponseDto productResponseDto=ProductResponseDto.mapToProductResponse(product);
        return productResponseDto;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> productList=productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList=new ArrayList<>();
        for (Product product:productList){
            productResponseDtoList.add(ProductResponseDto.mapToProductResponse(product));
        }
        return productResponseDtoList;
    }

    @Override
    public ProductResponseDto deleteProductById(long id,String token) {
        Product product=productRepository.findById(id).orElseThrow(
                ()->new ProductNotFoundException(Errors.PRODUCT_NOT_FOUND));
        ProductResponseDto productResponseDto=ProductResponseDto.mapToProductResponse(product);
        productRepository.deleteById(id);
        restTemplateHelper.deleteFromInventory(id,token);
        logger.info("Product with id :"+id+" is deleted successfully from DB!");
        return productResponseDto;
    }

    @Override
    public BigDecimal getPriceOfProduct(Long productId) {
        if(!productRepository.existsById(productId)){
            throw new ProductNotFoundException(Errors.PRODUCT_NOT_FOUND);
        }
        Product product=productRepository.findById(productId).get();
        return product.getPrice();
    }

    @Override
    public ProductResponseDto getProductById(long productId) {
        if(!productRepository.existsById(productId)){
            throw new ProductNotFoundException(Errors.PRODUCT_NOT_FOUND);
        }
        ProductResponseDto productResponseDto=ProductResponseDto.mapToProductResponse(productRepository.findById(productId).get());
        return productResponseDto;
    }

    @Override
    public List<ProductResponseDto> getAllProductsByList(List<Long> ids) {
        List<Product> products=productRepository.findByIdIn(ids);
        List<ProductResponseDto> productResponseDtoList=products
                .stream().map(ProductResponseDto::mapToProductResponse).toList();
        return productResponseDtoList;
    }

    @Override
    public String updateProductQuantity(InventoryEntryDto inventoryEntryDto,String type) {
        System.out.println(inventoryEntryDto);
        Product product=productRepository.findById(inventoryEntryDto.getProductId()).get();
        if(type.equals("sub")) {
            product.setQuantity(product.getQuantity() - inventoryEntryDto.getQuantity());
        }
        else{
            product.setQuantity(product.getQuantity()+ inventoryEntryDto.getQuantity());
        }
        productRepository.save(product);
        return "Success";
    }
}
