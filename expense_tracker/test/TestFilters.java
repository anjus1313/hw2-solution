// package test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.ExpenseTrackerView;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class TestFilters {

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

    public Color getColour(int row){
        JTable table = view.getTransactionsTable();
        Color color = table.prepareRenderer(table.getCellRenderer(row, 0), row, 0).getBackground();
        return color;
    }

    @Test
    public void testFilterAmount() {

        // Pre-condition: view model is empty
        assertEquals(0, view.getTableModel().getRowCount());

        // Perform the action: Add a set of transactions and filter based on amount
        List<Double> amountList= Arrays.asList(10.0, 10.0, 20.0, 30.0);
        for (Double amount : amountList) {
            String category = "food";
            assertTrue(controller.addTransaction(amount, category));
        }
        double amountFilterInput = 10.0;
        AmountFilter amountFilter = new AmountFilter(amountFilterInput);
        controller.setFilter(amountFilter);
        controller.applyFilter();

        // Post-condition: view model and table contains 5 rows ( 5 transactions and 1 total)
        assertEquals(5, view.getTableModel().getRowCount());

        Color highlightColor = new Color(173, 255, 168);

        // check if row1 and row2 got highlighted
        assertEquals(highlightColor, getColour(0));
        assertEquals(highlightColor, getColour(1));

        // check if row3 and row4 did not get highlighted
        assertNotEquals(highlightColor, getColour(3));
        assertNotEquals(highlightColor, getColour(4));
    }

    @Test
    public void testFilterCategory() {

        // Pre-condition: view model is empty
        assertEquals(0, view.getTableModel().getRowCount());

        // Perform the action: Add a set of transactions and filter based on amount
        List<String> categoryList= Arrays.asList("food","bills","food","other");
        for (String category : categoryList) {
            double amount = 50.0;
            assertTrue(controller.addTransaction(amount, category));
        }
        String categoryFilterInput = "food";
        CategoryFilter categoryFilter = new CategoryFilter(categoryFilterInput);
        controller.setFilter(categoryFilter);
        controller.applyFilter();

        // Post-condition: view model and table contains 5 rows ( 5 transactions and 1 total)
        assertEquals(5, view.getTableModel().getRowCount());

        Color highlightColor = new Color(173, 255, 168);

        // check if row1 and row3 got highlighted
        assertEquals(highlightColor, getColour(0));
        assertEquals(highlightColor, getColour(2));

        // check if row1 and row4 did not get highlighted
        assertNotEquals(highlightColor, getColour(1));
        assertNotEquals(highlightColor, getColour(4));
    }

    @After
    public void cleanup() {
        model = null;
        view = null;
        controller = null;
    }
}
