package com.techelevator.model.site;

public class Site {

	private long siteId;
	private long campgroundId;
	private int maxOcc;
	private boolean isAccessible;
	private int maxRvLength;
	private boolean isUtility;
	private double totalCost;
	
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getMaxOcc() {
		return maxOcc;
	}
	public void setMaxOcc(int maxOcc) {
		this.maxOcc = maxOcc;
	}
	public boolean isAccessible() {
		return isAccessible;
	}
	public void setAccessible(boolean isAccessible) {
		this.isAccessible = isAccessible;
	}
	public int getMaxRvLength() {
		return maxRvLength;
	}
	public void setMaxRvLength(int maxRvLength) {
		this.maxRvLength = maxRvLength;
	}
	public boolean isUtility() {
		return isUtility;
	}
	public void setUtility(boolean isUtility) {
		this.isUtility = isUtility;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	@Override
	public String toString() {
		String cgString = "";
		cgString += String.format("%-25s", "Site Id");
		cgString += String.format("%-25s", "Max Occupancy");
		cgString += String.format("%-25s", "Accessible");
		cgString += String.format("%-25s", "Max Rv Length");
		cgString += String.format("%-25s", "Utility");
		cgString += String.format("%-25s", "Total Cost");
		cgString += "\n";
		cgString += String.format("%-4s", "");
		cgString += String.format("%-25s", siteId);
		cgString += String.format("%-25s", maxOcc);
		cgString += String.format("%-25s", isAccessible);
		cgString += String.format("%-25s", maxRvLength);
		cgString += String.format("%-25s", isUtility);
		cgString += String.format("%-25s", totalCost);
		return cgString;
	}
}
