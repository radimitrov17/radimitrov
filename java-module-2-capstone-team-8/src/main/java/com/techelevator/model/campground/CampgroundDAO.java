package com.techelevator.model.campground;

import java.util.List;

import com.techelevator.model.park.Park;

public interface CampgroundDAO {
	
	public List<Campground> findCampgroundByParkName(Park park);


	
}
