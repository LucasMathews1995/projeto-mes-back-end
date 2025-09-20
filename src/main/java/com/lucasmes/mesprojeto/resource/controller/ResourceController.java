package com.lucasmes.mesprojeto.resource.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasmes.mesprojeto.entity.Resource;
import com.lucasmes.mesprojeto.exceptions.NotAvailableResourceException;
import com.lucasmes.mesprojeto.resource.DTO.ResourceDTOReceiver;
import com.lucasmes.mesprojeto.resource.DTO.ResourceSenderDTO;
import com.lucasmes.mesprojeto.resource.service.ResourceService;

@RestController
@RequestMapping("/resource")
public class ResourceController {


    @Autowired
    private ResourceService service;




    @GetMapping()
    public ResponseEntity< List<ResourceSenderDTO>> listAll(){
        List<ResourceSenderDTO> resources = service.listAll();
        return ResponseEntity.ok(resources);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ResourceSenderDTO> getEachResource(@PathVariable String resourceName ){
        ResourceSenderDTO resource = service.getEachById(resourceName);

        return ResponseEntity.ok(resource);
    }

    @PostMapping("/saveall")
    public ResponseEntity<List<Resource>> saveAllResources(@RequestBody List<ResourceDTOReceiver> resourcesDTO ){
        List<Resource> resources = service.saveAllResources(resourcesDTO);

        return ResponseEntity.ok(resources);

    }

    @PostMapping("/save")
    public ResponseEntity<Resource> saveResource(@RequestBody ResourceDTOReceiver resourceDTO){

Resource resource = service.saveResource(resourceDTO);
        try {
            
        
              return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (NotAvailableResourceException e) {
         
          return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resource);
      
    }


}
}
