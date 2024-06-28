package DSCoinPackage;

public class TransactionQueue {

  public Transaction firstTransaction;
  public Transaction lastTransaction;
  public int numTransactions;


  public void AddTransaction(Transaction transaction) {
    if (numTransactions == 0) {
      firstTransaction = transaction;
      lastTransaction = transaction;
    } else {
      lastTransaction.next = transaction;
      transaction.prev = lastTransaction;
      lastTransaction = transaction;
    }
    numTransactions++;
  }
  

  public Transaction RemoveTransaction() throws EmptyQueueException {
    if (numTransactions == 0) {
      throw new EmptyQueueException();
    }

    Transaction toReturn = firstTransaction;
    
    if (numTransactions == 1) {
      firstTransaction = null;
      lastTransaction = null;
    } else {
      firstTransaction = firstTransaction.next;
      firstTransaction.prev = null;
    }

    numTransactions--;
    return toReturn;
  }

  public int size() {
    return numTransactions;
  }
}
