package com.techelevator.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.techelevator.CampgroundApp;
import com.techelevator.model.campground.Campground;
import com.techelevator.model.park.Park;
import com.techelevator.model.reservation.Reservation;
import com.techelevator.model.site.Site;

public class CampgroundUI {
	
	Menu campMenu = new Menu(System.in, System.out);  // Define menu for keyboard input and screen output
	CampgroundApp campApp = new CampgroundApp();
	Park selectedPark;
	Campground selectedCampground;
	Site selectedSite;
	String hold, name;
	Long campgroundId;
	LocalDate toDate, fromDate;
	Reservation createdReservation;
	
	//main method - single flow
	public void run() {
		
		displayApplicationBanner();	
		printHeading("Main Menu");
		//promptTheUser();
		displayParksAndSelectOne();
		printHeading(selectedPark.getName());
		//promptTheUser();
		displayParkInfo(selectedPark);
		displayCampgroundBanner();
		printHeading(selectedPark.getName() + "'s CampGrounds");
		//promptTheUser();
		selectedCampground = viewCampGrounds(selectedPark);
		inputToDate();
		inputFromDate();
		displayCampsiteBanner();
		printHeading(selectedPark.getName() + "'s Campsites");
		selectedSite = findAllAvailableReservations(fromDate, toDate, selectedCampground);
		inputUserName();
		createReservation(selectedSite.getCampgroundId(), name, fromDate, toDate);
		displayExitBanner();
		}
	
	// prints out all park information 
	private void displayParkInfo(Park park) {
		System.out.println("Location: " + park.getLocation());
		System.out.println("Estamblished: " + park.getEstDate());
		System.out.println("Area: " + park.getArea() + " sq km");
		System.out.println("Annual Visitors: " + park.getVisitors());
		System.out.println("\n");
		System.out.println(park.getDescription());
	}
	
	//lists all parks in database
	public Park displayParksAndSelectOne() {
		List<Park> parks = campApp.getAllParks();
		selectedPark = (Park) campMenu.getChoiceFromOptions(parks.toArray());
		return selectedPark;
	}
	
	//lists all campgrounds in database
	private Campground viewCampGrounds(Park selectedPark) {
		List<Campground> campground = campApp.getAllCampgrounds(selectedPark);
		selectedCampground = (Campground) campMenu.getChoiceFromOptions(campground.toArray());
		return selectedCampground;
	}
	
	//lists all site in database
	public Site findAllAvailableReservations(LocalDate fromDate, LocalDate toDate, Campground selectedCampground) {
		List<Site> site = campApp.findSiteByReservationId(fromDate, toDate, selectedCampground);
		selectedSite = (Site) campMenu.getChoiceFromOptions(site.toArray());
		return selectedSite;
	}
	
	//creates and prints out the reservation 
	public Reservation createReservation(Long siteId, String name, LocalDate fromDate, LocalDate toDate) {
		Reservation reservation = campApp.getCreatedReservation(siteId, name, fromDate, toDate);
		System.out.println("Your reservation number is {" + reservation.getResId() +"}.");
		return reservation;
	}
	
	// Inputs Methods
	private String scanner() {//creates scanner
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String hold = input.nextLine();
		return hold;
	}
	
	private void inputToDate() {
		System.out.println("\n");
		System.out.println("PLease enter the date you plan to arrive on the camp site");
		System.out.println("(Input date as such: YYYY-MM-DD)");
		hold = scanner();
		fromDate = LocalDate.parse(hold);
	}
	
	private void inputFromDate() {
		System.out.println("\n");
		System.out.println("PLease enter the date you plan to depart from the camp site");
		System.out.println("(Input date as such: YYYY-MM-DD)");
		hold = scanner();	
		toDate = LocalDate.parse(hold);
	}
	private void inputUserName() {
		System.out.println("PLease enter the name you wish to use for the reservation");
		name = scanner();
	}
	
	// Headers/Banners
	private void displayApplicationBanner() {

		System.out.println("  _   _       _   _                   _ _____           _     _____                          _ _       _____                                _   _              ");
		System.out.println(" | \\ | |     | | (_)                 | |  __ \\         | |   / ____|                        (_) |     |  __ \\                              | | (_)             ");
		System.out.println(" |  \\| | __ _| |_ _  ___  _ __   __ _| | |__) |_ _ _ __| | _| |     __ _ _ __ ___  _ __  ___ _| |_ ___| |__) |___  ___  ___ _ ____   ____ _| |_ _  ___  _ __   ");
		System.out.println(" | . ` |/ _` | __| |/ _ \\| '_ \\ / _` | |  ___/ _` | '__| |/ / |    / _` | '_ ` _ \\| '_ \\/ __| | __/ _ \\  _  // _ \\/ __|/ _ \\ '__\\ \\ / / _` | __| |/ _ \\| '_ \\  ");
		System.out.println(" | |\\  | (_| | |_| | (_) | | | | (_| | | |  | (_| | |  |   <| |___| (_| | | | | | | |_) \\__ \\ | ||  __/ | \\ \\  __/\\__ \\  __/ |   \\ V / (_| | |_| | (_) | | | | ");
		System.out.println(" |_| \\_|\\__,_|\\__|_|\\___/|_| |_|\\__,_|_|_|   \\__,_|_|  |_|\\_\\\\_____\\__,_|_| |_| |_| .__/|___/_|\\__\\___|_|  \\_\\___||___/\\___|_|    \\_/ \\__,_|\\__|_|\\___/|_| |_| ");
		System.out.println("                                                                                  | |                                                                          ");
		System.out.println("                                                                                  |_|                                                                          ");

	}
	
	private void displayCampgroundBanner() {
		System.out.println("   _____                                                      _       ");
		System.out.println("  / ____|                                                    | |     ");
		System.out.println(" | |     __ _ _ __ ___  _ __   __ _ _ __ ___  _   _ _ __   __| |___  ");
		System.out.println(" | |    / _` | '_ ` _ \\| '_ \\ / _` | '__/ _ \\| | | | '_ \\ / _` / __| ");
		System.out.println(" | |___| (_| | | | | | | |_) | (_| | | | (_) | |_| | | | | (_| \\__ \\ ");
		System.out.println("  \\_____\\__,_|_| |_| |_| .__/ \\__, |_|  \\___/ \\__,_|_| |_|\\__,_|___/ ");
		System.out.println("                       | |     __/ |                                ");
		System.out.println("                       |_|    |___/                                 ");

	}
	private void displayCampsiteBanner(){
		System.out.println("   _____                          _ _               ");
		System.out.println("  / ____|                        (_) |              ");
		System.out.println(" | |     __ _ _ __ ___  _ __  ___ _| |_ ___  ___    ");
		System.out.println(" | |    / _` | '_ ` _ \\| '_ \\/ __| | __/ _ \\/ __|   ");
		System.out.println(" | |___| (_| | | | | | | |_) \\__ \\ | ||  __/\\__ \\   ");
		System.out.println("  \\_____\\__,_|_| |_| |_| .__/|___/_|\\__\\___||___/   ");
		System.out.println("                       | |                          ");
		System.out.println("                       |_|                          ");

	}
	private void displayExitBanner() {
	System.out.println("\n*********************************************************************");
	System.out.println("   Thanks for using the National Parks Campsite Reservation System");
	System.out.println("       			Happy Trails Camper !");
	System.out.println("*********************************************************************");
	System.exit(0);// ends the program
	}
	
	
	private void printHeading(String headingText) {
		System.out.println("\n"+headingText);
		for(int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
    }
	// dead code since single flow
//	private void promptTheUser() {
//		System.out.println("Please choose what you are interested in or press zero to cancel");
//	}
}
