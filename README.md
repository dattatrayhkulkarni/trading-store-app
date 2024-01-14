# trading-store-app
**A Store for managing Trades**

This application acts as a store for the trades.
Every trade record consists of below fields 
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

| **trade id  3** | -----> [trade version 4, trade version 5, trade version 6, trade version 8]


Below are the important public methods of the store application : 

1. Store the Trade Entry

   public boolean addToStore(Trade newTradeEntry)

   
   This method performs below validations :
   - Trade with previous maturity date is not added
   - Trade with same Trade Id but lower version is not added, exception is thrown
   - If Trade with same Trade Id and same version is received, the Trade is overwritten

2. Get the trade entries for the given trade is

   public List<Trade> getTrades (String tradeId)

Whenever one of these methods is called, any trades where the maturity date is passed, is updated.

3. Update the Expiry of Trades

   public void updateExpiryOfTrades()

This method updates the "expired" flag to 'Y'.
Since the trade store can have thousands of entries, going through all the entries is not an efficient solution.
To optimise the process to update the expiry of trades, another data structure is used, which stores the trade entries in list of buckets, based on the maturity date of trade.
So instead of scanning through the entire trade store, only the expired trades can ne searched from the "ExpiryTradeStore" and updated.

| **Maturity Date 1** | -----> [trade 1, trade 2]

| **Maturity Date 2** | -----> [trade 3, trade 4, trade 10]

| **Maturity Date 3** | -----> [trade  14, trade 20, trade 21, trade 22]

