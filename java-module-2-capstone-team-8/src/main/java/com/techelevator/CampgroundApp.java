package com.techelevator;


import java.time.LocalDate;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.campground.Campground;
import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.park.Park;
import com.techelevator.model.reservation.*;
import com.techelevator.model.site.JdbcSiteDAO;
import com.techelevator.model.site.Site;
import com.techelevator.view.*;

public class CampgroundApp {

	private static JdbcSiteDAO siteDAO;
	private static JdbcCampgroundDAO campgroundDAO;
	private static JdbcReservationDAO reservationDAO;
	private static JdbcParkDAO parkDAO;
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/nationalpark");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		/*********************************************
		 * INSTANTIATING DAOs
		 *********************************************/
		
		siteDAO = new JdbcSiteDAO(dataSource);					
		campgroundDAO = new JdbcCampgroundDAO(dataSource);
		reservationDAO = new JdbcReservationDAO(dataSource);
		parkDAO =new JdbcParkDAO(dataSource);
		
		CampgroundUI userInterface = new CampgroundUI(); // Object to manage user interactions;
		userInterface.run();
	}
	
		
		/****************************************************************************************************
		 * Instantiate any DAOs you will be using here
		 ***************************************************************************************************/
	
	
	
		public List<Park> getAllParks() {
			return parkDAO.findAllParks();
		}
		
		public List<Campground> getAllCampgrounds(Park selectedPark){
			return campgroundDAO.findCampgroundByParkName(selectedPark);//Argument should not be null but will not take anything else 
		}
	
		public List<Site> findSiteByReservationId(LocalDate fromDate, LocalDate toDate, Campground selectedCampground ){
			return siteDAO.findSiteByReservationId(fromDate, toDate, selectedCampground);
		}
		
		public Reservation getCreatedReservation(long campgroundId, String name, LocalDate fromDate, LocalDate toDate){
			return reservationDAO.createNewReservation(campgroundId, name, fromDate, toDate);
		}

}	
