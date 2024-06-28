package DSCoinPackage;

import HelperClasses.MerkleTree;
import HelperClasses.CRF;

public class TransactionBlock {

  public Transaction[] trarray;
  public TransactionBlock previous;
  public MerkleTree Tree;
  public String trsummary;
  public String nonce;
  public String dgst;

  TransactionBlock(Transaction[] t) {
    int trsize = t.length;
    trarray = new Transaction[trsize];

   
    for (int i = 0; i < t.length; i++) {
      trarray[i] = new Transaction(t[i]);
    }

    this.previous = null;          
    Tree = new MerkleTree();       
    trsummary = Tree.Build(trarray); 
    this.dgst = null;                
  }
  
  public boolean checkTransaction (Transaction t) {

    TransactionBlock src = t.coinsrc_block; 
    boolean flag = false;                 
   
    if (src != null) {
      for (Transaction transaction : src.trarray) {
        if (transaction != null && transaction.coinID.equals(t.coinID) && transaction.Destination == t.Source) {
          flag = true;
          break;
        }
      }
      if (!flag) return false;
    }

   
    int totalCoinID = 0;
    for (Transaction transaction : trarray) {
      if (transaction != null && transaction.coinID.equals(t.coinID)) {
        totalCoinID++;
      }
    }
    if (totalCoinID > 1) return false;

    
    TransactionBlock current = this.previous;
    TransactionBlock dest = t.coinsrc_block;
    while (current != dest) {
      for (Transaction transaction : current.trarray) {
        if (transaction != null && transaction.coinID.equals(t.coinID)) {
          return false;
        }
      }
      current = current.previous;
    }

    return true;
  }
}

