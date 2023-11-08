import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.ExpenseTrackerView;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSuiteUndo {
    private ExpenseTrackerModel model;
    private ExpenseTrackerView view;
    private ExpenseTrackerController controller;

    @Before
    public void setup() {
        model = new ExpenseTrackerModel();
        view = new ExpenseTrackerView();
        controller = new ExpenseTrackerController(model, view);
    }

    public double getTotalCost() {
        double totalCost = 0.0;
        int rowCount = view.getTableModel().getRowCount();
        int columnCount = view.getTableModel().getColumnCount();
        if (rowCount!=0){
            totalCost = (double) view.getTableModel().getValueAt(rowCount-1,columnCount-1);
        }

        return totalCost;
    }

    @After
    public void tearDown() {
        model = null;
        view = null;
        controller = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUndoDisallowed1() {
        // Check List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        //Check undo functionality with invalid rows
        controller.undoTransaction(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUndoDisallowed2() {
        // Check List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        //Check undo functionality with invalid rows
        controller.undoTransaction(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUndoDisallowed3() {
        // Check List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        //Check undo functionality with invalid rows
        controller.undoTransaction(1);
    }

    @Test
    public void testUndoAllowed() {
        // Check List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        // Pre-condition: Add a transaction
        double amount = 50.0;
        String category = "food";
        assertTrue(controller.addTransaction(amount, category));

        assertEquals(2, view.getTableModel().getRowCount(),0.01);
        assertEquals(amount, getTotalCost(),0.01);

        //Perform the action: Undo the transaction
        controller.undoTransaction(0);

        // Post-condition: Check if transaction is removed
        assertEquals(0, view.getTableModel().getRowCount(),0.01);
        assertEquals(0, getTotalCost(),0.01);

    }

}
