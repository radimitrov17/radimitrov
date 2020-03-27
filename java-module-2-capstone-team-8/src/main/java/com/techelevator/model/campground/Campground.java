package com.techelevator.model.campground;


/********************************
 * 
 * CAMPGROUND POJO
 *
 *********************************/

public class Campground {
	private long campgroundId;
	private long parkId;
	private String name;
	private int openFromMm;
	private int openToMm;
	private double dailyFee;
	

	public long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public long getParkId() {
		return parkId;
	}
	public void setParkId(long parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOpenFromMm() {
		return openFromMm;
	}
	public void setOpenFromMm(int openFromMm) {
		this.openFromMm = openFromMm;
	}
	public int getOpenToMm() {
		return openToMm;
	}
	public void setOpenToMm(int openToMm) {
		this.openToMm = openToMm;
	}
	public double getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(double dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	/***********************************
	 * CAMPGROUND toString OVERRIDE
	 **********************************/
	
	@Override
	public String toString() {		
		String cgString = "";
		cgString += String.format("%-25s", "Name");
		cgString += String.format("%-25s", "Open");
		cgString += String.format("%-25s", "Closed");
		cgString += String.format("%-25s", "Daily Fee");
		cgString += "\n";
		cgString += String.format("%-4s", "");
		cgString += String.format("%-25s", name);
		cgString += String.format("%-25s", openFromMm);
		cgString += String.format("%-25s", openToMm);
		cgString += String.format("%-25s", dailyFee);
		return cgString;
	}
}
