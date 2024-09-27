package com.test.model;

import java.util.List;

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

    private Status status;

    public BondOrder(){

    }

    public BondOrder(String id, String ticker, Side side, Double price, Integer quantity, Integer filledQuantity, Integer leftoverQuantity, Status status) {
        this.id = id;
        this.ticker = ticker;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.filledQuantity = filledQuantity;
        this.leftoverQuantity = leftoverQuantity;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getFilledQuantity() {
        return filledQuantity;
    }

    public void setFilledQuantity(Integer filledQuantity) {
        this.filledQuantity = filledQuantity;
    }

    public Integer getLeftoverQuantity() {
        return leftoverQuantity;
    }

    public void setLeftoverQuantity(Integer leftoverQuantity) {
        this.leftoverQuantity = leftoverQuantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
