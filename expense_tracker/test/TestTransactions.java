// package test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.ExpenseTrackerView;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.*;


public class TestTransactions {

    private ExpenseTrackerModel model;
    private ExpenseTrackerView view;
    private ExpenseTrackerController controller;

    @Before
    public void setup() {
        model = new ExpenseTrackerModel();
        view = new ExpenseTrackerView();
        controller = new ExpenseTrackerController(model, view);
        //ExpenseTrackerApp.main(null);
    }

    @After
    public void cleanup() {
        model = null;
        view = null;
        controller = null;
    }

    public void checkViewTransaction(double amount, String category, double viewAmount, String viewCategory, String viewTimeStamp) {
        assertEquals(amount, viewAmount, 0.01);
        assertEquals(category, viewCategory);
        Date transactionDate = null;
        try {
            transactionDate = Transaction.dateFormatter.parse(viewTimeStamp);
        }
        catch (ParseException pe) {
            pe.printStackTrace();
            transactionDate = null;
        }
        Date nowDate = new Date();
        assertNotNull(transactionDate);
        assertNotNull(nowDate);
        // They may differ by 60 ms
        assertTrue(nowDate.getTime() - transactionDate.getTime() < 60000);
    }

    @Test
    public void testAddTransaction() {

        // Pre-condition: view model is empty
        assertEquals(0, view.getTableModel().getRowCount());

        // Perform the action: Add a transaction
        double amount = 50.0;
        String category = "food";
        assertTrue(controller.addTransaction(amount, category));

        // Post-condition: view model and table contains 2 rows ( 1 transaction and 1 total)
        assertEquals(2, view.getTableModel().getRowCount());

        // Check the contents of the view model
        double viewAmount = (double) view.getTableModel().getValueAt(0,1);
        String viewCategory = (String) view.getTableModel().getValueAt(0,2);
        String viewTimeStamp = (String) view.getTableModel().getValueAt(0,3);
        checkViewTransaction(amount, category, viewAmount, viewCategory, viewTimeStamp);

        // Check the total amount
        int TotalRow = view.getTableModel().getRowCount() - 1;
        int TotalColumn = view.getTableModel().getColumnCount() - 1;
        assertEquals(amount, (Double) view.getTableModel().getValueAt(TotalRow,TotalColumn), 0.01);
    }

    @Test
    public void testInvalidTransaction() {

        // Pre-condition: view model has 2 rows (1 transaction and a Total row)
        double initialAmount = 50.0;
        String initialCategory = "food";
        assertTrue(controller.addTransaction(initialAmount, initialCategory));
        assertEquals(2, view.getTableModel().getRowCount());

        // Perform the action: Add an invalid transaction
        double invalidAmount = -50.0;
        String invalidCategory = "food";
        assertFalse(controller.addTransaction(invalidAmount, invalidCategory));

        // Post-condition: view model and table still contains only 2 rows ( 1 transaction and 1 total)
        assertEquals(2, view.getTableModel().getRowCount());

        // Check the total amount remains same
        int TotalRow = view.getTableModel().getRowCount() - 1;
        int TotalColumn = view.getTableModel().getColumnCount() - 1;
        assertEquals(initialAmount, (Double) view.getTableModel().getValueAt(TotalRow,TotalColumn), 0.01);
    }

}
