package com.techelevator;import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.time.LocalDateTime;

public class VendingMachine {
	//declaring attributes for the class
	private double balance;
	private Map<String,VendingMachineItem> machineInventory = new TreeMap<String, VendingMachineItem>();
	private Map<VendingMachineItem, Integer> purchaseHistory = new HashMap<VendingMachineItem, Integer>();
	
	private static DecimalFormat decFormat = new DecimalFormat("##.###");
	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss a");

	
	
	public VendingMachine(String filePath) {	//building ctor for VendingMachine
		restockMachine(filePath);
		balance = 0;
	}
	
	
	public void restockMachine(String filePath) {
		try {	//creating try to to catch potential FileNotFound exception
			File stockFile = new File(filePath);
			//initializing scanner to read through file
			Scanner fileReader = new Scanner(stockFile);
			//looping through file 
			while (fileReader.hasNextLine()) {
				//initailizing nextLine of file to a string
				String fileLine = fileReader.nextLine();
				//placing fileLine into array of strings and splitting elements based on "|"
				String[] item = fileLine.split("\\|");
				//setting itemLocation to first element of the array
				String itemLocation = item[0];
				//for every new line, create new VendingMachineItem
				VendingMachineItem snack = new VendingMachineItem(item[0], item[1], item[2], item[3], 5);
				//sending itemLocation and vendingmachineitem to machineInventory
				machineInventory.put(itemLocation, snack);
				//sending vendingmachineitem and current number sold to purchase history
				purchaseHistory.put(snack, 0);
			}
		}
		catch (FileNotFoundException e) {
			//message to print if appropriate file is not found
			System.out.println("Stock file not found!");
			e.printStackTrace();
		}
	}
	
	public void feedMoney(int moneyIn) { //method for adding money to vendingmachine
		//instantiating variable for balance before money inserted for audit log
		double balanceBefore = this.balance;
		//adding money to balance while making sure input is acceptable
		if (moneyIn == 1 || moneyIn == 2 || moneyIn == 5 || moneyIn == 10) {
			this.balance += moneyIn;
		}
		else {	//message to print if invalid user entry
			System.out.println("Must enter 1, 2, 5, or 10 dollar bill");
		}
		decFormat.setMinimumFractionDigits(2);  //making sure entry in audit log have at least 2 decimal places
				//call for generateAuditEntryforFeed method and provide proper arguments to fill array
		writeAuditLogEntry(generateAuditEntry(df.format(LocalDateTime.now()), "FEED MONEY", decFormat.format(balanceBefore), decFormat.format(this.balance)));
		
	}
	
	public String purchase(String locationOfItemToPurchase) { //purchase method
		//making sure entry in audit log have at least 2 decimal places
		decFormat.setMinimumFractionDigits(2);
		//instantiating variable for balance before money inserted for audit log
		double balanceBefore = this.balance;
		//getting Inventory and setting it to current inventory minus 1
		machineInventory.get(locationOfItemToPurchase).setInventory(machineInventory.get(locationOfItemToPurchase).getInventory() - 1);
		//setting balance to balance minus cost of item purchased
		balance -= machineInventory.get(locationOfItemToPurchase).getItemPrice();
		//call for generateAuditEntryforFeed method and provide proper arguments to fill array
		writeAuditLogEntry(generateAuditEntry(df.format(LocalDateTime.now()), machineInventory.get(locationOfItemToPurchase).getItemName() + " " + locationOfItemToPurchase, decFormat.format(balanceBefore), decFormat.format(this.balance)));
		//calling addItemToPurchaseHistory method with the item purchased to add to purchase history
		addItemToPurchaseHistory(machineInventory.get(locationOfItemToPurchase));
		
		//instantiating string variable to determine what message to print out based on type of item purchased
		String type = machineInventory.get(locationOfItemToPurchase).getItemType();
		if (type.equals("Chip")) {
			return "Crunch Crunch, Yum!";
		}
		if (type.equals("Gum")) {
			return "Chew Chew, Yum!";
		}
		if (type.equals("Drink")) {
			return "Glug Glug, Yum!";
		}
		if (type.equals("Candy")) {
			return "Munch Munch, Yum!";
		}
		
		return "";	
	}
	//method to write to Audit Log
	private void writeAuditLogEntry(String[] actions) {
		try {  //building try block to catch IO exception
		File auditLog = new File("Log.txt");	//instantiating variable to hold new file
		String auditEntry = "";		//String to hold what will be put into audit log
		for (int i = 0; i < actions.length; i++) { //for loop to iterate through action array
			auditEntry += actions[i] + " ";	//turning those actions into single string to print in audit log
		}
		//creating a writer to append to the end of a file as opposed to overwriting same line
		BufferedWriter writer = new BufferedWriter(new FileWriter(auditLog, true));
		writer.newLine();			//create a new line
		writer.append(auditEntry);	//add auditEntry to new line
		writer.close();				//close writer 
	}
		catch (IOException e) {		//catch in case unable to write to file
			e.printStackTrace();	//or file not found
		}
	}

		//method for generating audit entry
	private String[] generateAuditEntry(String dateTime, String actionTaken, String balanceBefore, String balanceAfter) {
		//declaring variable for auditEntry string array
		String[] auditEntry = new String[4];
		//setting elements of array equal to parameters of the method
		auditEntry[0] = dateTime;
		auditEntry[1] = actionTaken;
		auditEntry[2] = "$" + balanceBefore;
		auditEntry[3] = "$" + balanceAfter;
		return auditEntry;
	}
	
			//method for generating the sales report
	public void generateSalesReport(Map<VendingMachineItem, Integer> purchaseHistory) {
		String salesReportPath = "SalesReport.txt";	//declaring string to hold SalesReport file
		File reportFile = new File(salesReportPath);	//creating file at salesReportPath
		//instantiating printwriter to write new file
		//creating try block to catch potential IO exception
		try (PrintWriter writer = new PrintWriter(reportFile)) { 
			//declaring variable to hold amount sold
			double amountSold = 0;
			//create for/each loop to iterate through purchase history and add each item to the sales report
			for (Map.Entry<VendingMachineItem, Integer> entry : purchaseHistory.entrySet()) {													
				writer.println(entry.getKey().getItemName() + "|" + entry.getValue());
				//adding amount sold to sales report
				amountSold += (entry.getKey().getItemPrice()) * entry.getValue();
			}
			writer.println();	//create a blank line
			writer.printf("Total Sales: $%.2f", amountSold);	//formatting total sales
			
		}
		catch (IOException i) {
			System.out.println("Sales Report Not Found!");	//catch any io/file not found exception
			i.printStackTrace();
		}
	}
		//method for giving change
	public String giveChange() {
		//creating variable equal to balance
		double balanceBefore = this.balance;
		//rounding to avoid repeating decimal and multiplying to use only ints 
		this.balance = Math.round(this.balance * 100);
		int quarters = 0;	//instantiating variables for each change denomination
		int dimes = 0;
		int nickles = 0;
		while (balance > 5) {		//while loop to iterate through balance to determin
			if (balance >= 25) {	//least amount of coins to return
				balance -= 25;
				quarters++;
			}
			if(balance >= 10 && balance < 25) {
				balance -= 10;
				dimes++;
			}
			if(balance >= 5 && balance < 10) {
				balance -= 5;
				nickles++;
			}
			
		}		//properly formatting finish transaction message of change given and resetting balance to zero
		writeAuditLogEntry(generateAuditEntry(df.format(LocalDateTime.now()), "GIVE CHANGE", decFormat.format(balanceBefore), decFormat.format(this.balance)));
		return "Your change is " + quarters + " quarters, " + dimes + " dimes, and " + nickles + " nickles.";
	}
	
	private void addItemToPurchaseHistory(VendingMachineItem item) {
		//replacing current purchasehistory inventory by adding 1 to purchase history item
		purchaseHistory.replace(item, purchaseHistory.get(item) + 1);
	}
	
	public double getBalance() {
		return balance;
	}


	public Map<String, VendingMachineItem> getMachineInventory() {
		return machineInventory;
	}


	public Map<VendingMachineItem, Integer> getPurchaseHistory() {
		return purchaseHistory;
	}


	private String getItemNameForCtor(String itemName) {
		return itemName;
	}

}
