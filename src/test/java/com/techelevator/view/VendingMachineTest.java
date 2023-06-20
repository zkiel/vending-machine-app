package com.techelevator.view;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineTest  {


        private VendingMachine vendingMachine;
        private final String TEST_VENDING_MACHINE_PATH = "./vendingmachine.csv";
        private ByteArrayOutputStream output;
        private Product item;

        @Before
        public void setup() {

            output = new ByteArrayOutputStream();
            vendingMachine = new VendingMachine(TEST_VENDING_MACHINE_PATH, output);
//            vendingMachine.setCurrentBalance(BigDecimal.ZERO);
            item=new Product("C4","Heavy",BigDecimal.valueOf(1.50));

        }

        @Test
        public void check_balance_is_zero_after_tranascton() {
            BigDecimal expected = BigDecimal.valueOf(0.0);
            vendingMachine.setCurrentBalance(BigDecimal.valueOf(3));

//            vendingMachine.selectProduct("C4");
            vendingMachine.decreaseBalance(item.getPrice());
            vendingMachine.decreaseBalance((item.getPrice()));
            ///possible issue
//            vendingMachine.finishTransaction(vendingMachine.getCurrentBalance());
            //            vendingMachine.finishTransaction();
//            BigDecimal actual = vendingMachine.getTotalSales();
            Assert.assertEquals(expected, vendingMachine.getCurrentBalance());
            System.out.println("Expected: $"+expected);
            System.out.println("Actual: $"+vendingMachine.getCurrentBalance());
        }

        @Test
        public void add_to_balance_after_purchase_calculation() {
            BigDecimal expected = new BigDecimal("8.50");
            vendingMachine.setCurrentBalance(new BigDecimal("5"));
            vendingMachine.decreaseBalance(item.getPrice());
            vendingMachine.increaseBalance(new BigDecimal("5"));
            BigDecimal actual = vendingMachine.getCurrentBalance();

            Assert.assertEquals("8.50", vendingMachine.displayCurrentBalance().substring(25));
            System.out.println("Expected is : 8.50");
            System.out.println("Actual is: "+vendingMachine.displayCurrentBalance().substring(25));
        }

        @Test
        public void balance_after_purchase() {
            BigDecimal expected = new BigDecimal("3.50");
            vendingMachine.setCurrentBalance(new BigDecimal("5"));
            vendingMachine.decreaseBalance((item.getPrice()));
            String  actual = vendingMachine.displayCurrentBalance().substring(25);
            Assert.assertEquals("3.50", actual);
            System.out.println("Expected is : 3.50");
            System.out.println("Actual is: "+vendingMachine.displayCurrentBalance().substring(25));
        }

        @Test
        public void item_selected() {
            String expected = "";
            File testFile = new File(TEST_VENDING_MACHINE_PATH);
            try (Scanner scanner = new Scanner(testFile)) {
                while (scanner.hasNextLine()){
                    String lineFromFile = scanner.nextLine();
                    if (lineFromFile.startsWith("C4")) {
                        String [] arrayFromLine = lineFromFile.split("\\|");
                        expected = arrayFromLine[1];
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
            String actual = vendingMachine.getProduct("C4").getName();
            Assert.assertEquals(expected, actual);
            System.out.println("Expected: "+expected);
            System.out.println("Actual: "+actual);
        }

        @Test
        public void is_in_stock() {
            Boolean actual = true;
            Boolean expected = true;
            for (Map.Entry<String, Product> itemEntry :vendingMachine.getAllVendingItem().entrySet()) {
                if(itemEntry.getValue().getCurrentStock() != 5) {
                    actual = false;
                }
            }
            Assert.assertEquals(expected, actual);
            System.out.println("Expected "+expected);
            System.out.println("Actual"+actual);
        }

        @Test
        public void has_correct_change() {
//            vendingMachine.setCurrentBalance(new BigDecimal("8.75"));
            vendingMachine.feedMoney("8");
            vendingMachine.decreaseBalance(item.getPrice());


            String expected = "Here is your change"  +System.lineSeparator() + "Quarters: 26" + System.lineSeparator()
                    + "Dimes: 0" + System.lineSeparator() + "Nickles: 0" + System.lineSeparator();

            System.out.println(expected);
//I use the finishTransaction method below what the expected format should be there is no assert only a visual check
            vendingMachine.finishTransaction(vendingMachine.getCurrentBalance());

        }

        @Test
        public void display_sold_out() {
            vendingMachine.setCurrentBalance(new BigDecimal("10"));

            while(vendingMachine.getProduct("C4").getCurrentStock() > 0) {
                vendingMachine.selectProduct("C4");
            }
            String itemDisplay = vendingMachine.displayVendingItems();
            String outputList = itemDisplay.substring(itemDisplay.lastIndexOf("C4"));
            int indexOfSoldOut = outputList.indexOf("Stock: Sold out");
            boolean actual = (indexOfSoldOut < outputList.indexOf("D1"));
            boolean expected = true;
            Assert.assertEquals(expected, actual);
        }
    }
