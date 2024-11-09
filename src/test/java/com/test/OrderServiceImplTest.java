package com.test;

import com.test.model.BondOrder;
import com.test.service.OrderServiceImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class OrderServiceImplTest {

    @Test
    public void simpleBuySell() {
        OrderServiceImpl orderService = new OrderServiceImpl();

        BondOrder buyOrder1 = new BondOrder("AAPL_BUY_1", "AAPL", BondOrder.Side.BUY, 100.0, 10, 0, 10, BondOrder.Status.RECEIVED, System.currentTimeMillis());
        BondOrder buyOrder2 = new BondOrder("AAPL_BUY_2", "AAPL", BondOrder.Side.BUY, 100.0, 15, 0, 15, BondOrder.Status.RECEIVED, System.currentTimeMillis());


        // Submit sell orders
        BondOrder sellOrder1 = new BondOrder("AAPL_SELL_1", "AAPL", BondOrder.Side.SELL, 100.0, 20, 0, 20, BondOrder.Status.RECEIVED, System.currentTimeMillis());
        BondOrder sellOrder2 = new BondOrder("AAPL_SELL_2", "AAPL", BondOrder.Side.SELL, 100.0, 5, 0, 5, BondOrder.Status.RECEIVED, System.currentTimeMillis());

        orderService.submitOrder(buyOrder1);
        orderService.submitOrder(buyOrder2);
        orderService.submitOrder(sellOrder1);
        orderService.submitOrder(sellOrder2);

        for (TreeSet<BondOrder> bondOrders : Arrays.asList(orderService.getBuyOrdersbyTicker("AAPL"), orderService.getSellOrdersbyTicker("AAPL"))) {
            assertEquals(0, bondOrders.size());
        }
    }

    @Test
    public void unmatchedBuySell() {
        OrderServiceImpl orderService = new OrderServiceImpl();

        BondOrder buyOrder1 = new BondOrder("AAPL_BUY_1", "AAPL", BondOrder.Side.BUY, 100.0, 40, 0, 40, BondOrder.Status.RECEIVED, System.currentTimeMillis());
        BondOrder buyOrder2 = new BondOrder("AAPL_BUY_2", "AAPL", BondOrder.Side.BUY, 100.0, 15, 0, 15, BondOrder.Status.RECEIVED, System.currentTimeMillis());


        // Submit sell orders
        BondOrder sellOrder1 = new BondOrder("AAPL_SELL_1", "AAPL", BondOrder.Side.SELL, 100.0, 20, 0, 20, BondOrder.Status.RECEIVED, System.currentTimeMillis());
        BondOrder sellOrder2 = new BondOrder("AAPL_SELL_2", "AAPL", BondOrder.Side.SELL, 100.0, 5, 0, 5, BondOrder.Status.RECEIVED, System.currentTimeMillis());

        orderService.submitOrder(buyOrder1);
        orderService.submitOrder(buyOrder2);
        orderService.submitOrder(sellOrder1);
        orderService.submitOrder(sellOrder2);


        assertEquals(2, orderService.getBuyOrdersbyTicker("AAPL").size());
        assertEquals(0, orderService.getSellOrdersbyTicker("AAPL").size());
    }

    @Test
    public void unmatchedSellBuy() {
        OrderServiceImpl orderService = new OrderServiceImpl();

        // Submit sell orders
        BondOrder sellOrder1 = new BondOrder("AAPL_SELL_1", "AAPL", BondOrder.Side.SELL, 100.0, 40, 0, 40, BondOrder.Status.RECEIVED, System.currentTimeMillis());
        BondOrder sellOrder2 = new BondOrder("AAPL_SELL_2", "AAPL", BondOrder.Side.SELL, 100.0, 5, 0, 5, BondOrder.Status.RECEIVED, System.currentTimeMillis());

        BondOrder buyOrder1 = new BondOrder("AAPL_BUY_1", "AAPL", BondOrder.Side.BUY, 100.0, 20, 0, 20, BondOrder.Status.RECEIVED, System.currentTimeMillis());
        BondOrder buyOrder2 = new BondOrder("AAPL_BUY_2", "AAPL", BondOrder.Side.BUY, 100.0, 15, 0, 15, BondOrder.Status.RECEIVED, System.currentTimeMillis());

        orderService.submitOrder(sellOrder1);
        orderService.submitOrder(sellOrder2);
        orderService.submitOrder(buyOrder1);
        orderService.submitOrder(buyOrder2);

        assertEquals(0, orderService.getBuyOrdersbyTicker("AAPL").size());
        assertEquals(2, orderService.getSellOrdersbyTicker("AAPL").size());
    }

    @Test
    public void noPriceMatch() {
        OrderServiceImpl orderService = new OrderServiceImpl();

        // Submit sell orders
        BondOrder sellOrder1 = new BondOrder("AAPL_SELL_1", "AAPL", BondOrder.Side.SELL, 115.0, 40, 0, 40, BondOrder.Status.RECEIVED, System.currentTimeMillis());
        BondOrder sellOrder2 = new BondOrder("AAPL_SELL_2", "AAPL", BondOrder.Side.SELL, 110.0, 5, 0, 5, BondOrder.Status.RECEIVED, System.currentTimeMillis());

        BondOrder buyOrder1 = new BondOrder("AAPL_BUY_1", "AAPL", BondOrder.Side.BUY, 105.0, 20, 0, 20, BondOrder.Status.RECEIVED, System.currentTimeMillis());
        BondOrder buyOrder2 = new BondOrder("AAPL_BUY_2", "AAPL", BondOrder.Side.BUY, 100.0, 15, 0, 15, BondOrder.Status.RECEIVED, System.currentTimeMillis());

        orderService.submitOrder(sellOrder1);
        orderService.submitOrder(sellOrder2);
        orderService.submitOrder(buyOrder1);
        orderService.submitOrder(buyOrder2);

        assertEquals(2, orderService.getBuyOrdersbyTicker("AAPL").size());
        assertEquals(2, orderService.getSellOrdersbyTicker("AAPL").size());
    }

    @Test
    public void oneEachOutstanding() {
        OrderServiceImpl orderService = new OrderServiceImpl();

        // Submit sell orders
        BondOrder sellOrder1 = new BondOrder("AAPL_SELL_1", "AAPL", BondOrder.Side.SELL, 100.0, 40, 0, 40, BondOrder.Status.RECEIVED, System.currentTimeMillis());
        BondOrder sellOrder2 = new BondOrder("AAPL_SELL_2", "AAPL", BondOrder.Side.SELL, 110.0, 5, 0, 5, BondOrder.Status.RECEIVED, System.currentTimeMillis());

        BondOrder buyOrder1 = new BondOrder("AAPL_BUY_1", "AAPL", BondOrder.Side.BUY, 100.0, 40, 0, 40, BondOrder.Status.RECEIVED, System.currentTimeMillis());
        BondOrder buyOrder2 = new BondOrder("AAPL_BUY_2", "AAPL", BondOrder.Side.BUY, 100.0, 15, 0, 15, BondOrder.Status.RECEIVED, System.currentTimeMillis());

        orderService.submitOrder(sellOrder1);
        orderService.submitOrder(sellOrder2);
        orderService.submitOrder(buyOrder1);
        orderService.submitOrder(buyOrder2);

        assertEquals(1, orderService.getBuyOrdersbyTicker("AAPL").size());
        assertEquals(1, orderService.getSellOrdersbyTicker("AAPL").size());
    }
}