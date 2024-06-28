package DSCoinPackage;
import DSCoinPackage.TransactionBlock;
import DSCoinPackage.Members;
public class Transaction {

  public String coinID;
  public Members Source;
  public Members Destination;
  public TransactionBlock coinsrc_block;
    public Transaction next;           
    public Transaction prev;            
  
    // Default constructor
    public Transaction() {}
  
    // Copy constructor
    public Transaction(Transaction copy) {
      if (copy != null) {
        this.coinID = copy.coinID;
        this.Source = copy.Source;
        this.Destination = copy.Destination;
        this.coinsrc_block = copy.coinsrc_block;
        this.next = copy.next;
        this.prev = copy.prev;
      }
    }
  
    
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;                     
      if (o == null || getClass() != o.getClass())    // Check if the object is null or of a different class
        return false;
  
      Transaction transaction = (Transaction) o;      // Cast the object to Transaction
  
      
      return coinID.equals(transaction.coinID) &&
             Source.UID.equals(transaction.Source.UID) &&
             Destination.UID.equals(transaction.Destination.UID) &&
             coinsrc_block == transaction.coinsrc_block;
    }
  }
  



