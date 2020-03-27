package com.techelevator.model.park;

import java.util.ArrayList; 
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcParkDAO implements ParkDAO {	 // concrete class is named JDBCtablenameDAO

	private JdbcTemplate parkJdbcTemplate;  // declare an JdbcTemplate variable
		
	// constructor that takes datasource and assigns to JdbcTemplate object
		
	public JdbcParkDAO(DataSource aDataSource) {
		this.parkJdbcTemplate = new JdbcTemplate(aDataSource);  // instantiate and initialize JdbcTemplate with datasource
	}
	
	
	/****************************
	 * FIRST LIST OF SHOWED TO USER 
	 * WHEN PROGRAM RUNS
	 *****************************/
	
	public ArrayList<Park> findAllParks() {
		ArrayList<Park> parks = new ArrayList<>();
		
		String sqlFindAllParks = "SELECT * "
							   + "FROM park "
							   + "ORDER BY name";
		SqlRowSet parkResults = parkJdbcTemplate.queryForRowSet(sqlFindAllParks);
		while (parkResults.next()){
			Park thePark = mapRowToPark(parkResults);
			parks.add(thePark);
		}
		
		return parks;
	}

	
	/***********************************
	 * 
	 * MAP ROW TO PARK METHOD TO GIVE INFO
	 * ABOUT THE PARK USER SELECTED
	 * 
	 ********************************/
	
	private Park mapRowToPark(SqlRowSet parkResults) {
		
		Park thePark = new Park();
		
		thePark.setParkId(parkResults.getLong("park_id"));
		thePark.setName(parkResults.getString("name"));
		thePark.setLocation(parkResults.getString("location"));
		thePark.setEstDate(parkResults.getDate("establish_date"));
		thePark.setArea(parkResults.getInt("area"));
		thePark.setVisitors(parkResults.getInt("visitors"));
		thePark.setDescription(parkResults.getString("description"));
		
		return thePark;
	}
}

