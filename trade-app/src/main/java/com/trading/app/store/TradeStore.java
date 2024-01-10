package com.trading.app.store;

import com.trading.app.entity.Trade;

import java.util.*;

public class TradeStore {

    Map<String, List<Trade>> tradeStore = new HashMap<>();

    public boolean addToStore(Trade newTradeEntry) {

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

            // Check if the version of this new trade is lower
            if(isLowerversion(tradeList, newTradeEntry)) {
                return  false;
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

}
