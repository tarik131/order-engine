package com.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trade {
    private String id;
    private String ticker;
    private String trader;
    private Integer quantity;
    private String buyer;
    private String seller;
    private Double price;
    private String bidOrderId;
    private String offerOrderId;
}
