package src.main.java.com.trading.app.store;

import src.main.java.com.trading.app.entity.TradeExpiry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trading.app.entity.Trade;

// Expiry store maintains the records of the Trade entries based on the Expiry date
// | expiry days since Epoch | ----> [trade Expiry Entry 1 , trade Expiry Entry 2 ]

public class ExpiryTradeStore {

    Map<Long, List<TradeExpiry>> expiryStore;

    public ExpiryTradeStore() {
        expiryStore = new HashMap<>();
    }

    public void addToExpiryStore(Trade newTradeEntry) {

        long expirySinceEpoch = newTradeEntry.getMaturityDate().toEpochDay();

       TradeExpiry tradeExpiryEntry = new TradeExpiry(expirySinceEpoch, newTradeEntry.getTradeId(),
                                                newTradeEntry.getVersion(),newTradeEntry.getMaturityDate());

       if(expiryStore.get(expirySinceEpoch) == null) {

           List<TradeExpiry> tradeExpiryList = new ArrayList<>();
           tradeExpiryList.add(tradeExpiryEntry);

           expiryStore.put(expirySinceEpoch, tradeExpiryList);

       } else {
           List<TradeExpiry> tradeExpiryList = expiryStore.get(expirySinceEpoch);
           tradeExpiryList.add(tradeExpiryEntry);

           expiryStore.put(expirySinceEpoch, tradeExpiryList);

       }

    }

    public List<TradeExpiry> getExpiredTrades(LocalDate expirydate) {
        return expiryStore.get(expirydate.toEpochDay()-1);
    }

    public void  deleteExpiredTrades(LocalDate expirydate) {
        expiryStore.remove(expirydate.toEpochDay());
    }

}
