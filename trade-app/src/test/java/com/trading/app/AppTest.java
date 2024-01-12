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


}
