package com.test.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BondOrder {

    public enum Side {bid, offer}
    public enum Status {received, open, completed, cancelled}

    private String id;
    private String ticker;
    private String trader;
    private Side side; //an order can be a bid or offer
    private Double limit;
    private Integer quantity;
    private Integer filledQuantity;
    private Integer leftoverQuantity;
    private Status status;
    private List<String> tradeIds;
}
