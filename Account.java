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

import java.util.Scanner;
import java.util.zip.DataFormatException;
import java.io.File;
import java.io.FileNotFoundException;


public class Account {
  private static final int MAX_GROUPS = 10000; //max group can be contain
  private static int nextUniqueId = 1000; //max value

  private String name; //name
  private final int UNIQUE_ID; //ID
  private TransactionGroup[] transactionGroups; //the array of transactiongroup
  private int transactionGroupsCount; //count the number of transactionGroup

  //create an Account constructor
  public Account(String name) {
    this.name = name;
    this.UNIQUE_ID = Account.nextUniqueId;
    Account.nextUniqueId++;
    this.transactionGroups = new TransactionGroup[MAX_GROUPS];
    this.transactionGroupsCount = 0;
  }
  //create an Account constructor with throw the problem FileNotFoundException
  public Account(File file) throws FileNotFoundException {
    if (!file.exists()) {
      throw new FileNotFoundException(file + "was not found");
    }
    // NOTE: THIS METHOD SHOULD NOT BE CALLED MORE THAN ONCE, BECAUSE THE
    // RESULTING BEHAVIOR IS NOT DEFINED WITHIN THE JAVA SPECIFICATION ...
    Scanner in = new Scanner(file);
    // ... THIS WILL BE UPDATED TO READ FROM A FILE INSTEAD OF SYSTEM.IN.

    this.name = in.nextLine();
    this.UNIQUE_ID = Integer.parseInt(in.nextLine());
    Account.nextUniqueId = this.UNIQUE_ID + 1;
    this.transactionGroups = new TransactionGroup[MAX_GROUPS];//array of transactionGroups
    this.transactionGroupsCount = 0;
    String nextLine = "";
    while (in.hasNextLine())
      try {
        this.addTransactionGroup(in.nextLine());
      } catch (DataFormatException e) {
        System.out.println("DataFormatException occured" + e.getMessage());
        e.printStackTrace();// to finish reading the rest of the transaction groups from that file as usual
      }
    in.close();
  }
  //create an getId constructor
  public int getId() {
    return this.UNIQUE_ID;
  }
  /**
   * Define a new method called addTransactionGroup that takes such a string as command/input, and
   * correctly adds a new transaction group to the provided set of transactions groups. When the
   * first character within a command does not correspond to a valid transaction encoding or the
   * allTransactions array starts out full, this method should throw DataFormatException problem
   * 
   */
  public void addTransactionGroup(String command) throws DataFormatException {
    if (!command.contains(" ")) {
      throw new DataFormatException(
          "addTransactionGroup requires string commands that contain only space separated integer values");
    }
    String[] parts = command.split(" ");
    for(int i = 0; i < parts.length; ++i) {
      try{Integer.parseInt(parts[i]);}
      catch(NumberFormatException e){
        throw new DataFormatException(
            "addTransactionGroup requires string commands that contain only space separated integer values");
      }
    }
    int[] newTransactions = new int[parts.length];
    for (int i = 0; i < parts.length; i++)
      newTransactions[i] = Integer.parseInt(parts[i]);
    TransactionGroup t = new TransactionGroup(newTransactions);
    this.transactionGroups[this.transactionGroupsCount] = t;
    if (this.transactionGroups.length > MAX_GROUPS) {
      throw new OutOfMemoryError();
    }
    this.transactionGroupsCount++;
  }
  //create a getTransactionCount constructor to get the transactionCount
  public int getTransactionCount() {
    int transactionCount = 0;
    for (int i = 0; i < this.transactionGroupsCount; i++)
      transactionCount += this.transactionGroups[i].getTransactionCount();
    return transactionCount;
  }

  // a method to calculate and return the total account balance based on a
  // collection of provided transaction groups
  public int getTransactionAmount(int index) throws  IndexOutOfBoundsException{
    if(index >= this.transactionGroups.length) {
      throw new IndexOutOfBoundsException(index + "does not fall within the range of" + transactionGroups.length);
    }
    int transactionCount = 0;
    for (int i = 0; i < this.transactionGroupsCount; i++) {
      int prevTransactionCount = transactionCount;
      transactionCount += this.transactionGroups[i].getTransactionCount();
      if (transactionCount > index) {
        index -= prevTransactionCount;
        return this.transactionGroups[i].getTransactionAmount(index);
      }
    }
    return -1;
  }
  //create a getCurrentBalance constructor to show the current balance
  public int getCurrentBalance() {
    int balance = 0;
    int size = this.getTransactionCount();
    for (int i = 0; i < size; i++)
      balance += this.getTransactionAmount(i);
    return balance;
  }
  /**
   * perform on collections of transaction groups is to count the number of overdrafts. An overdraft
   * is when a withdraw is made that results in a negative balance.
   */
  public int getNumberOfOverdrafts() {
    int balance = 0;
    int overdraftCount = 0;
    int size = this.getTransactionCount();
    for (int i = 0; i < size; i++) {
      int amount = this.getTransactionAmount(i);
      balance += amount;
      if (balance < 0 && amount < 0)//when achieve negative balance , count once
        overdraftCount++;
    }
    return overdraftCount;
  }

}

