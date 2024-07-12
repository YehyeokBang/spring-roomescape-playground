package roomescape.dao;

import roomescape.model.Reservation;

import java.util.List;

public interface ReservationDAO {
    Reservation save(String name, String date, Long time);
    List<Reservation> findAll();
    void deleteById(Long id);
}
