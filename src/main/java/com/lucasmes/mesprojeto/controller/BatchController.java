package com.lucasmes.mesprojeto.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lucasmes.mesprojeto.DTO.BatchDTOReceiver;

import com.lucasmes.mesprojeto.entity.Batch;
import com.lucasmes.mesprojeto.service.BatchService;



@RestController
@RequestMapping("/batch")
public class BatchController {

@Autowired
private BatchService service;







}
