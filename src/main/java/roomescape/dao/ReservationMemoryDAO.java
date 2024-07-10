package roomescape.dao;

import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationMemoryDAO implements ReservationDAO {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    @Override
    public Reservation saveReservation(String name, String date, String time) {
        Long newId = idCounter.incrementAndGet();
        Reservation newReservation = new Reservation(newId, name, date, time);
        reservations.add(newReservation);
        return newReservation;
    }

    @Override
    public List<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public void deleteReservation(Long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));
        if (!removed) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }
}
