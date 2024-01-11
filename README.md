# trading-store-app
**A Store for managing Trades**

This application acts as a store for the trades.
Every trade redcord conists of below fields 
- trade id
- version
- counter party id
- book id
- maturity date
- created date
- expired (flag)

This application stores the records into one in-memory data structure, which consists a Hashmap and a List.
For every trade id, there can be multiple records with different versions.
Trade id is used as the key for the Hashmap and the trades with same trade id and different versions are added to the list, for the Hashmap entry. 


| **trade id  1** | -----> [trade version 1, trade version 2, trade version 3] 

| **trade id  2** | -----> [trade version 1, trade version 2, trade version 4]

| **trade id  3** | -----> [trade version 4, trade version 5, trade version 6]


Thee are two public methods to the store appliaction : 

1. Store the Trade Entry
   public boolean addToStore(Trade newTradeEntry)

2. Get the trade entries for the given trade is
   public List<Trade> getTrades (String tradeId)

