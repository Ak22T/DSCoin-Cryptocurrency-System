package DSCoinPackage;

import HelperClasses.CRF;

public class BlockChain_Honest {

  public int transactionCount; 
  public static final String start_string = "DSCoin";
  public TransactionBlock lastBlock;

  public void InsertBlock_Honest(TransactionBlock newBlock) {
    CRF crfObj = new CRF(64);

  
    if (BlockValidator.isValidBlock(lastBlock, newBlock)) {
      newBlock.previous = lastBlock;
      String lastBlockString = lastBlock == null ? start_string : lastBlock.dgst;
      String nonceComputed = GetNonce(lastBlockString, newBlock.trsummary);

      newBlock.nonce = nonceComputed;
      newBlock.dgst = crfObj.Fn(lastBlockString + "#" + newBlock.trsummary + "#" + newBlock.nonce);

      lastBlock = newBlock;
      transactionCount += newBlock.trarray.length; 
    } else {
    
    }
  }

  private String GetNonce(String a, String b) {
    CRF crfObj = new CRF(64);
    long i = 1000000000L;
    for (i = 1000000000L; i < 10000000000L; i++) {
      String non = String.valueOf(i);
      String crfComp = a.concat("#").concat(b).concat("#").concat(non);
      if (crfObj.Fn(crfComp).substring(0, 4).equals("0000")) {
        return non;
      }
    }
    return "";
  }
}

class BlockValidator {

  public static boolean isValidBlock(TransactionBlock previousBlock, TransactionBlock newBlock) {
  
    if (previousBlock == null) { 
      return newBlock.dgst.substring(0, 4).equals("0000");
    } else {
      String expectedHash = previousBlock.dgst;
      CRF crfObj = new CRF(64);
      String actualHash = crfObj.Fn(expectedHash + "#" + newBlock.trsummary + "#" + newBlock.nonce);
      return actualHash.equals(newBlock.dgst);
    }
  }
}
