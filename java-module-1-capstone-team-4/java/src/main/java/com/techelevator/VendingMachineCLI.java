package com.techelevator;
import java.nio.channels.SelectableChannel;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;

/**************************************************************************************************************************
*  This is your Vending Machine Command Line Interface (CLI) class
*
*  It is the main process for the Vending Machine
*
*  THIS is where most, if not all, of your Vending Machine interactions should be coded
*  
*  It is instantiated and invoked from the VendingMachineApp (main() application)
*  
*  Your code should be placed in here
***************************************************************************************************************************/
import com.techelevator.view.Menu;         // Gain access to Menu class provided for the Capstone

public class VendingMachineCLI {

    // Main menu options defined as constants

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE      = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT          = "Exit";
	private static final String MAIN_MENU_OPTION_GENERATE_SALES_REPORT = "Generate Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
													    MAIN_MENU_OPTION_PURCHASE,
													    MAIN_MENU_OPTION_EXIT,
													    MAIN_MENU_OPTION_GENERATE_SALES_REPORT
													    };
	//Creating purchase menu defined as constants
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};



	
	private Menu vendingMenu;              // Menu object to be used by an instance of this class
	
	public VendingMachineCLI(Menu menu) {  // Constructor - user will pas a menu for this class to use
		this.vendingMenu = menu;           // Make the Menu the user object passed, our Menu
	}
	
	VendingMachine vendingMachine = new VendingMachine("vendingmachine.csv");	//creating new vending machine with stock file
	/**************************************************************************************************************************
	*  VendingMachineCLI main processing loop
	*  
	*  Display the main menu and process option chosen
	*
	*  It is invoked from the VendingMachineApp program
	*
	*  THIS is where most, if not all, of your Vending Machine objects and interactions 
	*  should be coded
	*
	*  Methods should be defined following run() method and invoked from it
	*
	***************************************************************************************************************************/
	public void run() {
		
		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			String choice = (String)vendingMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);  // Display menu and get choice
			
			switch(choice) {                  // Process based on user menu choice
			
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					displayItems(vendingMachine);           // invoke method to display items in Vending Machine
					break;                    // Exit switch statement
			
				case MAIN_MENU_OPTION_PURCHASE:
					
					boolean inPurchaseMenu = true;			// creating boolean to stay in purchase menu until transaction finished
					while (inPurchaseMenu) {
					System.out.printf("Current balance: $%.2f", vendingMachine.getBalance());
					choice = (String)vendingMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
					
					switch(choice) {
					
						case PURCHASE_MENU_OPTION_FEED_MONEY:
							boolean insertingMoney = true;	//as long as insertingMoney, continue prompting for amount to insert
							while (insertingMoney) {
								Scanner billInserted = new Scanner(System.in);	//initialize scanner for user input
								System.out.println("Machine Accepts $1, $2, $5, and $10 bills");
								System.out.print("What bill do you want to insert? ");
								String strippedInput = billInserted.nextLine().replaceAll("[^\\d]", "");
								if (strippedInput.length() > 0) {	/*stripping all non-digit characters to make sure 
																											there is a number from the user */
									//user input has digit and we are making the digit the bill inserted
									int bill = Integer.parseInt(strippedInput);
									//making sure bill is equal to acceptable values
									if (bill == 1 || bill == 2 || bill == 5 || bill == 10) {
										//we have a valid integer equal to acceptable bills
										//and adding it to vendingMachine through feedMoney method
										vendingMachine.feedMoney(bill);
									}
								}
								else {	//message to print in case of invalid user entry
									System.out.println("Invalid bill type - insert a $1, $2, $5, or, $10 bill!");
								}		//prompting user to insert more money if desired
								System.out.print("Would you like to insert more money? (Y/N)");
									//instantiating string to accept users Y/N input
								boolean prompting = true;
								while (prompting) {
								String keepInserting = billInserted.nextLine();
									//when "N" inserted, set boolean to false to exit feedMoney function
								if (keepInserting.equalsIgnoreCase("N")) {
									insertingMoney = false;
									prompting = false;
								}
								if (keepInserting.equalsIgnoreCase("Y")) {
									prompting = false;
								}
								else {
									System.out.println("Please enter Y or N");
								}
								}
							}
							break;
						
						case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
							//calling displayItems method using file within vendingMachine
							displayItems(vendingMachine);
							System.out.println("Please enter item code.");
							//setting up scanner for user input
							Scanner userInput = new Scanner(System.in);
							//making sure user entry is uppercase to compare to item location
							String itemCode = userInput.nextLine().toUpperCase();
							//checking if userInput is valid itemCode
							if (vendingMachine.getMachineInventory().containsKey(itemCode)) {
								//making sure items inventory is available
								if (vendingMachine.getMachineInventory().get(itemCode).getInventory() > 0) {
									//comparing current balance to itemPrice to make sure suffecient funds exist
									if (vendingMachine.getBalance() >= vendingMachine.getMachineInventory().get(itemCode).getItemPrice()) {
										//call purchase item method with itemCode which returns string based on type of item
										System.out.println(vendingMachine.purchase(itemCode));
									}
									else { //print if insuffiecient funds
										System.out.println("Insufficient Funds!");
									}
								}
								else { //print if item inventory = 0
									System.out.println("Sold Out!");
								}
								}
							else {	//print if itemCode is invalid
								System.out.println("Invalid Item Code!");
							}
							break;
						case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
							//call giveChange function which sets balance to 0 and sets amount of change user receives
							System.out.println(vendingMachine.giveChange());
							System.out.println(vendingMachine.getBalance());
							inPurchaseMenu = false;
							
							break;
						
						}
					}
					break;                    // Exit switch statement
				case MAIN_MENU_OPTION_EXIT:
					endMethodProcessing();    // Invoke method to perform end of method processing
					vendingMachine.generateSalesReport(vendingMachine.getPurchaseHistory());
					shouldProcess = false;    // Set variable to end loop
					break;						// Exit switch statement
				
				case MAIN_MENU_OPTION_GENERATE_SALES_REPORT:
					//calling generateSalesReport method and passing it to PurchaseHistory
					vendingMachine.generateSalesReport(vendingMachine.getPurchaseHistory());		
					break;
			}	
		}
		System.out.println("Thank you for usingn Ricky & Tom's vending services. Have a nice day!");
		return;                               // End method and return to caller
	}
/********************************************************************************************************
 * Methods used to perform processing
 ********************************************************************************************************/
	public static void displayItems(VendingMachine machine) { // static attribute used as method is not associated with specific object instanc
		DecimalFormat df = new DecimalFormat("###.##"); //setting decimal format for display items
		df.setMinimumFractionDigits(2); //ensures prices ending in zero have correct pricing format
		//setting loop to iterate through every location within the machineInventory and printing for item display
		for (String location : machine.getMachineInventory().keySet()) {
			//printing out in a readable format for the user
			System.out.println(machine.getMachineInventory().get(location).getSlotLocation() + " " +
			machine.getMachineInventory().get(location).getItemName() + " $" +
			df.format(machine.getMachineInventory().get(location).getItemPrice()) + " " +
			machine.getMachineInventory().get(location).getItemType());
		}
	}
	
	
	public static void endMethodProcessing() { // static attribute used as method is not associated with specific object instance
		// Any processing that needs to be done before method ends
	}
}
