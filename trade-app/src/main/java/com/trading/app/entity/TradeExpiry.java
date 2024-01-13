package src.main.java.com.trading.app.entity;

import java.time.LocalDate;

public class TradeExpiry {
    long expiryDaysSinceEpoch;

    private String tradeId;
    private int version;
    private LocalDate maturityDate;


    public TradeExpiry(long expiryDaysSinceEpoch, String tradeId, int version, LocalDate maturityDate) {
        this.expiryDaysSinceEpoch = expiryDaysSinceEpoch;
        this.tradeId = tradeId;
        this.version = version;
        this.maturityDate = maturityDate;
    }

    public long getExpiryDaysSinceEpoch() {
        return expiryDaysSinceEpoch;
    }

    public String getTradeId() {
        return tradeId;
    }

    public int getVersion() {
        return version;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }
}
