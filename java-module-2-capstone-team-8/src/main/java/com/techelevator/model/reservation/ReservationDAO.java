package com.techelevator.model.reservation;


import java.time.LocalDate;

public interface ReservationDAO {
	
	public Reservation createNewReservation(Long siteId, String name, LocalDate fromDate, LocalDate toDate);
	
}
