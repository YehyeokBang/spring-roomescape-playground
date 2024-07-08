package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationJdbcDAO implements ReservationDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation saveReservation(String name, String date, String time) {
        Map<String, Object> parameters = getParametersMapForInsert(name, date, time);
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Reservation(newId.longValue(), name, date, time);
    }

    @Override
    public List<Reservation> getReservations() {
        return jdbcTemplate.query("SELECT * FROM reservation",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time")
                ));
    }

    @Override
    public void deleteReservation(Long id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }

    private Map<String, Object> getParametersMapForInsert(String name, String date, String time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("date", date);
        parameters.put("time", time);
        return parameters;
    }
}
