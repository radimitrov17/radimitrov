package com.techelevator;

public class VendingMachineItem {
	
	private String itemName;		//setting attributes of VendingMachineItem
	private String slotLocation;
	private String itemType;
	private double itemPrice;
	private int inventory;
	
	//building constructor for vending machine item
	public VendingMachineItem(String slotLocation, String itemName, String itemPrice, String itemType, int inventory) {
		this.itemName = itemName;
		this.slotLocation = slotLocation;
		this.itemType = itemType;
		this.itemPrice = Double.parseDouble(itemPrice);
		this.inventory = inventory;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public String getItemName() {
		return itemName;
	}

	public String getSlotLocation() {
		return slotLocation;
	}

	public String getItemType() {
		return itemType;
	}

	public double getItemPrice() {
		return itemPrice;
	}


	
	
}
