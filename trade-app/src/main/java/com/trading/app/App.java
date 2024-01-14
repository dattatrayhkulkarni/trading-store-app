package src.main.java.com.trading.app;

import src.main.java.com.trading.app.store.TradeException;

import java.time.LocalDate;
import java.util.Date;


public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Trading App started..." );


        LocalDate maturityDate =  LocalDate.now();
        LocalDate createdDate = LocalDate.now();

        com.trading.app.store.TradeStore tradeStore = new com.trading.app.store.TradeStore();

        com.trading.app.entity.Trade newTrade = new com.trading.app.entity.Trade("T5", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        try {
            tradeStore.addToStore(newTrade);
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }

        tradeStore.simulateExpiredTrade(LocalDate.now().plusDays(10));

        System.out.println(tradeStore.getExpiredTradeCount());


        /*
        LocalDate maturityDate = LocalDate.of(2024, 1, 27);
        LocalDate createdDate = LocalDate.now();



        com.trading.app.entity.Trade newTrade = new com.trading.app.entity.Trade("T1", 2, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        com.trading.app.store.TradeStore tradeStore = new com.trading.app.store.TradeStore();

        try {
            tradeStore.addToStore(newTrade);
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }

        com.trading.app.entity.Trade trade2 = new com.trading.app.entity.Trade("T1", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        try {
            tradeStore.addToStore(trade2);
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }


         */

    }
}
