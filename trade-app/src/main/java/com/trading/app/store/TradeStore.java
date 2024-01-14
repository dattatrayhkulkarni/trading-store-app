package com.trading.app.store;

import com.trading.app.entity.Trade;
import src.main.java.com.trading.app.entity.TradeExpiry;
import src.main.java.com.trading.app.store.ExpiryTradeStore;
import src.main.java.com.trading.app.store.TradeException;

import java.time.LocalDate;
import java.util.*;

public class TradeStore {

    Map<String, List<Trade>> tradeStore = new HashMap<>();

    ExpiryTradeStore expiryTradeStore = new ExpiryTradeStore();

    public boolean addToStore(Trade newTradeEntry) throws TradeException {

        // Update the Expired Trades
        updateExpiryOfTrades();

        // check if trade id or the version is null
        if(newTradeEntry.getTradeId().isEmpty() ) {
            return false;
        }

        // Ingore the trade with previous maturity Date
        //if(newTradeEntry.getMaturityDate().before( new Date())) {
        if(newTradeEntry.getMaturityDate().isBefore(LocalDate.now())) {
            return false;
        }


        if(tradeStore.get(newTradeEntry.getTradeId()) == null) {

            List<Trade> tradeList = new ArrayList<>();
            tradeList.add(newTradeEntry);

            tradeStore.put(newTradeEntry.getTradeId(), tradeList);
            expiryTradeStore.addToExpiryStore(newTradeEntry);


        } else {
            List<Trade> tradeList = tradeStore.get(newTradeEntry.getTradeId());

            // Check if the version of this new trade is lower,
            // then throw the TradeException
            if(isLowerversion(tradeList, newTradeEntry)) {
                throw new TradeException("Lower version not allowed for Trade");
            }

            //  if the trade with same version exists, then override that trade
            boolean sameVersion = false;
            int index = 0;
            for (Trade existingTrade: tradeList
            ) {
                if(existingTrade.getVersion() == newTradeEntry.getVersion()) {
                    sameVersion = true;
                    tradeList.set(index,newTradeEntry);
                    // This needs special handling
                    expiryTradeStore.addToExpiryStore(newTradeEntry);
                    break;
                }
                index++;
            }

            if(! sameVersion) {
                tradeList.add(newTradeEntry);
                tradeStore.put(newTradeEntry.getTradeId(), tradeList);
                expiryTradeStore.addToExpiryStore(newTradeEntry);
            }

        }

        return true;
    }

    public List<Trade> getTrades (String tradeId) {

        // Update the Expired Trades
        updateExpiryOfTrades();

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


    public void simulateExpiredTrade(LocalDate todaysDate) {

        List<TradeExpiry> expiredTrades = expiryTradeStore.getExpiredTrades(todaysDate);

        if(expiredTrades != null) {

            for (TradeExpiry tradeExpiry : expiredTrades) {
                updateExpiryFlag(tradeExpiry.getTradeId(), tradeExpiry.getVersion());
            }

            expiryTradeStore.deleteExpiredTrades(todaysDate);

        }

        /*

        for (Map.Entry<String, List<Trade>> entry : tradeStore.entrySet() ) {
            List<Trade> tradeList = entry.getValue();

            int index = 0;
            for(Trade existingTrade : tradeList) {
                if(existingTrade.getMaturityDate().isBefore(todaysDate)) {
                    existingTrade.setExpired('Y');
                    tradeList.set(index, existingTrade);
                }
                index++;
            }

        }


         */
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

    private void updateExpiryFlag(String tradeId, int version) {

        List<Trade> tradeList = getTrades(tradeId);

        int index =0;
            for(Trade existingTrade : tradeList) {
                if(existingTrade.getVersion() == version) {
                    existingTrade.setExpired('Y');
                    tradeList.set(index, existingTrade);
                    break;
                }
                index++;
            }

        }

    public void updateExpiryOfTrades() {

        List<TradeExpiry> expiredTrades = expiryTradeStore.getExpiredTrades(LocalDate.now());

        if(expiredTrades == null || expiredTrades.isEmpty()) {
            return;
        } else {
            for (TradeExpiry expiredTradeEntry: expiredTrades) {

                // Update the Expired flag in the Trade Store
                String tradeId = expiredTradeEntry.getTradeId();
                int version = expiredTradeEntry.getVersion();
                updateExpiryFlag(tradeId,version);
            }
            // Since the flag for expired trades is set to Y, remove from the entries from expired trades store
            expiryTradeStore.deleteExpiredTrades(LocalDate.now());

        }
    }


    // This internal method is set the maturity date, so that  expiry of Trades can be simulated
    public void updateMaturityDate(String tradeId, int version, LocalDate maturityDate) {

        List<Trade> tradeList = getTrades(tradeId);

        int index =0;
        for(Trade existingTrade : tradeList) {
            if(existingTrade.getVersion() == version) {
                existingTrade.setMaturityDate(maturityDate);
                tradeList.set(index, existingTrade);
                break;
            }
            index++;
        }

    }



}
