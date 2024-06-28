package DSCoinPackage;

import HelperClasses.Pair;

public class Moderator {

  // Initializes DSCoin_Honest with a specified number of coins
  public void initializeDSCoin(DSCoin_Honest DSObj, int coinCount) {
    Members moderator = new Members();
    moderator.UID = "Moderator";

    int startIndex = 100000;

    for (int i = 0; i < coinCount; i++) {
      Transaction transaction = new Transaction();
      transaction.Source = moderator;
      transaction.coinID = Integer.toString(i + startIndex);
      transaction.coinsrc_block = null;
      transaction.Destination = DSObj.memberlist[i % DSObj.memberlist.length];
      DSObj.pendingTransactions.AddTransaction(transaction);
    }

    while (DSObj.pendingTransactions.size() >= DSObj.bChain.tr_count) {
      mineBlock(DSObj);
    }

    DSObj.latestCoinID = Integer.toString(coinCount + startIndex - 1);
  }

  // Mines a block in DSCoin_Honest
  public void mineBlock(DSCoin_Honest DSObj) {
    int trCount = DSObj.bChain.tr_count;
    Transaction[] transactions = new Transaction[trCount];

    for (int i = 0; i < trCount; i++) {
      try {
        transactions[i] = DSObj.pendingTransactions.RemoveTransaction();
      } catch (EmptyQueueException e) {
        // Handle exception
      }
    }

    TransactionBlock minedBlock = new TransactionBlock(transactions);
    DSObj.bChain.InsertBlock_Honest(minedBlock);

    for (Transaction transaction : transactions) {
      transaction.Destination.mycoins.add(new Pair<>(transaction.coinID, minedBlock));
    }
  }

  // Initializes DSCoin_Malicious with a specified number of coins
  public void initializeDSCoin(DSCoin_Malicious DSObj, int coinCount) {
    Members moderator = new Members();
    moderator.UID = "Moderator";

    int startIndex = 100000;

    for (int i = 0; i < coinCount; i++) {
      Transaction transaction = new Transaction();
      transaction.Source = moderator;
      transaction.coinID = Integer.toString(i + startIndex);
      transaction.coinsrc_block = null;
      transaction.Destination = DSObj.memberlist[i % DSObj.memberlist.length];
      DSObj.pendingTransactions.AddTransaction(transaction);
    }

    while (DSObj.pendingTransactions.size() >= DSObj.bChain.tr_count) {
      mineBlock(DSObj);
    }

    DSObj.latestCoinID = Integer.toString(coinCount + startIndex - 1);
  }

  // Mines a block in DSCoin_Malicious
  public void mineBlock(DSCoin_Malicious DSObj) {
    int trCount = DSObj.bChain.tr_count;
    Transaction[] transactions = new Transaction[trCount];

    for (int i = 0; i < trCount; i++) {
      try {
        transactions[i] = DSObj.pendingTransactions.RemoveTransaction();
      } catch (EmptyQueueException e) {
        // Handle exception
      }
    }

    TransactionBlock minedBlock = new TransactionBlock(transactions);
    DSObj.bChain.InsertBlock_Malicious(minedBlock);

    for (Transaction transaction : transactions) {
      transaction.Destination.mycoins.add(new Pair<>(transaction.coinID, minedBlock));
    }
  }
}

