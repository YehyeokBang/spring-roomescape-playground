package roomescape.dao;

import roomescape.model.Time;

import java.util.List;
import java.util.Optional;

public interface TimeDAO {
    Time save(String time);
    List<Time> findAll();
    Optional<Time> findTimeById(Long id);
    void deleteById(Long id);
}
