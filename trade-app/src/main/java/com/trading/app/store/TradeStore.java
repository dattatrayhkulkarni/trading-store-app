package com.trading.app.store;

import com.trading.app.entity.Trade;
import src.main.java.com.trading.app.store.TradeException;

import java.util.*;

public class TradeStore {

    Map<String, List<Trade>> tradeStore = new HashMap<>();

    public boolean addToStore(Trade newTradeEntry) throws TradeException {

        // check if trade id or the version is null
        if(newTradeEntry.getTradeId().isEmpty() ) {
            return false;
        }

        // Ingore the trade with previous maturity Date
        if(newTradeEntry.getMaturityDate().before( new Date())) {
            return false;
        }


        if(tradeStore.get(newTradeEntry.getTradeId()) == null) {

            List<Trade> tradeList = new ArrayList<>();
            tradeList.add(newTradeEntry);

            tradeStore.put(newTradeEntry.getTradeId(), tradeList);

        } else {
            List<Trade> tradeList = tradeStore.get(newTradeEntry.getTradeId());

            // Check if the version of this new trade is lower,
            // then throw the TradeException
            if(isLowerversion(tradeList, newTradeEntry)) {
                throw new TradeException("Lower version not allowed for Trade");
                //return  false;
            }

            //  if the trade with same version exists, then override that trade
            boolean sameVersion = false;
            int index = 0;
            for (Trade existingTrade: tradeList
            ) {
                if(existingTrade.getVersion() == newTradeEntry.getVersion()) {
                    sameVersion = true;
                    tradeList.set(index,newTradeEntry);
                    break;
                }
                index++;
            }

            if(! sameVersion) {
                tradeList.add(newTradeEntry);
                tradeStore.put(newTradeEntry.getTradeId(), tradeList);
            }

        }

        return true;
    }

    public List<Trade> getTrades (String tradeId) {
        List<Trade> tradeList = tradeStore.get(tradeId);

        return tradeList;
    }

    private boolean isLowerversion(List<Trade> tradeList, Trade trade) {

        boolean lowwerVersion = false;

        // iterate through the list and
        // check if the version of this trade is lower than the existing trades for the same Trade id
        for(Trade existingTrade : tradeList) {
            if(existingTrade.getVersion() > trade.getVersion()) {
                lowwerVersion = true;
                break;
            }
        }

        return lowwerVersion;
    }


    public void simulateExpiredTrade(Date todaysDate) {

        for (Map.Entry<String, List<Trade>> entry : tradeStore.entrySet() ) {
            List<Trade> tradeList = entry.getValue();

            int index = 0;
            for(Trade existingTrade : tradeList) {
                if(existingTrade.getMaturityDate().before(todaysDate)) {
                    existingTrade.setExpired('Y');
                    tradeList.set(index, existingTrade);
                }
                index++;
            }

        }
    }

    public int getExpiredTradeCount() {

        int expiredTradeCount = 0;

        for(Map.Entry<String, List<Trade>> entey : tradeStore.entrySet()) {
            List<Trade> tradeList = entey.getValue();

            for(Trade existingTrade : tradeList) {
                if(existingTrade.getExpired() == 'Y') {
                    expiredTradeCount ++;
                }
            }
        }

        return expiredTradeCount;

    }



}
