package com.techelevator;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


import com.techelevator.model.site.JdbcSiteDAO;
import com.techelevator.model.site.Site;

public class DAOSiteIntegrationTest extends DAOIntegrationTest {
	private static final long CAMPGROUND_ID = 700;
	
	private static SingleConnectionDataSource dataSource;
	private JdbcSiteDAO dao;
	
	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/nationalpark");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}
	@Before 
	public void setup() {
	String sqlInsertCampground = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee)" +
								 "VALUES (?, 1, 'Acadia', 01, 12, 35.00) ";
	String sqlInsertSite = "INSERT INTO site (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities)" +
							   "VALUES (700, ? , 5, 6, 'false', 0, 'false')";
		JdbcTemplate siteJdbcTemplate = new JdbcTemplate(dataSource);
		siteJdbcTemplate.update(sqlInsertCampground, CAMPGROUND_ID);
		siteJdbcTemplate.update(sqlInsertSite, CAMPGROUND_ID);
		
		dao = new JdbcSiteDAO(dataSource);
	}
	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	
	
	

	@Test
	public void return_all_available_sites() {
		LocalDate fromDate = LocalDate.of(2020, 02, 18);
		LocalDate toDate = LocalDate.of(2020, 02, 22);
		long campgroundId = 700;

		
		ArrayList<Site> siteResults = dao.findSiteByReservationId(fromDate, toDate, campgroundId);
		
		assertNotNull(siteResults);
	}

}
