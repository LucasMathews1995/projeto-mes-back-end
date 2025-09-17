package com.lucasmes.mesprojeto.service;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lucasmes.mesprojeto.DTO.ProductionOrderDTO;
import com.lucasmes.mesprojeto.entity.ProductionOrder;
import com.lucasmes.mesprojeto.exceptions.NotFoundProductionOrderException;
import com.lucasmes.mesprojeto.repository.ProductionOrderRepository;

@Service
public class ProductionOrderService {

@Autowired
private ProductionOrderRepository repository;




    public ProductionOrder saveProductionOrder(ProductionOrderDTO dto){
        ProductionOrder productionOrder = new ProductionOrder(dto.numberOp(),dto.weight());
            return repository.save(productionOrder);

        
    }
    public List<ProductionOrder> saveAllProductionOrder(List<ProductionOrderDTO> dto){
        List<ProductionOrder> productionOrders = dto.stream().map(it-> {
            ProductionOrder productionOrder = new ProductionOrder();
            productionOrder.setNumberOp(it.numberOp());
            productionOrder.setWeight(it.weight());
          return productionOrder;
            
        }).collect(Collectors.toList());

  if(productionOrders.isEmpty()){
    throw new NotFoundProductionOrderException("There's no such production Order");
  }
  return repository.saveAll(productionOrders);

    }
   

  

}
