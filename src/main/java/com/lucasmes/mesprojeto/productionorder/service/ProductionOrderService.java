package com.lucasmes.mesprojeto.productionorder.service;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lucasmes.mesprojeto.entity.ProductionOrder;
import com.lucasmes.mesprojeto.exceptions.NotFoundProductionOrderException;
import com.lucasmes.mesprojeto.repository.ProductionOrderRepository;
import com.lucasmes.mesprojeto.productionorder.DTO.*;

@Service
public class ProductionOrderService {

@Autowired
private ProductionOrderRepository repository;




    public ProductionOrder saveProductionOrder(ProductionOrderDTO dto){
        ProductionOrder productionOrder = new ProductionOrder(dto.productionOrder(),dto.weight(),dto.maxLimit());
            return repository.save(productionOrder);

        
    }

    
    public List<ProductionOrder> saveAllProductionOrder(List<ProductionOrderDTO> dto){
        List<ProductionOrder> productionOrders = dto.stream().map(it-> {
            ProductionOrder productionOrder = new ProductionOrder();
            productionOrder.setProductionOrder(it.productionOrder());
            productionOrder.setWeight(it.weight());
          return productionOrder;
            
        }).collect(Collectors.toList());

  if(productionOrders.isEmpty()){
    throw new NotFoundProductionOrderException("There's no such production Order");
  }
  return repository.saveAll(productionOrders);

    }


    public List<ProductionOrder> listAll() {
      List<ProductionOrder> poList = repository.findAll();
      return poList;

    }
   
    public ProductionOrder findById(String id){
      ProductionOrder po = repository.findById(id)
      .orElseThrow(()-> new NotFoundProductionOrderException("there's no OP with this name"));
    return po;
    }
  

}
