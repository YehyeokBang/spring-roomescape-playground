package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dto.ResponseReservation;
import roomescape.model.Reservation;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
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
        long timeId = reservation.getTimeId();
        return ResponseReservation.of(id, name, date, timeId);
    }
}
