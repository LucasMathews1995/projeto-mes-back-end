package com.lucasmes.mesprojeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasmes.mesprojeto.DTO.ResourceDTOReceiver;
import com.lucasmes.mesprojeto.entity.Resource;
import com.lucasmes.mesprojeto.exceptions.NotFoundResource;
import com.lucasmes.mesprojeto.repository.ResourceRepository;

@Service
public class ResourceService {

@Autowired
private ResourceRepository repository;



public Resource getEachById(String resourceName ){
Resource r = repository.findById(resourceName).orElseThrow(()-> new NotFoundResource("not found resource by this id"));
return r;
}

public  List<Resource> listAll(){

     List<Resource>  r = repository.findAll();
    return r;
}


public Resource savResource(ResourceDTOReceiver resource){
Resource r = new Resource(resource.nameResource(),resource.nameArea(),resource.currentCapacity());

return r;
}

public List<Resource> saveAllResources(List<ResourceDTOReceiver> receivers){
List<Resource> resources  = receivers.stream().map(it-> {
Resource r = new Resource(it.nameResource(),it.nameArea(),it.currentCapacity());


return r;
}).collect(Collectors.toList());
return resources;
}



}
