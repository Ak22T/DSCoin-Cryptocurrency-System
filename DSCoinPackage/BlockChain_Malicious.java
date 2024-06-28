package DSCoinPackage;

import HelperClasses.CRF;
import HelperClasses.MerkleTree;

public class BlockChain_Malicious {

  public static final String start_string = "DSCoin";
  public TransactionBlock[] lastBlocksList;

  public TransactionBlock FindLongestValidChain() {

    int maxLength = 0;
    TransactionBlock longestChainBlock = null;

    for (TransactionBlock block : lastBlocksList) {
      int currentLength = 0;
      TransactionBlock currentBlock = block;
      while (currentBlock != null && BlockValidator.isValidBlock(currentBlock)) {
        currentLength++;
        currentBlock = currentBlock.previous;
      }

      if (currentLength > maxLength) {
        maxLength = currentLength;
        longestChainBlock = currentBlock;
      }
    }

    return longestChainBlock;
  }

  public void InsertBlock_Malicious(TransactionBlock newBlock) {
    TransactionBlock lastBlock = FindLongestValidChain();
    CRF crfObj = new CRF(64);
    newBlock.previous = lastBlock;
    String lastBlockString = lastBlock == null ? start_string : lastBlock.dgst;
    String nonceComputed = GetNonce(lastBlockString, newBlock.trsummary);

    newBlock.nonce = nonceComputed;
    newBlock.dgst = crfObj.Fn(lastBlockString + "#" + newBlock.trsummary + "#" + newBlock.nonce);

    int locn = -1;
    for (int i = 0; i < lastBlocksList.length; i++) {
      if (lastBlocksList[i] == lastBlock) {
        locn = i;
        break;
      }
    }

    if (locn == -1) {

    
      for (int i = 0; i < lastBlocksList.length; i++) {
        if (lastBlocksList[i] == null) {
          lastBlocksList[i] = newBlock;
          break;
        }
      }


    } else {
      lastBlocksList[locn] = newBlock;
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

  public static boolean isValidBlock(TransactionBlock block) {
    
    String tBPrevDgst = block.previous == null ? start_string : block.previous.dgst;
    CRF crfObj = new CRF(64);

    if (!block.dgst.substring(0, 4).equals("0000"))
      return false;

    if (!crfObj.Fn(tBPrevDgst + "#" + block.trsummary + "#" + block.nonce).equals(block.dgst))
      return false;

    MerkleTree verifyTree = new MerkleTree();
    String ver = verifyTree.Build(block.trarray);
    if (!ver.equals(block.trsummary))
      return false;

    for (int i = 0; i < block.trarray.length; i++) {
      if (!block.checkTransaction(block.trarray[i]))
        return false;
    }

    return true;
  }
}
