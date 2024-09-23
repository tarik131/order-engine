package com.test.service;

import com.test.model.BondOrder;
import com.test.model.Trade;
import com.test.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    TradeRepository tradeRepository;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository){
        this.tradeRepository = tradeRepository;
    }
 
    @Override
    public Trade createTrade(BondOrder bid, BondOrder offer, Integer quantity) {
    
        Trade trade = new Trade();
        trade.setId(bid.getId()+"-"+offer.getId());
        System.out.println("Creating trade --"+trade.getId());

        //bid details
        trade.setBidOrderId(bid.getId());
        trade.setBuyer(bid.getTrader());
        trade.setQuantity(quantity);
        trade.setTicker(bid.getTicker());

        //offer details
        trade.setOfferOrderId(offer.getId());
        trade.setSeller(offer.getTrader());
        trade.setPrice(offer.getLimit());
        
        tradeRepository.save(trade);
        System.out.println("Created trade successfully:"+trade);
        return trade;
    }

    @Override
    public List<Trade> getTrades(String ticker) {
        return tradeRepository.findAll();
    }
}
