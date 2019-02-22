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

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.io.File;

public class ExceptionalBankingTests {
  /*
   * Testing main. Runs each test and prints which (if any) failed.
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    int fails = 0;
    if(!testAccountBalance()) {
      System.out.println("testAccountBalance failed");
      fails++;
    }
    if(!testOverdraftCount()) {
      System.out.println("testOverdraftCount failed");
      fails++;
    }

    if(!testTransactionGroupEmpty()) {
      System.out.println("testTransactionGroupEmpty failed");
      fails++;
    }

    if(!testTransactionGroupInvalidEncoding()) {
      System.out.println("testTransactionGroupInvalidEncoding failed");
      fails ++;
    }
    if(!testAccountAddNegativeQuickWithdraw()) {
      System.out.println("testAccountAddNegativeQuickWithdraw failed");
      fails++;
    }
    if(!testAccountBadTransactionGroup()) {
      System.out.println("testAccountBadTranactionGroup failed");
      fails++;
    }
    if(!testAccountIndexOutOfBounds()) {
      System.out.println("testAccountIndexOutOfBounds failed");
    }
    if(!testAccountMissingFile()) {
      System.out.println("testAccountMissingFile");
      fails++;
    }
    if(fails==0) {
      System.out.println("All tests passed");
    }
  }
  //method to test if testAccountBalance return the correct account balance number
  public static boolean testAccountBalance() { 
    Account account1 = new Account("account1");
    String[] transactions = new String[] {"1 10 -20 30 -20 -20",
        "0 1 1 1 0 0 1 1 1 1",
        "1 115", 
        "2 3 1 0 1"
    };
    try {
      for(int i = 0; i < transactions.length; i++) {
        account1.addTransactionGroup(transactions[i]); 
      }
    }catch(DataFormatException e) {
    }
    if(account1.getCurrentBalance() == -100) {
      System.out.println("Pass testAccountBalance");
      return true;
    }
    else {
      System.out.println("Failure: did not get -100:" + account1.getCurrentBalance());
    }

    return false;
  }
  //method to test if testOverdraftCount return the correct overdraft count
  public static boolean testOverdraftCount() { 
    Account account = new Account("acount2");
    String[]transactions = new String[] {"1 10 -20 +30 -20 -20",
        "0 1 1 1 0 0 1 1 1 1",
        "1 115", 
        "2 3 1 0 1"
    };
    try {
      for(int i = 0; i < transactions.length; ++i) {
        account.addTransactionGroup(transactions[i]);
      }
    }catch(DataFormatException e) {

    }
    if(account.getNumberOfOverdrafts() == 4) {
      System.out.println("Pass testOverdraftCount");
      return true;
    }
    else {
      System.out.println("Failure: did not get 4:" + account.getNumberOfOverdrafts());
    }
    return false;
  }
  /**
   * This method checks whether the TransactionGroup constructor throws an exception with an
   * appropriate message, when it is passed an empty int[].
   * @return true when test verifies correct functionality, and false otherwise.
   */
  public static boolean testTransactionGroupEmpty() { 
    try {
      int[] em = null ;
      TransactionGroup test1 = new TransactionGroup(em);
    }catch(DataFormatException e){
      return true;
    }
    return false; 
  }
  /**
   * This method checks whether the TransactionGroup constructor throws an exception with an
   * appropriate message, when then first int in the provided array is not 0, 1, or 2.
   * @return true when test verifies correct functionality, and false otherwise.
   */
  public static boolean testTransactionGroupInvalidEncoding() { 

    try {
      String f = "5 3 1 0 1";
      Account fa = new Account("account");
      fa.addTransactionGroup(f);
    }catch(DataFormatException e) {
      return true;
    }

    return false; }

  /**
   * This method checks whether the Account.addTransactionGroup method throws an exception with an
   * appropriate message, when it is passed a quick withdraw encoded group that contains negative
   * numbers of withdraws.
   * @return true when test verifies correct functionality, and false otherwise.
   */
  public static boolean testAccountAddNegativeQuickWithdraw() { 
    try {
      String q = "2 3 -1 0 1";
      Account qw = new Account("account");
      qw.addTransactionGroup(q);
    }catch(DataFormatException e) {
      return true;
    }
    return false; }

  /**
   * This method checks whether the Account.addTransactionGroup method throws an exception with an
   * appropriate message, when it is passed a string with space separated English words (non-int).
   * @return true when test verifies correct functionality, and false otherwise.
   */
  public static boolean testAccountBadTransactionGroup() { 
    try {
      String command = "2 3 1 a 1";
      Account o = new Account("account");
      o.addTransactionGroup(command);
    }catch(DataFormatException e) {
      return true;
    }
    return false; }

  /**
   * This method checks whether the Account.getTransactionAmount method throws an exception with an
   * appropriate message, when it is passed an index that is out of bounds.
   * @return true when test verifies correct functionality, and false otherwise.
   * @throws DataFormatException 
   */
  public static boolean testAccountIndexOutOfBounds()  { 
    try {
      int[]iof = new int[] {1,100,300,400};  
      TransactionGroup group = new TransactionGroup(iof);
      group.getTransactionAmount(3);
    }catch(IndexOutOfBoundsException e) {
      return true;
    }catch(DataFormatException e) {

    }

    return false; }

  /**
   * This method checks whether the Account constructor that takes a File parameter throws an 
   * exception with an appropriate message, when it is passed a File object that does not correspond
   * to an actual file within the file system.
   * @return true when test verifies correct functionality, and false otherwise.
   */
  public static boolean testAccountMissingFile() { 
    try {
      File file = new File("U/acount/test.txt");
      Account nfile = new Account(file);
    }catch(FileNotFoundException e){
      return true;
    }

    return false; }
}

