package src.test.java.com.trading.app;

import static org.junit.Assert.assertEquals;

import com.trading.app.entity.Trade;
import com.trading.app.store.TradeStore;
import org.junit.Test;
import src.main.java.com.trading.app.store.TradeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class AppTest 
{


    @Test
    public void addSingletrade()
    {

        LocalDate maturityDate = LocalDate.of(2024, 5, 20);
        LocalDate createdDate = LocalDate.now();


        Trade newTrade = new Trade("T1", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        TradeStore tradeStore = new TradeStore();

        try {
            assertEquals(true,tradeStore.addToStore(newTrade));
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void addTradeWithSameVersion() {


        LocalDate maturityDate = LocalDate.of(2024, 5, 20);
        LocalDate createdDate = LocalDate.now();


        Trade newTrade = new Trade("T1", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        TradeStore tradeStore = new TradeStore();

        try {
            tradeStore.addToStore(newTrade);
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }

        Trade trade2 = new Trade("T1", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );


        assertEquals(tradeStore.getTrades("T1").size(),1);

    }



    @Test(expected = TradeException.class)
    public void addTradeWithLowerVersioin() throws TradeException {

        LocalDate maturityDate = LocalDate.of(2024, 5, 20);
        LocalDate createdDate = LocalDate.now();


        Trade newTrade = new Trade("T1", 2, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        TradeStore tradeStore = new TradeStore();

        try {
            tradeStore.addToStore(newTrade);
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }

        Trade trade2 = new Trade("T1", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        tradeStore.addToStore(trade2);

    }




    @Test
    public void addTradeWithDifferentVersions() {


        LocalDate maturityDate = LocalDate.of(2024, 5, 20);
        LocalDate createdDate = LocalDate.now();


        Trade newTrade = new Trade("T1", 2, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        TradeStore tradeStore = new TradeStore();

        try {
            tradeStore.addToStore(newTrade);
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }

        Trade trade2 = new Trade("T1", 3, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        try {
            tradeStore.addToStore(trade2);
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(tradeStore.getTrades("T1").size(),2);

    }

    @Test
    public void addTradeWithPreviousMaturityDate() {


        LocalDate maturityDate = LocalDate.of(2010, 5, 20);
        LocalDate createdDate = LocalDate.now();

        TradeStore tradeStore = new TradeStore();

        Trade newTrade = new Trade("T1", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        try {
            tradeStore.addToStore(newTrade);
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }

        try {
            assertEquals(false,  tradeStore.addToStore(newTrade));
        } catch (TradeException e) {
            System.out.println(e.getMessage());

        }

    }

    @Test
    public void addTradewithEmptyTradeId() {

        LocalDate maturityDate = LocalDate.of(2010, 5, 20);
        LocalDate createdDate = LocalDate.now();

        TradeStore tradeStore = new TradeStore();

        Trade newTrade = new Trade("", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        try {
            tradeStore.addToStore(newTrade);
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }

        try {
            assertEquals(false,  tradeStore.addToStore(newTrade));
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }

    }


    @Test
    public void testTradeExpiry() {

        LocalDate maturityDate =  LocalDate.now();
        LocalDate createdDate = LocalDate.now();

        TradeStore tradeStore = new TradeStore();

        Trade newTrade = new Trade("T5", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        try {
            tradeStore.addToStore(newTrade);
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }

        tradeStore.simulateExpiredTrade(LocalDate.now().plusDays(10));

        assertEquals(1, tradeStore.getExpiredTradeCount());

    }


    @Test
    public void testZeroTradeExpiry() {

        LocalDate maturityDate =  LocalDate.now();
        LocalDate createdDate = LocalDate.now();

        TradeStore tradeStore = new TradeStore();

        Trade trade1 = new Trade("T5", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );
        Trade trade2 = new Trade("T6", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );
        Trade trade3 = new Trade("T7", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );
        Trade trade4 = new Trade("T8", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );
        Trade trade5 = new Trade("T9", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );


        try {
            tradeStore.addToStore(trade1);
            tradeStore.addToStore(trade2);
            tradeStore.addToStore(trade3);
            tradeStore.addToStore(trade4);
            tradeStore.addToStore(trade5);
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(0, tradeStore.getExpiredTradeCount());

    }


    @Test
    public void testMultipleTradeExpiry() {

        LocalDate maturityDate =  LocalDate.now();
        LocalDate createdDate = LocalDate.now();

        TradeStore tradeStore = new TradeStore();

        Trade trade1 = new Trade("T5", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );
        Trade trade2 = new Trade("T6", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );
        Trade trade3 = new Trade("T7", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );
        Trade trade4 = new Trade("T8", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );
        Trade trade5 = new Trade("T9", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );


        try {
            tradeStore.addToStore(trade1);
            tradeStore.addToStore(trade2);
            tradeStore.addToStore(trade3);
            tradeStore.addToStore(trade4);
            tradeStore.addToStore(trade5);
        } catch (TradeException e) {
            System.out.println(e.getMessage());
        }

        tradeStore.updateMaturityDate(trade1.getTradeId(),trade1.getVersion(), LocalDate.now().minusDays(1));

        tradeStore.getTrades(trade1.getTradeId());

        assertEquals(0, tradeStore.getExpiredTradeCount());

    }

}
