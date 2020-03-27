package com.techelevator.model.campground;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.park.Park;

public class JdbcCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate campgroundJdbcTemplate;
	public JdbcCampgroundDAO(DataSource aDataSource) {
		this.campgroundJdbcTemplate = new JdbcTemplate(aDataSource);  // instantiate and initialize JdbcTemplate with datasource
	}
	@Override
	/**********************************
	 * METHOD TO GET ALL CAMPGROUNDS 
	 * FROM PARK SELECTED BY USER
	 ******************************/
	public List<Campground> findCampgroundByParkName(Park selectedPark) { 
		ArrayList<Campground> campgrounds = new ArrayList<>();
		
		String SqlFindCampground = "SELECT * "
								 + "FROM campground "
								 + "INNER JOIN park ON park.park_id = campground.park_id "
								 + "WHERE park.name = ?" ;
		
		SqlRowSet campgroundResults = campgroundJdbcTemplate.queryForRowSet(SqlFindCampground, selectedPark.getName());
		
		while(campgroundResults.next()) {
			Campground theCampground = mapRowToCampground(campgroundResults);
			campgrounds.add(theCampground);
		}
		return campgrounds;
	}
	
	/******************************
	 * 
	 * MAP ROW TO CAMPGROUND METHOD TO RETURN INFO
	 * TO USER BASED ON CAMPGROUND SELECTED
	 *  
	 ********************************/
	
	private Campground mapRowToCampground(SqlRowSet campgroundResults) {
		
		Campground theCampground = new Campground();
		
		theCampground.setCampgroundId(campgroundResults.getLong("campground_id"));
		theCampground.setParkId(campgroundResults.getLong("park_id"));
		theCampground.setName(campgroundResults.getString("name"));
		theCampground.setOpenFromMm(campgroundResults.getInt("open_from_mm"));
		theCampground.setOpenToMm(campgroundResults.getInt("open_to_mm"));
		theCampground.setDailyFee(campgroundResults.getDouble("daily_fee"));
		
		
		return theCampground;
	}

}
