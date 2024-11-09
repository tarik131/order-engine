package com.test.service;

import com.test.model.BondOrder;
import com.test.model.Trade;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final Comparator<BondOrder> orderComparator = (o1, o2) -> {
        int priceCompare = Double.compare(o1.getPrice(), o2.getPrice());

        if (priceCompare == 0) {
            // If prices are equal, compare time
            int timeCompare = Long.compare(o1.getMillisSinceEpoch(), o2.getMillisSinceEpoch());
            if (timeCompare == 0) {
                return o1.getId().compareTo(o2.getId());
            }
            return timeCompare;
        }
        return priceCompare;
    };

    //data structure to store orders on the basis of side and price
    private final Map<String, TreeSet<BondOrder>> buySideMap = new HashMap<>();//pass comparator
    private final Map<String, TreeSet<BondOrder>> sellSideMap = new HashMap<>(); //pass comparator

    //Store orders in java data structure(s)
    public boolean submitOrder(BondOrder order) {
        String instrument = order.getTicker();
        if (BondOrder.Side.BUY.equals(order.getSide())) {
            if (buySideMap.containsKey(instrument)) {
                buySideMap.get(instrument).add(order);
            } else {
                TreeSet<BondOrder> orders = new TreeSet<>(orderComparator);
                orders.add(order);
                buySideMap.put(instrument, orders);
            }
        } else {
            if (sellSideMap.containsKey(instrument)) {
                sellSideMap.get(instrument).add(order);
            } else {
                TreeSet<BondOrder> orders = new TreeSet<>(orderComparator);
                orders.add(order);
                sellSideMap.put(instrument, orders);
            }
        }
        processOrder(order);
        return true;
    }

    public TreeSet<BondOrder> getBuyOrdersbyTicker(String ticker) {
        return buySideMap.get(ticker);
    }

    public TreeSet<BondOrder> getSellOrdersbyTicker(String ticker) {
        return sellSideMap.get(ticker);
    }

    //Implement process order
    private void processOrder(BondOrder order) {
        String ticker = order.getTicker();
        int remainingQuantity = order.getQuantity();

        if (BondOrder.Side.BUY.equals(order.getSide())) {
            // Find matching sell orders with price <= buy price
            TreeSet<BondOrder> sellOrders = sellSideMap.get(ticker);
            BondOrder matchingSellOrder = Objects.nonNull(sellSideMap.get(ticker)) ? sellOrders.first() : null;

            while (matchingSellOrder != null && order.getLeftoverQuantity() > 0) {
                // Check price and quantity match
                if (matchingSellOrder.getPrice() <= order.getPrice()) {

                    int quantityToFill = Math.min(order.getLeftoverQuantity(), matchingSellOrder.getLeftoverQuantity());

                    order.setFilledQuantity(order.getFilledQuantity() + quantityToFill);
                    order.setLeftoverQuantity(order.getLeftoverQuantity() - quantityToFill);

                    matchingSellOrder.setFilledQuantity(matchingSellOrder.getFilledQuantity() + quantityToFill);
                    matchingSellOrder.setLeftoverQuantity(matchingSellOrder.getLeftoverQuantity() - quantityToFill);


                    // Update quantities
                    if (order.getLeftoverQuantity() == 0) {
                        order.setStatus(BondOrder.Status.FILLED);
                        buySideMap.get(ticker).remove(order);
                        break; //order has been filled completely
                    }
                    if (matchingSellOrder.getLeftoverQuantity() == 0) {
                        matchingSellOrder.setStatus(BondOrder.Status.FILLED);
                        sellSideMap.get(ticker).remove(matchingSellOrder);
                    }

                    // Create trade

                    Trade trade = createTrade(order, matchingSellOrder, matchingSellOrder.getQuantity(), matchingSellOrder.getPrice());
                    System.out.println("New Trade: " + trade);

                    // Find next matching sell order
                    matchingSellOrder = sellOrders.ceiling(order);
                } else {
                    break; // Price or quantity mismatch; stop searching
                }
            }
        } else {
            // Find matching buy orders with price >= sell price
            TreeSet<BondOrder> buyOrders = buySideMap.get(ticker);
            BondOrder matchingBuyOrder = Objects.nonNull(buySideMap.get(ticker)) ? buyOrders.first() : null;


            while (matchingBuyOrder != null && remainingQuantity > 0) {

                // Check price and quantity match
                if (matchingBuyOrder.getPrice() >= order.getPrice()) {

                    int quantityToFill = Math.min(order.getLeftoverQuantity(), matchingBuyOrder.getLeftoverQuantity());

                    order.setFilledQuantity(order.getFilledQuantity() + quantityToFill);
                    order.setLeftoverQuantity(order.getLeftoverQuantity() - quantityToFill);

                    matchingBuyOrder.setFilledQuantity(matchingBuyOrder.getFilledQuantity() + quantityToFill);
                    matchingBuyOrder.setLeftoverQuantity(matchingBuyOrder.getLeftoverQuantity() - quantityToFill);


                    // Update quantities
                    if (matchingBuyOrder.getLeftoverQuantity() == 0) {
                        matchingBuyOrder.setStatus(BondOrder.Status.FILLED);
                        buySideMap.get(ticker).remove(matchingBuyOrder);
                    }
                    if (order.getLeftoverQuantity() == 0) {
                        Trade trade = createTrade(matchingBuyOrder, order, quantityToFill, matchingBuyOrder.getPrice());
                        System.out.println("New Trade: " + trade);
                        order.setStatus(BondOrder.Status.FILLED);
                        sellSideMap.get(ticker).remove(order);
                        break; //order has been filled completely
                    }


                    // Create trade

                    Trade trade = createTrade(matchingBuyOrder, order, quantityToFill, matchingBuyOrder.getPrice());
                    System.out.println("New Trade: " + trade);

                    // Find next matching buy order
                    matchingBuyOrder = buyOrders.floor(order);
                } else {
                    break; // Price or quantity mismatch; stop searching
                }
            }
        }
    }


    private Trade createTrade(BondOrder buyOrder, BondOrder sellOrder, int quantity, Double price) {

        Trade trade = new Trade();

        trade.setId(buyOrder.getId() + "-" + sellOrder.getId());
        trade.setTicker(sellOrder.getTicker());
        trade.setPrice(price);
        trade.setQuantity(quantity);

        trade.setBuyerOrderId(buyOrder.getId());
        trade.setSellOrderId(sellOrder.getId());
        return trade;
    }


}
