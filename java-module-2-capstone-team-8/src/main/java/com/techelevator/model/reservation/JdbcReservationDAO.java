package com.techelevator.model.reservation;

import java.time.LocalDate; 
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcReservationDAO implements ReservationDAO {
	
	private JdbcTemplate reservationJdbcTemplate;
	public JdbcReservationDAO(DataSource aDataSource) {
		this.reservationJdbcTemplate = new JdbcTemplate(aDataSource);  // instantiate and initialize JdbcTemplate with datasource
	}

	@Override
	
	/***************************************
	 * CREATE RESERVATION METHOD TO CREATE
	 * A NEW RESERVATION BASED ON USER 
	 * INPUT
	 **********************************/
	
	public Reservation createNewReservation(Long siteId, String name, LocalDate fromDate, LocalDate toDate) {
		String sqlInsertReservation = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date, create_date)"
									+ "VALUES (?, ?, ?, ?, ?, ?)";	
		
		Reservation reservation = new Reservation();
		reservation.setName(name);
		reservation.setSiteId(siteId);
		reservation.setFromDate(fromDate);
		reservation.setToDate(toDate);
		reservation.setResId(getNextReservationId());
		reservation.setCreateDate(LocalDate.now());
		
		reservationJdbcTemplate.update(sqlInsertReservation, 
										reservation.getResId(), 
										reservation.getSiteId(), 
										reservation.getName(), 
										reservation.getFromDate(), 
										reservation.getToDate(), 
										reservation.getCreateDate());
		return reservation;
	}
	
	/******************************************
	 * 
	 * GETNEXTRESID METHOD CREATES A NEW ID 
	 * FOR NEWLY CREATED RESERVATION
	 * 
	 ******************************************/
	
	private long getNextReservationId() {
		SqlRowSet nextIdResult = reservationJdbcTemplate.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		}else {
			throw new RuntimeException("Help me frank plz");
		}
	}

}
