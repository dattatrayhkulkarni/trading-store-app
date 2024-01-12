package com.trading.app.entity;

import java.util.Date;
import java.util.Objects;

public class Trade {

    private String tradeId;
    private int version;
    private String counterPartyId;
    private String bookId;
    private Date maturityDate;
    private Date createdDate;
    private char expired;


    public Trade(String tradeId, int version, String counterPartyId, String bookId, Date maturityDate, Date createdDate, char expired) {
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.expired = expired;

    }


    public String getTradeId() {
        return tradeId;
    }

    public int getVersion() {
        return version;
    }

    public String getCounterPartyId() {
        return counterPartyId;
    }

    public String getBookId() {
        return bookId;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public char getExpired() {
        return expired;
    }


    @Override
    public String toString() {
        return "Trade{" +
                "tradeId='" + tradeId + '\'' +
                ", version=" + version +
                ", counterPartyId='" + counterPartyId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", maturityDate=" + maturityDate +
                ", createdDate=" + createdDate +
                ", expired=" + expired +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return getVersion() == trade.getVersion() && getExpired() == trade.getExpired() && getTradeId().equals(trade.getTradeId()) && getCounterPartyId().equals(trade.getCounterPartyId()) && getBookId().equals(trade.getBookId()) && getMaturityDate().equals(trade.getMaturityDate()) && getCreatedDate().equals(trade.getCreatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTradeId(), getVersion(), getCounterPartyId(), getBookId(), getMaturityDate(), getCreatedDate(), getExpired());
    }

    public void setExpired(char expired) {
        this.expired = expired;
    }
}

