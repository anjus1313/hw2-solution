import javax.swing.JOptionPane;
import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;
import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ExpenseTrackerApp {

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);
    

    // Initialize view
    view.setVisible(true);



    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      
      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);
      
      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });

      // Add action listener to the "Apply Category Filter" button
    view.addApplyCategoryFilterListener(e -> {
      try{
      String categoryFilterInput = view.getCategoryFilterInput();
      CategoryFilter categoryFilter = new CategoryFilter(categoryFilterInput);
      if (categoryFilterInput != null) {
          // controller.applyCategoryFilter(categoryFilterInput);
          controller.setFilter(categoryFilter);
          controller.applyFilter();
      }
     }catch(IllegalArgumentException exception) {
    JOptionPane.showMessageDialog(view, exception.getMessage());
    view.toFront();
   }});


    // Add action listener to the "Apply Amount Filter" button
    view.addApplyAmountFilterListener(e -> {
      try{
      double amountFilterInput = view.getAmountFilterInput();
      AmountFilter amountFilter = new AmountFilter(amountFilterInput);
      if (amountFilterInput != 0.0) {
          controller.setFilter(amountFilter);
          controller.applyFilter();
      }
    }catch(IllegalArgumentException exception) {
    JOptionPane.showMessageDialog(view,exception.getMessage());
    view.toFront();
   }});

      // Add action listener to the Jframe
      view.getTransactionsTable().addMouseListener(new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
              // Get row selected by user from view
              int selectedRow = view.getTransactionsTable().getSelectedRow();
              //JOptionPane.showMessageDialog(view, String.valueOf(selectedRow));
              //Check if valid row is selected
              if(selectedRow != -1 && selectedRow < model.getTransactions().size()){
                  view.enableUndoBtn();
              }
              else {
                  view.disableUndoBtn();
              }
          }
          @Override
          public void mousePressed(MouseEvent e) {}
          @Override
          public void mouseReleased(MouseEvent e) {}
          @Override
          public void mouseEntered(MouseEvent e) {}
          @Override
          public void mouseExited(MouseEvent e) {}
      });

      // Handles remove transaction button clicks
      view.getUndoTransactionBtn().addActionListener(e -> {

          // Get row selected by user from view
          int selectedRow = view.getTransactionsTable().getSelectedRow();

          // Call controller to remove transaction
          controller.undoTransaction(selectedRow);

          // Call view to disable button
          view.disableUndoBtn();

      });

  }
}
