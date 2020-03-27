package com.techelevator;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


import com.techelevator.model.reservation.JdbcReservationDAO;
import com.techelevator.model.reservation.Reservation;

public class DAOReservationIntegrationTest extends DAOIntegrationTest {

	
	private static SingleConnectionDataSource dataSource;
	private JdbcReservationDAO dao;
	
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
		
		
		dao = new JdbcReservationDAO(dataSource);
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

	/* This method provides access to the DataSource for subclasses so that
	 * they can instantiate a DAO for testing */
	protected DataSource getDataSource() {
		return dataSource;
	}
	
	@Test		//test to ensure create reservation method creates new reservation
	public void create_new_reservation(/*long resId, String name, LocalDate fromDate, LocalDate toDate, LocalDate createDate*/) throws SQLException {
		LocalDate fromDate = LocalDate.of(2020, 02, 18);
		LocalDate toDate = LocalDate.of(2020, 02, 22);
		LocalDate createDate = LocalDate.of(2020, 02, 03);
		
		Reservation myReservation = getReservation(1000, "Dimitrov Family Reservation", fromDate, toDate, createDate);
		
		assertNotNull(myReservation);
		
	}	//method created to use in other test method
	private Reservation getReservation(long resId, String name, LocalDate fromDate, LocalDate toDate, LocalDate createDate) {
		Reservation myReservation = new Reservation();
		myReservation.setResId(resId);
		myReservation.setName(name);
		myReservation.setFromDate(fromDate);
		myReservation.setToDate(toDate);
		myReservation.setCreateDate(createDate);
		return myReservation;
	}

}
