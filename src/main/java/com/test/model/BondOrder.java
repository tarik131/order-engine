package com.test.model;

import lombok.Data;

import java.util.List;
import java.util.Objects;


@Data
public class BondOrder {

    public enum Side {BUY, SELL}
    public enum Status {RECEIVED, PARTIALLY_FILLED, FILLED}

    private String id;
    private String ticker;
    private Side side;
    private Double price;
    private Integer quantity;
    private Integer filledQuantity;
    private Integer leftoverQuantity;
    private Long millisSinceEpoch;

    private Status status;

    public BondOrder(){

    }

    public BondOrder(String id, String ticker, Side side, Double price, Integer quantity, Integer filledQuantity, Integer leftoverQuantity, Status status, Long millisSinceEpoch) {
        this.id = id;
        this.ticker = ticker;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.filledQuantity = filledQuantity;
        this.leftoverQuantity = leftoverQuantity;
        this.status = status;
        this.millisSinceEpoch = millisSinceEpoch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BondOrder bondOrder)) return false;
        return Objects.equals(id, bondOrder.id) && Objects.equals(ticker, bondOrder.ticker) && side == bondOrder.side && Objects.equals(price, bondOrder.price) && Objects.equals(quantity, bondOrder.quantity) && Objects.equals(millisSinceEpoch, bondOrder.millisSinceEpoch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticker, side, price, quantity, millisSinceEpoch);
    }
}
