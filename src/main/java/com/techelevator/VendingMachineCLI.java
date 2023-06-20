package com.techelevator;

import com.techelevator.view.Menu;
import com.techelevator.view.SalesLog;
import com.techelevator.view.VendingMachine;

import java.math.BigDecimal;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit"; // Inserted missing option
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT}; // created the string array to work with menu
	private static final String MAIN_MENU_OPTION_SALES_REPORT = "Sales Report";

	private static final String FEED_MONEY = "Feed Money";
	private static final String SELECT_PRODUCT = "Select Product";
	private static final String FINISH_TRANSACTION="Finish Transaction"; //changed to proper finish transaction
	private static final String[] HIDDEN_MENU_OPTIONS = {MAIN_MENU_OPTION_SALES_REPORT};
	private static final String[] PURCHASE_MENU_OPTIONS={FEED_MONEY,SELECT_PRODUCT,FINISH_TRANSACTION};
	private static final String PATH_TO_INVENTORY_FILE = "./vendingmachine.csv";

	private VendingMachine vendingMachine;
	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		this.vendingMachine = new VendingMachine(PATH_TO_INVENTORY_FILE, System.out);
	}

	public void run() {

		while (true) {

			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS, HIDDEN_MENU_OPTIONS);


			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				System.out.println(vendingMachine.displayVendingItems());
				/////
				// display vending machine items this works
				/////



			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				/*
					Selecting "(1) Feed Money" allows the customer to repeatedly feed money into the machine in whole dollar amounts.
					The "Current Money Provided" indicates how much money the customer has fed into the machine.
				 */

				while (true) {
					choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS, vendingMachine.displayCurrentBalance());
					System.out.println();
					//String purchaseMenuChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);  //New: shows purchase menu options
					if (choice.equals(FEED_MONEY)) {
						System.out.println("Please insert cash into the machine: ");
						String moneyInserted = menu.getIn().nextLine();
						vendingMachine.feedMoney(moneyInserted);
					} else if (choice.equals(SELECT_PRODUCT)) {
						System.out.println(vendingMachine.displayVendingItems());
						System.out.println("Please make your selection: ");
						String input = menu.getIn().nextLine();
						vendingMachine.selectProduct(input);
					} else if (choice.equals(FINISH_TRANSACTION)) {
						vendingMachine.finishTransaction(vendingMachine.getCurrentBalance());
						vendingMachine.setCurrentBalance(BigDecimal.ZERO);
						break;
					}
				}
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {

				break;
			} else if (choice.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
				SalesLog.generateReport(vendingMachine.getAllVendingItem(), vendingMachine.getTotalSales());
			}
		}
	}
	public static void main (String[]args){
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
