package com.techelevator.model.site;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.campground.Campground;

public class JdbcSiteDAO implements SiteDAO {
	
	private JdbcTemplate siteJdbcTemplate;
	public JdbcSiteDAO(DataSource aDataSource) {
		this.siteJdbcTemplate = new JdbcTemplate(aDataSource);  // instantiate and initialize JdbcTemplate with datasource
	}
	@Override 
	
	/**********************************
	 * METHOD TO FIND A SITE FOR USER
	 * BY AVAILABILITY USING fromDate, 
	 * toDate, AND selectedCampground
	 * 
	 *****************************/
	public ArrayList<Site> findSiteByReservationId(LocalDate fromDate, LocalDate toDate, Campground selectedCampground) {
		ArrayList<Site> availableSites = new ArrayList<Site>();
		String sqlFindAvailableSites ="SELECT site.site_id, site.site_number, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities, site.campground_id " +
										"FROM site " +
										"WHERE site.campground_id = ? " +
										"AND site_id NOT in ( "+
										"SELECT site_id FROM reservation " +
										"WHERE from_date <= ? AND to_date > ? " +
										"OR from_date < ? AND to_date >= ? "+
										"OR from_date > ? AND to_date < ?) "+
										" LIMIT 5";

		int daysPassed = (int) ChronoUnit.DAYS.between(fromDate, toDate);
		SqlRowSet siteResults = siteJdbcTemplate.queryForRowSet(sqlFindAvailableSites, selectedCampground.getCampgroundId(), fromDate, fromDate, toDate, toDate, fromDate, toDate);
		while(siteResults.next()) {
			Site theSite = mapRowToSite(siteResults);
			theSite.setTotalCost(selectedCampground.getDailyFee() * daysPassed);
			availableSites.add(theSite);
		}
				
		return availableSites;
	}

	
	/***********************************
	 *  MAP ROW TO SITE METHOD TO RETURN  
	 * 	ALL INFO FOR AVAILABLE SITES
	 *  WITHIN USERS SELECTED DATES
	 ***********************************/
	
	private Site mapRowToSite(SqlRowSet siteResults) {
		Site theSite = new Site();
		
		theSite.setSiteId(siteResults.getLong("site_id"));
		theSite.setCampgroundId(siteResults.getLong("campground_id"));
		theSite.setMaxOcc(siteResults.getInt("max_occupancy"));
		theSite.setAccessible(siteResults.getBoolean("accessible"));
		theSite.setMaxRvLength(siteResults.getInt("max_rv_length"));
		theSite.setUtility(siteResults.getBoolean("utilities"));
		return theSite;
	}

}
