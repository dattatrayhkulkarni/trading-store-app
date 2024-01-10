package com.trading.app;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.trading.app.entity.Trade;
import com.trading.app.store.TradeStore;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void addSingletrade()
    {

        Date maturityDate = new Date(2024,5,20);
        Date createdDate = new Date();


        Trade newTrade = new Trade("T1", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        TradeStore tradeStore = new TradeStore();

        assertEquals(true,tradeStore.addToStore(newTrade));
    }

    @Test
    public void addTradeWithSameVersion() {

        Date maturityDate = new Date(2024,5,20);
        Date createdDate = new Date();


        Trade newTrade = new Trade("T1", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        TradeStore tradeStore = new TradeStore();

        tradeStore.addToStore(newTrade);

        Trade trade2 = new Trade("T1", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );


        assertEquals(tradeStore.getTrades("T1").size(),1);

    }

    @Test
    public void addTradeWithLowerVersioin() {

        Date maturityDate = new Date(2024,5,20);
        Date createdDate = new Date();


        Trade newTrade = new Trade("T1", 2, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        TradeStore tradeStore = new TradeStore();

        tradeStore.addToStore(newTrade);

        Trade trade2 = new Trade("T1", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        assertEquals(false,tradeStore.addToStore(trade2));


    }

    @Test
    public void addTradeWithDifferentVersions() {

        Date maturityDate = new Date(2024,5,20);
        Date createdDate = new Date();


        Trade newTrade = new Trade("T1", 2, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        TradeStore tradeStore = new TradeStore();

        tradeStore.addToStore(newTrade);

        Trade trade2 = new Trade("T1", 3, "CP-1", "B1",
                maturityDate,createdDate, 'N' );
        tradeStore.addToStore(trade2);

        assertEquals(tradeStore.getTrades("T1").size(),2);

    }

    @Test
    public void addTradeWithPreviousMaturityDate() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date  maturityDate = null;
        try {
            maturityDate = simpleDateFormat.parse("2010-01-09");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date createdDate = new Date();

        TradeStore tradeStore = new TradeStore();

        Trade newTrade = new Trade("T1", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        tradeStore.addToStore(newTrade);

        assertEquals(false,  tradeStore.addToStore(newTrade));

    }

    @Test
    public void addTradewithEmptyTradeId() {

        Date maturityDate = new Date();
        Date createdDate = new Date();

        TradeStore tradeStore = new TradeStore();

        Trade newTrade = new Trade("", 1, "CP-1", "B1",
                maturityDate,createdDate, 'N' );

        tradeStore.addToStore(newTrade);

        assertEquals(false,  tradeStore.addToStore(newTrade));

    }












}
