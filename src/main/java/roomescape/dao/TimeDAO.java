package roomescape.dao;

import roomescape.model.Time;

import java.util.List;

public interface TimeDAO {
    Time saveTime(String time);
    List<Time> getTimes();
    void deleteTime(Long id);
}
