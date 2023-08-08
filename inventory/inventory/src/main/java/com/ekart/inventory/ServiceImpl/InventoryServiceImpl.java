package com.ekart.inventory.ServiceImpl;

import com.ekart.inventory.Dtos.InventoryEntryDto;
import com.ekart.inventory.Entity.Inventory;
import com.ekart.inventory.Entity.InventoryCheck;
import com.ekart.inventory.Repository.InventoryRepository;
import com.ekart.inventory.RestTemplateHelper.RestTemplateHelper;
import com.ekart.inventory.Service.InventoryService;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@Transactional
public class InventoryServiceImpl implements InventoryService {
    private static final Logger logger= LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    RestTemplateHelper restTemplateHelper;
    @Override
    public String addToInventory(InventoryEntryDto inventoryEntryDto) {
        for(long i=0;i<inventoryEntryDto.getQuantity();i++){
            Inventory inventory=InventoryEntryDto.mapToInventory(inventoryEntryDto);
            inventoryRepository.save(inventory);
            logger.info("product with id :"+inventory.getProductId()+" is saved to db !");
        }
        return "success";
    }


public List<InventoryCheck> getAvailableIds(List<Long> productInfo){
    HashSet<Long> set=new HashSet<>();
    set.addAll(productInfo);
    List<InventoryCheck> inventoryCheckList=new ArrayList<>();
    HashMap<Long,Integer> map=new HashMap<>();
    for (Long i:productInfo){
        map.put(i, map.getOrDefault(i,0)+1);
    }
    for (Long i: map.keySet())
        inventoryCheckList.add(new InventoryCheck(i,map.get(i)));

    List<Tuple> tuples=inventoryRepository.getInventoryCheck();
    List<InventoryCheck> inventoryChecks= tuples.stream().map(i->new InventoryCheck((Long) i.get(0), (Long) i.get(1))).toList();
    List<InventoryCheck> inventoryChecks1=new ArrayList<>();
    for (InventoryCheck i:inventoryChecks){
        for (InventoryCheck j:inventoryCheckList){

            if(i.getProductId()==j.getProductId() && i.getQuantity()>=j.getQuantity()) {
                inventoryChecks1.add(j);
                System.out.println(i+" "+j);
            }
        }
    }
    return inventoryChecks1;

}

    @Override
    public List<Long> getAvailableProducts(List<Long> productInfo) {
        HashSet<Long> set=new HashSet<>();
        set.addAll(productInfo);
        List<InventoryCheck> inventoryCheckList=getAvailableIds(productInfo);
        List<Long> ids=inventoryCheckList.stream().map(i->i.getProductId()).toList();
        set.retainAll(ids);
        return set.stream().toList();
    }


    @Override
    public String order(List<Long> productIds, String emailId,long orderId,String token) {
       List<InventoryCheck> checks=getAvailableIds(productIds);
       for (InventoryCheck i:checks){
           System.out.println(i.toString());
           List<Inventory> inventories=inventoryRepository.getInventoryByProductIdAvailableLimit(i.getProductId(),i.getQuantity());
           for (Inventory j:inventories){
               j.setAvailable(false);
               j.setEmailId(emailId);
               j.setOrderId(orderId);
           }
           InventoryEntryDto inventoryEntryDto=InventoryEntryDto.builder()
                   .productId(i.getProductId())
                   .quantity(i.getQuantity())
                   .build();
           System.out.println(inventoryEntryDto);
           System.out.println(restTemplateHelper.updateProductQuantity(inventoryEntryDto,"sub",token));
           inventoryRepository.saveAll(inventories);

       }
        return "Success";
    }

    @Override
    public String cancelOrder(List<Integer> ids, Long orderId,String token) {
        List<Inventory> inventories=inventoryRepository.findByOrderId(orderId);
        HashMap<Integer,Integer> map=new HashMap<>();

        for(Integer i:ids){
            map.put(i,map.getOrDefault(i,0)+1);
        }
        for (Integer i: map.keySet()){
            int count=map.get(i);
            for (Inventory j:inventories){
                if(j.getProductId()==i && j.getOrderId()==orderId && count>0){
                    j.setAvailable(true);
                    j.setEmailId(null);
                    j.setOrderId(0);
                }
            }
            InventoryEntryDto inventoryEntryDto=new InventoryEntryDto(Long.valueOf(map.get(i)),Long.valueOf(i));
            String response= restTemplateHelper.updateProductQuantity(inventoryEntryDto,"add",token);
            System.out.println("success");
        }

        inventoryRepository.saveAll(inventories);
        return "Success";
    }

    @Override
    public String deleteFromInventory(Long productId) {
        inventoryRepository.deleteByProductId(productId);
        logger.info("Inventory deleted with product Id :"+productId);
        return "Success";
    }
}
