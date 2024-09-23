package com.test.repository;

import com.test.model.Trade;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TradeRepository {

    private final List<Trade> trades;

    public TradeRepository (){
        this.trades = new ArrayList<>();
    }

    public void save(Trade trade){
        this.trades.add(trade);
    }

    public List<Trade> findAll(){
        return trades;
    }
}
