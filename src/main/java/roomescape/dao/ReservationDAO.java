package roomescape.dao;

import roomescape.model.Reservation;

import java.util.List;

public interface ReservationDAO {
    Reservation saveReservation(String name, String date, String time);
    List<Reservation> getReservations();
    void deleteReservation(Long id);
}
