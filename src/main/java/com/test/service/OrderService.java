package com.test.service;

import com.test.model.BondOrder;

public interface OrderService {
    boolean submitOrder(BondOrder bondOrder);
}
