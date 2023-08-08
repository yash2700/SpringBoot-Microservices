package com.ekart.cart.ServiceImpl;

import com.ekart.cart.Dtos.CartEntryDto;
import com.ekart.cart.Dtos.CartResponseDto;
import com.ekart.cart.Respository.CartRepository;
import com.ekart.cart.Service.CartService;
import com.ekart.cart.entity.Cart;
import com.ekart.cart.entity.Product;
import com.ekart.cart.helper.RestTemplateHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class CartServiceImpl implements CartService {
    private static final Logger logger= LoggerFactory.getLogger(CartServiceImpl.class);
    @Autowired
    RestTemplateHelper restTemplateHelper;
    @Autowired
    CartRepository cartRepository;
    @Override
    public String addToCart(CartEntryDto cartEntryDto) {
        if(!cartRepository.existsByEmailId(cartEntryDto.getEmailId())){
          Cart cart=Cart.builder()
                  .emailId(cartEntryDto.getEmailId())
                  .build();
          List<Long> products=new ArrayList<>();
             products.add(cartEntryDto.getProductId());
            cart.setProductList(products);
            cartRepository.save(cart);
            logger.info("Product with id :"+cartEntryDto.getProductId()+" has benn added to cart");
            return "Success";
        }
        Cart cart=cartRepository.findByEmailId(cartEntryDto.getEmailId());
        List<Long> products=cart.getProductList();
        products.add(cartEntryDto.getProductId());
        cartRepository.save(cart);
        System.out.println(cart.toString());
        return "Successfully Added to Cart!";
    }

    @Override
    public CartResponseDto getCart(String emailId,String token) throws JsonProcessingException {
            Cart cart=cartRepository.findByEmailId(emailId);
        System.out.println(cart.toString());
            CartResponseDto cartResponseDto= CartResponseDto.builder()
                    .emailId(emailId)
                    .build();
        System.out.println(cart.getProductList());
            List<Product> productsList=restTemplateHelper.getProductByProductId(cart.getProductList(), token);
        cartResponseDto.setProductList(productsList);
            List<Product> productList=cartResponseDto.getProductList();
            productList.stream().forEach(i->i.setQuantity(0));
            Double total=0.0;
            HashMap<Long,Integer> map=new HashMap<>();
            for (Long i:cart.getProductList()){
                map.put(i, map.getOrDefault(i,0)+1);
            }
        System.out.println(map);
            for(Product i:productList){
                i.setQuantity(map.get(i.getId()));
                total+=map.get(i.getId())*i.getPrice().doubleValue();
            }

//            for (Long i:products){
//                for (Product j:productList){
//                    if(j.getId()==i){
//                        j.setQuantity(j.getQuantity()+1);
//                    }
//                }
//            }
        cartResponseDto.setTotalAmount(BigDecimal.valueOf(total));
            cartResponseDto.setProductList(productList);
            return cartResponseDto;
    }
}
