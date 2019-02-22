//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P04 EXCEPTIONAL BANKING
// Files:           TransactionGroup.java, Account.java, ExceptionalBankingTests.java
// Course:          300, 2018, fall, 
//
// Author:          Ante Du
// Email:           adu3@wisc.edu
// Lecturer's Name: Gary
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         None
// Online Sources:  None
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.zip.DataFormatException;

public class TransactionGroup {

  private enum EncodingType { BINARY_AMOUNT, INTEGER_AMOUNT, QUICK_WITHDRAW }; //make sure 3 types
  private EncodingType type; //show 3 different types
  private int[] values; //array of values

  /**to make sure:
   * transaction group encoding cannot be null or empty
     the first element within a transaction group must be 0, 1, or 2
     binary amount transaction groups may only contain 0s and 1s
     integer amount transaction groups may not contain 0s
     quick withdraw transaction groups must contain 5 elements
     quick withdraw transaction groups may not contain negative numbers
     then run the program
   * @param groupEncoding
   * @throws DataFormatException
   */
  public TransactionGroup(int[] groupEncoding) throws DataFormatException {
    if(groupEncoding == null) {
      throw new DataFormatException("transaction group encoding cannot be null or empty");
    }

    if(groupEncoding[0]!= 0 && groupEncoding[0]!= 1 && groupEncoding[0]!=2) {
      throw new DataFormatException("the first element within a transaction group must be 0, 1, or 2");
    }

    for(int i = 0; i < groupEncoding.length; ++i) {
      if(groupEncoding[0] == 0) {
        if(groupEncoding[i]!= 0 && groupEncoding[i]!= 1) {
          throw new DataFormatException("binary amount transaction groups may only contain 0s and 1s");
        }
      }
    }

    for(int i = 0; i < groupEncoding.length; ++i) {
      if(groupEncoding[0] == 1) {
        if(groupEncoding[i] == 0 ) {
          throw new DataFormatException("integer amount transaction groups may not contain 0s");
        }
      }
    }

    if(groupEncoding[0] == 2) {
      if(groupEncoding.length != 5) {
        throw new DataFormatException("quick withdraw transaction groups must contain 5 elements");
      }
    }

    for(int i = 0; i < groupEncoding.length; ++i)
      if(groupEncoding[0] == 2) {
        if(groupEncoding[i] < 0) {
          throw new DataFormatException("quick withdraw transaction groups may not contain negative numbers");
        }
      }
    this.type = EncodingType.values()[groupEncoding[0]];
    this.values = new int[groupEncoding.length-1];
    for(int i=0;i<values.length;i++)
      this.values[i] = groupEncoding[i+1];//get the value
  }
  //a constructor to get the transaction court with 3 different type
  public int getTransactionCount() {
    int transactionCount = 0;
    switch(this.type) {//use switch to get into different type directly with no loop and if condition
      case BINARY_AMOUNT:
        int lastAmount = -1;
        for(int i=0;i<this.values.length;i++) {
          if(this.values[i] != lastAmount) {
            transactionCount++; 
            lastAmount = this.values[i];            
          }
        }
        break;
      case INTEGER_AMOUNT:
        transactionCount = values.length;
        break;
      case QUICK_WITHDRAW:
        for(int i=0;i<this.values.length;i++)
          transactionCount += this.values[i];        
    }
    return transactionCount;
  }
  //the method to make sure without the indexoutofbounds problem than return the transaction amount with 3 different type
  public int getTransactionAmount(int transactionIndex)throws IndexOutOfBoundsException{
    int  transactionCount = getTransactionCount();
    if(transactionIndex >= transactionCount ) {
      throw new IndexOutOfBoundsException( transactionIndex + "does not fall within the range of " + transactionCount);
    }
    transactionCount = 0;
    switch(this.type) { //use switch to get into different type directly with no loop and if condition
      case BINARY_AMOUNT:
        int lastAmount = -1;
        int amountCount = 0;
        for(int i=0;i<=this.values.length;i++) {
          if(i == this.values.length || this.values[i] != lastAmount)  { 
            if(transactionCount-1 == transactionIndex) {// calculate the transaction count
              if(lastAmount == 0)
                return -1 * amountCount;
              else
                return +1 * amountCount;
            }
            transactionCount++;       
            lastAmount = this.values[i];
            amountCount = 1;
          } else
            amountCount++;
          lastAmount = this.values[i];
        } 
        break;
      case INTEGER_AMOUNT:
        return this.values[transactionIndex];
      case QUICK_WITHDRAW:
        final int[] QW_AMOUNTS = new int[] {-20, -40, -80, -100};//input the withdraw amount
        for(int i=0;i<this.values.length;i++) 
          for(int j=0;j<this.values[i];j++)
            if(transactionCount == transactionIndex) 
              return QW_AMOUNTS[i]; 
            else 
              transactionCount++;
    }
    return -1;
  }
}

