package com.techelevator;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestVendingMachine {
	
	VendingMachine testObject = new VendingMachine("vendingmachine.csv");
	

	@Test
	public void test_for_item_in_appropriate_location() {
		assertEquals("Expect key/value to be in the appropriate location", "A1", testObject.getMachineInventory().get("A1").getSlotLocation());
	}

	@Test
	public void test_for_specific_item_price() {
		assertEquals("Price at B1 should be 1.80", 1.80, testObject.getMachineInventory().get("B1").getItemPrice(), 0);
	}
	
	@Test
	public void test_for_item_name() {
		assertEquals("Name at C1 should be Cola", "Cola", testObject.getMachineInventory().get("C1").getItemName());
	}
	
	@Test
	public void test_for_item_type() {
		assertEquals("Item type of U-Chews should be gum", "Gum", testObject.getMachineInventory().get("D1").getItemType());
	}
}
