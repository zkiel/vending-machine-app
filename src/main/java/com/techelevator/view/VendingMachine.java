package com.techelevator.view;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;



public class VendingMachine {
    private Map<String, Product> vending;
    private BigDecimal currentBalance;
    private BigDecimal totalSales;
    private PrintWriter out;
    private ErrorLog errorLog;
    private TransactionLog purchaseLog;


    public VendingMachine(String datapath, OutputStream output){
        vending = new TreeMap<String, Product>();
        currentBalance = new BigDecimal(0);
        totalSales = new BigDecimal(0);
        stockVendingmachine(datapath);
        this.out = new PrintWriter(output);
    }

    public Product getProduct(String id) {
        return vending.get(id);
    }
    public Map<String, Product> getAllVendingItem() {
        return vending;
    }
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void increaseBalance(BigDecimal dollarInput) {
        this.currentBalance = currentBalance.add(dollarInput);
    }
    public void decreaseBalance(BigDecimal dollarInput) {
        this.currentBalance = currentBalance.subtract(dollarInput);
    }
    public void stockVendingItem(Product item) {
        vending.put(item.getCode(), item);
    }


    public String displayVendingItems(){
        String display = System.lineSeparator();
        for(Map.Entry<String, Product> itemEntry: getAllVendingItem().entrySet()){
            Product item = itemEntry.getValue();
            display += item.getCode() + " " + item.getName() + " " + formatMoney(item.getPrice()) + " " + "Stock: " + (item.getCurrentStock() > 0 ? item.getCurrentStock() : "Sold out") + System.lineSeparator();
        }
        return display;
    }
    public void feedMoney(String moneyIn ) {
        try {
            BigDecimal input = new BigDecimal(moneyIn);

            purchaseLog.logTransaction(" FEED MONEY: ",input,getCurrentBalance());
            if(input.scale()!=0){
                System.out.println("Error: entered value is not a whole number");
            }else {
                this.increaseBalance(input);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println();
            errorLog.logError(e.getMessage());
        }
    }
    public void selectProduct(String input) {
        BigDecimal startingBalance = getCurrentBalance();
        Product itemSelected = getProduct(input.toUpperCase());
        try {
            if (itemSelected == null) {
                throw new UserInputException(input + " is not a valid selection.");
            }
            if (itemSelected.getCurrentStock() == 0) {
                throw new UserInputException("Item " + itemSelected.getCode() + " is sold out.");
            }
            if (startingBalance.compareTo(itemSelected.getPrice()) >= 0) {
                decreaseBalance(itemSelected.getPrice());
                itemSelected.setQuantitySold(itemSelected.getQuantitySold() + 1);
                totalSales = totalSales.add(itemSelected.getPrice());
                purchaseLog.logTransaction(itemSelected.getName(),itemSelected.getCode(), itemSelected.getPrice(), getCurrentBalance());
                System.out.println(itemSelected.getName()+" item Cost $"+itemSelected.getPrice()+" balance after transaction $"+ getCurrentBalance());
                String vendSound = itemSelected.vend();
                System.out.println();

                out.println(vendSound);
                out.flush();
            } else {
                throw new InsufficientBalanceException("Your balance of " + formatMoney(getCurrentBalance()) + " is insufficient for purchase of " + formatMoney(itemSelected.getPrice()) + ".");
            }
        } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
            errorLog.logError(e.getMessage());
        }
    }
        public static String formatMoney(BigDecimal money) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            return formatter.format(money);
        }
        public String displayCurrentBalance() {
            return "Current money provided: " + formatMoney(getCurrentBalance());
        }
        public void finishTransaction(BigDecimal money) {

            BigDecimal startingBalance = getCurrentBalance();
            int quarters = money.divideToIntegralValue(new BigDecimal("0.25")).intValue();
            BigDecimal quartersValue = new BigDecimal(quarters).multiply(new BigDecimal("0.25"));

            int dimes = money.subtract(quartersValue).divideToIntegralValue(new BigDecimal("0.10")).intValue();
            BigDecimal dimesValue = new BigDecimal(dimes).multiply(new BigDecimal("0.10"));

            int nickels = money.subtract(quartersValue).subtract(dimesValue).divideToIntegralValue(new BigDecimal("0.05")).intValue();


            System.out.println("Here is your change");
            System.out.println("Quarters: " + quarters);
            System.out.println("Dimes: " + dimes);
            System.out.println("Nickels: " + nickels);

            if (!(startingBalance.equals(new BigDecimal(0)))){
                String message=" Give Change: ";
                purchaseLog.logTransaction(message,getCurrentBalance(),BigDecimal.ZERO);
            }
    }

    private void stockVendingmachine(String dataPath){
        try(Scanner fileScanner = new Scanner(new File(dataPath))) {
            while (fileScanner.hasNextLine()){
                Product currentItem;
                String currentLine = fileScanner.nextLine();
                String[] lineInfo = currentLine.split("\\|");
                String lineItemID = lineInfo[0];
                String lineItemName = lineInfo[1];
                BigDecimal lineItemPrice = new BigDecimal(lineInfo[2]);
                currentItem = new Product(lineItemID, lineItemName, lineItemPrice);
                stockVendingItem(currentItem);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Machine data file not found.");
            errorLog.logError("File not found!");
        }
    }
}
