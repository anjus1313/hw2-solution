# hw1- Manual Review

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## New Functionalities Added
### 1. Undo transaction
The app lets the user undo previously added transactions.
#### User Input:
- User clicks on the row that needs to be deleted.
- If a valid row (row with a transaction) is selected, the Undo button will be enabled.
- User can then click on the Undo button to remove the transaction.

#### Result:
- The selected transaction is removed from the transactions list and updated in table view.


## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTracker
```

You should be able to view the GUI of the project upon successful compilation. 

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.