package com.test.controller;

import com.test.model.BondOrder;
import com.test.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<Void> createOrder(@RequestBody BondOrder bondOrder) {

        boolean result = orderService.submitOrder(bondOrder);;
        if (result)
            return new ResponseEntity<Void>(HttpStatus.OK);
        else
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
}
