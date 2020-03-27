package com.techelevator.model.site;

import java.time.LocalDate;
import java.util.ArrayList;

import com.techelevator.model.campground.Campground;

public interface SiteDAO {

	public ArrayList<Site> findSiteByReservationId(LocalDate fromDate, LocalDate toDate, Campground selectedReservation);
	
	
}
