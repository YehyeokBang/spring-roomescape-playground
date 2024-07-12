package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.TimeDAO;
import roomescape.dto.ResponseReservation;
import roomescape.dto.ResponseTime;
import roomescape.model.Reservation;
import roomescape.model.Time;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;
    private final TimeDAO timeDAO;

    public ReservationService(ReservationDAO reservationDAO, TimeDAO timeDAO) {
        this.reservationDAO = reservationDAO;
        this.timeDAO = timeDAO;
    }

    public List<ResponseReservation> getReservations() {
        List<Reservation> reservations = reservationDAO.getReservations();
        return reservations.stream()
                .map(this::mapToResponseReservation)
                .toList();
    }

    public ResponseReservation createReservation(String name, String date, Long timeId) {
        Reservation savedReservation = reservationDAO.saveReservation(name, date, timeId);
        return mapToResponseReservation(savedReservation);
    }

    public void deleteReservation(Long id) {
        reservationDAO.deleteReservationById(id);
    }

    private ResponseReservation mapToResponseReservation(Reservation reservation) {
        long id = reservation.getId();
        String name = reservation.getName();
        String date = reservation.getDate();
        Time time = getTimeById(reservation.getTimeId());
        return ResponseReservation.of(id, name, date, mapToResponseTime(time));
    }

    private Time getTimeById(Long timeId) {
        return timeDAO.findTimeById(timeId)
                .orElseThrow(() -> new IllegalArgumentException("No time with the given id exists."));
    }

    private ResponseTime mapToResponseTime(Time time) {
        long id = time.getId();
        String timeValue = time.getTimeValue();
        return ResponseTime.of(id, timeValue);
    }
}
