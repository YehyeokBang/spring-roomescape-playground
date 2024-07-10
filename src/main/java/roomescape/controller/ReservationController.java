package roomescape.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dao.ReservationDAO;
import roomescape.dto.RequestReservation;
import roomescape.model.Reservation;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
public class ReservationController {
    private final ReservationDAO reservationDAO;

    public ReservationController(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "new-reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations() {
        List<Reservation> reservations = reservationDAO.getReservations();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody RequestReservation requestReservation) {
        String name = requestReservation.name();
        String date = requestReservation.date();
        Long timeId = requestReservation.time();
        Reservation newReservation = reservationDAO.saveReservation(name, date, timeId);

        return ResponseEntity.status(CREATED)
                .header(HttpHeaders.LOCATION, "/reservations/" + newReservation.getId())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{reservationId}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservations(@PathVariable Long reservationId) {
        reservationDAO.deleteReservation(reservationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }
}
