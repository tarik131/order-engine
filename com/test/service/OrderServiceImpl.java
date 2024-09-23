package com.test.service;

import com.test.model.BondOrder;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    public boolean submitOrder(BondOrder order){
        return true;
    }

}
