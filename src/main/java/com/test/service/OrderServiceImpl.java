package com.test.service;

import com.test.model.BondOrder;
import com.test.model.Trade;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    //Store orders in java data structure(s)
    public boolean submitOrder(BondOrder order){
        return true;
    }

    //Implement process order
    private void processOrder(BondOrder order){

        BondOrder buyOrder = null;
        BondOrder sellOrder = null;
        int quantity = 0;

        //matching logic

        Trade trade = createTrade(buyOrder,sellOrder,quantity);
        System.out.println("New Trade happened: "+trade.toString());

    }


    private Trade createTrade(BondOrder buyOrder, BondOrder sellOrder,int quantity){

        Trade trade = new Trade();

        trade.setId(buyOrder.getId()+"-"+sellOrder.getId());
        trade.setTicker(sellOrder.getTicker());
        trade.setPrice(sellOrder.getPrice());
        trade.setQuantity(quantity);

        trade.setBuyerOrderId(buyOrder.getId());
        return trade;
    }

}
